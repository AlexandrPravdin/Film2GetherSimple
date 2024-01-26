package com.example.film2gethersimple.data

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.film2gethersimple.R

enum class NavigationScreens(
    val icon: ImageVector?,
    @StringRes val title: Int
) {
    HomeScreen(Icons.Filled.Home, R.string.home_screen),
    AccountScreen(Icons.Filled.AccountCircle, R.string.account_screen),
    DetailsScreen(Icons.Filled.List, title = R.string.details_screen),
}

