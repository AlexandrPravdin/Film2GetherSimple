package com.example.film2gethersimple.ui.navigation

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
import com.example.film2gethersimple.ui.mainscreen.homescreen.FilmUiState
import com.example.film2gethersimple.ui.mainscreen.homescreen.HomeFilmScreen
import com.example.film2gethersimple.ui.mainscreen.homescreen.LoadingScreen
import com.example.film2gethersimple.ui.mainscreen.listanddetailsscreen.FilmListAndDetailScreen
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.ui.utils.ContentType

@Composable
fun AppNavHost(
    navController: NavHostController,
    uiState: FilmUiState,
    onRetryButtonClick: () -> Unit,
    onHomeScreenCardClick: (Film) -> Unit,
    contentType: ContentType,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    NavHost(
        navController = navController,
        startDestination =
        if (contentType == ContentType.ListOnly)
            NavigationScreens.HomeScreen.name
        else NavigationScreens.ListAndDetailsScreen.name,
        modifier = modifier.padding(contentPadding),
    ) {
        composable(NavigationScreens.HomeScreen.name) {
            when (uiState) {
                is FilmUiState.Success -> {
                    HomeFilmScreen(
                        uiState = uiState,
                        onHomeScreenCardClick = { film: Film ->
                            onHomeScreenCardClick(film)
                            navController.navigate(NavigationScreens.DetailsScreen.name)
                        },
                    )
                }

                is FilmUiState.Error -> {
                    ErrorScreen(onRetryButtonClick)
                }

                is FilmUiState.Loading -> {
                    LoadingScreen()
                }
            }

        }
        composable(NavigationScreens.ListAndDetailsScreen.name) {

            when (uiState) {
                is FilmUiState.Success -> {
                    FilmListAndDetailScreen(allFilms = uiState.response,
                        currentFilm = uiState.currentSelectedItem,
                        onHomeScreenCardClick = { film: Film ->
                            onHomeScreenCardClick(film)
                        })
                }

                is FilmUiState.Error -> {
                    ErrorScreen(onRetryButtonClick)
                }

                is FilmUiState.Loading -> {
                    LoadingScreen()
                }
            }
        }
        composable(NavigationScreens.AccountScreen.name) {
            AccountScreen(account = account)
        }
        composable(NavigationScreens.DetailsScreen.name) {
            when (uiState) {
                is FilmUiState.Success -> {
                    DetailScreen(
                        film = uiState.currentSelectedItem,
                        onBackPressed = {
                            navController.popBackStack()
                        })
                }

                is FilmUiState.Error -> {
                    ErrorScreen(onRetryButtonClick)
                }

                is FilmUiState.Loading -> {
                    LoadingScreen()
                }
            }
        }
    }
}