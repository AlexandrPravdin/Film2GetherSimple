package com.example.film2gethersimple.ui

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.Account

@Composable
fun AccountScreen(
    account: Account,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        AccountImage(account = account)
        AccountName(account = account)
        Text(text = account.mail)
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

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    AccountScreen(
        account = Account(
            firstName = "Alexandr",
            secondName = "Pravdin",
            image = R.drawable.godfather_2_image,
            mail = "Alex.pravdinn@gmail.com".lowercase()
        )
    )
}