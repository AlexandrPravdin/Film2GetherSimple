package com.example.film2gethersimple.data.local

import com.example.film2gethersimple.R
import com.example.film2gethersimple.ui.models.Account

object LocalAccountDataProvider {

    //Account with real data
    val account: Account = Account(
        firstName = "Alexandr",
        secondName = "Pravdin",
        image = R.drawable.ic_launcher_foreground,
        mail = "alex.pravdinn@gmail.com"
    )

    //Default account for uiState initialization
}