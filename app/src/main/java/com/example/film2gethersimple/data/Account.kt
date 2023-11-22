package com.example.film2gethersimple.data

import android.media.Image
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.util.Date

//Data class with information about user
data class Account(
    @StringRes val firstName: Int,
    @StringRes val secondName: Int,
    @DrawableRes val image: Int,
    @StringRes val mail: Int,
//    val registrationDate: Date
)
