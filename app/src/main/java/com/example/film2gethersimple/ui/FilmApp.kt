package com.example.film2gethersimple.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.film2gethersimple.ui.utils.ContentType
import com.example.film2gethersimple.ui.utils.NavigationType


//App at all
@Composable
fun FilmApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    //viewModel architecture
    val viewModel: FilmViewModel = viewModel()
    val homeUiState = viewModel.uiState.collectAsState().value

    //navigation logic
    val navController = rememberNavController()


    //ScreenSize logic
    val navigationType: NavigationType
    val contentType: ContentType
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.ListOnly
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = ContentType.ListOnly
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = ContentType.ListAndDetails
        }

        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.ListOnly
        }
    }
    //Film screen with Home and Details screen
    FilmMainScreen(
        uiState = homeUiState,
        viewModel = viewModel,
        navController = navController,
        navigationType = navigationType,
        contentType = contentType,
        modifier = modifier
    )

}
