package com.example.film2gethersimple.data.local

import com.example.film2gethersimple.R
import com.example.film2gethersimple.ui.models.Account

object LocalAccountDataProvider {

    //Account with real data
    val account: Account = Account(
        firstName = "Alexandr",
        secondName = "Pravdin",
        image = R.drawable.account_image,
        mail = "alex.pravdinn@gmail.com"
    )

    //Default account for uiState initialization
    val default: Account = Account(
        firstName = "",
        secondName = "",
        image = -1,
        mail = ""
    )
}