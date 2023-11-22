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
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = account.image),
            contentDescription = "Account Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 16.dp)

                .size(200.dp)
                .clip(CircleShape)
                .border(
                    BorderStroke(4.dp, MaterialTheme.colorScheme.primary), CircleShape
                ),
        )
        Text(
            text = stringResource(id = account.firstName)
                    + " "
                    + stringResource(id = account.secondName),
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.displaySmall
        )
        Text(text = stringResource(id = account.mail))
    }
}


@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    AccountScreen(
        account = Account(
            firstName = R.string.account_first_name,
            secondName = R.string.account_second_name,
            image = R.drawable.godfather_2_image,
            mail = R.string.account_email
        )
    )
}