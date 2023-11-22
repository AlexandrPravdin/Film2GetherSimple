package com.example.film2gethersimple.data.local

import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.Account

object LocalAccountDataProvider {

    //Account with real data
    val account: Account = Account(
        firstName = R.string.account_first_name,
        secondName = R.string.account_second_name,
        image = R.drawable.account_image,
        mail = R.string.account_email
    )

    //Default account for uiState initialization
    val default: Account = Account(
        firstName = -1,
        secondName = -1,
        image = -1,
        mail = -1
    )
}