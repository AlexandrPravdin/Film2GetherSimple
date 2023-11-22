package com.example.film2gethersimple.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigationScreens(
    val icon: ImageVector
) {
    HomeScreen(Icons.Filled.Home),
    AccountScreen(Icons.Filled.AccountCircle)

}