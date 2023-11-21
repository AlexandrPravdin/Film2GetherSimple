package com.example.film2gethersimple.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Film(
    @StringRes val name: Int = -1,
    @DrawableRes val image: Int = -1,
    @StringRes val description: Int = -1,
    val genres: Set<Genres>,
    val iMDbRate: Double
)
