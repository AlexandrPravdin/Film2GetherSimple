package com.example.film2gethersimple.ui.mainscreen.listanddetailsscreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.NavigationScreens
import com.example.film2gethersimple.ui.TopFilmAppBar
import com.example.film2gethersimple.ui.mainscreen.detailscreen.DetailColumn
import com.example.film2gethersimple.ui.mainscreen.detailscreen.shareCurrentBook
import com.example.film2gethersimple.ui.mainscreen.homescreen.HomeScreenColumn
import com.example.film2gethersimple.ui.mainscreen.homescreen.HomeUiState
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.ui.navigation.AppNavHost
import com.example.film2gethersimple.ui.navigation.ScreensPermanentDrawerItem
import com.example.film2gethersimple.ui.utils.ContentType


@Composable
fun FilmPermanentDrawer(
    navController: NavHostController,
    screens: List<NavigationScreens>,
    uiState: HomeUiState,
    onHomeScreenCardClick: (Film) -> Unit,
    contentType: ContentType,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onRetryButtonClick: () -> Unit,
) {
    PermanentNavigationDrawer(modifier = modifier, drawerContent = {
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
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopFilmAppBar(title = stringResource(NavigationScreens.HomeScreen.title),
                isShowingBackButton = false,
                isShowingShareButton = true,
                onShareButtonClicked = {
                    shareCurrentBook(film = currentFilm, context = context)
                })
        }
    ) { paddingValues ->
        Row {
            HomeScreenColumn(
                onHomeScreenCardClick = onHomeScreenCardClick,
                allFilms = allFilms,
                modifier = Modifier
                    .weight(1f)
                    .padding(paddingValues)
            )
            DetailColumn(
                film = currentFilm,
                modifier = Modifier
                    .weight(1f)
                    .padding(paddingValues)
            )
        }

    }
}


