package com.example.film2gethersimple.ui.mainscreen.listanddetailsscreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.NavigationScreens
import com.example.film2gethersimple.ui.mainscreen.detailscreen.DetailScreen
import com.example.film2gethersimple.ui.mainscreen.homescreen.FilmUiState
import com.example.film2gethersimple.ui.mainscreen.homescreen.HomeScreenColumn
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.ui.navigation.AppNavHost
import com.example.film2gethersimple.ui.navigation.ScreensPermanentDrawerItem
import com.example.film2gethersimple.ui.utils.ContentType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmPermanentDrawer(
    navController: NavHostController,
    screens: List<NavigationScreens>,
    uiState: FilmUiState,
    onHomeScreenCardClick: (Film) -> Unit,
    contentType: ContentType,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onRetryButtonClick: () -> Unit,
) {
    PermanentNavigationDrawer(
        modifier = modifier,
        drawerContent = {
            PermanentDrawerSheet(
                Modifier
                    .width(dimensionResource(id = R.dimen.permanent_drawer_width))
                    .padding(contentPadding)
            ) {
                screens.forEach { screen ->
                    ScreensPermanentDrawerItem(
                        screen = screen,
                        navController = navController,
                    )
                }
            }
        }) {
        AppNavHost(
            navController = navController,
            uiState = uiState,
            contentPadding = contentPadding,
            onHomeScreenCardClick = onHomeScreenCardClick,
            contentType = contentType,
            onRetryButtonClick = onRetryButtonClick
        )
    }
}


//List and detail screen for big screen monitors
@Composable
fun FilmListAndDetailScreen(
    allFilms: List<Film>,
    currentFilm: Film,
    onHomeScreenCardClick: (Film) -> Unit,
) {
    Row {
        HomeScreenColumn(
            onHomeScreenCardClick = onHomeScreenCardClick,
            allFilms = allFilms,
            modifier = Modifier.weight(1f)
        )
        DetailScreen(
            film = currentFilm,
            onBackPressed = { },
            modifier = Modifier.weight(1f)
        )
    }
}


