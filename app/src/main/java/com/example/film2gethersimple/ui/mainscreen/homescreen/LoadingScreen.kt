package com.example.film2gethersimple.ui.mainscreen.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.film2gethersimple.R


@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Loading...",
            style = MaterialTheme.typography.h2,

            )
    }
}

@Composable
fun ErrorScreen(
    onRetryButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.h2,
        )
        Button(onClick = onRetryButtonClick) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Outlined.Refresh,
                    contentDescription = Icons.Outlined.Refresh.name,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.medium))
                )
                Text(
                    text = "Retry",
                    modifier = Modifier.padding(
                        end = dimensionResource(id = R.dimen.medium),
                        top = dimensionResource(id = R.dimen.medium),
                        bottom = dimensionResource(id = R.dimen.medium)
                    )
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
    ErrorScreen(onRetryButtonClick = {})
}