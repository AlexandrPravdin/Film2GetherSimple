package com.example.film2gethersimple.ui.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.film2gethersimple.data.Genres

data class Film(
    @StringRes val name: Int = -1,
    @DrawableRes val image: Int = -1,
    @StringRes val description: Int = -1,
    val genres: Set<Genres>,
    val iMDbRate: Double
)
