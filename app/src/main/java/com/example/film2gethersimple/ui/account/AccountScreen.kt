package com.example.film2gethersimple.ui.account

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.NavigationScreens
import com.example.film2gethersimple.ui.AppViewModelProvider
import com.example.film2gethersimple.ui.models.Account
import com.example.film2gethersimple.ui.theme.AppThemeViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    account: Account,
    modifier: Modifier = Modifier,
    themeViewModel: AppThemeViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val dynamicColor: Boolean = themeViewModel.uiState.collectAsState().value.isDynamicTheme
    var expanded by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier,
                title = {
                    Text(
                        text = stringResource(NavigationScreens.AccountScreen.title),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                actions =
                {
                    IconButton(onClick = { expanded = !expanded }
                    ) {
                        Icon(
                            imageVector = if (!expanded) {
                                Icons.Filled.KeyboardArrowDown
                            } else {
                                Icons.Filled.KeyboardArrowUp
                            },
                            contentDescription = Icons.Filled.ArrowBack.name
                        )
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = if (dynamicColor) {
                                        Icons.Filled.Done
                                    } else {
                                        Icons.Filled.Close
                                    },
                                    contentDescription = if (dynamicColor) {
                                        Icons.Filled.Done.name
                                    } else {
                                        Icons.Filled.Close.name
                                    }
                                )
                            },
                            text = {
                                Text(
                                    text = if (dynamicColor) {
                                        "Dynamic theme is ON"
                                    } else {
                                        "Dynamic theme is OFF"
                                    },
                                )
                            },
                            onClick = { themeViewModel.swapColorScheme() })
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            AccountImage(account = account)
            AccountName(account = account)
            Text(text = account.mail)
        }
    }
}


@Composable
fun AccountImage(
    account: Account,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = account.image),
        contentDescription = stringResource(R.string.account_image),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .padding(bottom = dimensionResource(id = R.dimen.large))
            .size(200.dp)
            .clip(CircleShape)
            .border(
                BorderStroke(4.dp, MaterialTheme.colorScheme.primary), CircleShape
            ),
    )
}

@Composable
fun AccountName(
    account: Account,
    modifier: Modifier = Modifier
) {
    Text(
        text = "${account.firstName} ${account.secondName}",
        modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.large)),
        style = MaterialTheme.typography.displaySmall
    )
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    AccountScreen(
        account = Account(
            firstName = "Alexandr",
            secondName = "Pravdin",
            image = R.drawable.ic_launcher_foreground,
            mail = "Alex.pravdinn@gmail.com".lowercase()
        )
    )
}
