package com.example.film2gethersimple.ui.navigation

import android.util.Log
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.film2gethersimple.data.NavigationScreens
import com.example.film2gethersimple.data.local.LocalAccountDataProvider.account
import com.example.film2gethersimple.ui.account.AccountScreen
import com.example.film2gethersimple.ui.mainscreen.detailscreen.DetailScreen
import com.example.film2gethersimple.ui.mainscreen.homescreen.ErrorScreen
import com.example.film2gethersimple.ui.mainscreen.homescreen.HomeFilmScreen
import com.example.film2gethersimple.ui.mainscreen.homescreen.HomeUiState
import com.example.film2gethersimple.ui.mainscreen.homescreen.LoadingScreen
import com.example.film2gethersimple.ui.mainscreen.listanddetailsscreen.FilmListAndDetailScreen
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.ui.utils.ContentType

private const val TAG = "FilmNavHost"

@Composable
fun AppNavHost(
    navController: NavHostController,
    uiState: HomeUiState,
    onRetryButtonClick: () -> Unit,
    onHomeScreenCardClick: (Film) -> Unit,
    contentType: ContentType,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Log.i(TAG, "NavHost Started, content type: ${contentType}")
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.HomeScreen.name,
        modifier = modifier.padding(contentPadding),

        ) {
        //Starting the main screen. Logic of Loading and Error are saved.
        composable(NavigationScreens.HomeScreen.name) {
            Log.i(TAG, "HomeScreen Started")
            when (uiState) {
                is HomeUiState.Success -> {
                    if (contentType != ContentType.ListAndDetails) {
                        Log.i(TAG, "I want to start home only")
                        HomeFilmScreen(
                            uiState = uiState,
                            onHomeScreenCardClick = { film: Film ->
                                onHomeScreenCardClick(film)
                                navController.navigate(NavigationScreens.DetailsScreen.name)
                            },
                        )
                    } else {
                        Log.i(TAG, "I want to start List and details")
                        FilmListAndDetailScreen(allFilms = uiState.response,
                            currentFilm = uiState.currentSelectedItem,
                            onHomeScreenCardClick = { film: Film ->
                                onHomeScreenCardClick(film)
                            })
                    }
                }

                is HomeUiState.Error -> {
                    ErrorScreen(
                        onRetryButtonClick,
                        uiState.errorName,
                    )
                }

                is HomeUiState.Loading -> {
                    LoadingScreen()
                }
            }

        }
        composable(NavigationScreens.AccountScreen.name) {
            Log.i(TAG, "AccountScreen Started")
            AccountScreen(account = account)
        }
        composable(NavigationScreens.DetailsScreen.name,
            enterTransition = {
                slideInVertically(
                    animationSpec = tween(durationMillis = 200)
                ) { 300 } + fadeIn(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 200)
                )
            },
            exitTransition = {
                slideOutVertically(
                    animationSpec = tween(durationMillis = 300, easing = EaseOut)
                ) { 300 } + fadeOut(
                    animationSpec = tween(delayMillis = 50)
                )
            }) {
            Log.i(TAG, "DetailsScreen Started")
            when (uiState) {
                is HomeUiState.Success -> {
                    DetailScreen(film = uiState.currentSelectedItem, onBackPressed = {
                        navController.popBackStack()
                    })
                }

                is HomeUiState.Error -> {
                    ErrorScreen(onRetryButtonClick, uiState.errorName)
                }

                is HomeUiState.Loading -> {
                    LoadingScreen()
                }
            }
        }
    }
}