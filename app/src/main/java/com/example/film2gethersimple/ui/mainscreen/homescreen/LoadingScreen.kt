package com.example.film2gethersimple.ui.mainscreen.homescreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.film2gethersimple.R


@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(dimensionResource(id = R.dimen.progres_indicator_size)),
            strokeWidth = dimensionResource(id = R.dimen.medium),
            color = MaterialTheme.colorScheme.primary,
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
fun ErrorScreen(
    onRetryButtonClick: () -> Unit, errorName: String?, modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Toast.makeText(context, errorName, Toast.LENGTH_LONG).show()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.error),
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
        )
        Button(
            onClick = onRetryButtonClick,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium)),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Outlined.Refresh,
                    contentDescription = Icons.Outlined.Refresh.name,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.medium)),
                )
                Text(
                    text = stringResource(R.string.retry), modifier = Modifier.padding(
                        end = dimensionResource(id = R.dimen.medium),
                        top = dimensionResource(id = R.dimen.medium),
                        bottom = dimensionResource(id = R.dimen.medium)
                    ), color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        onRetryButtonClick = {}, "Popa"
    )
}