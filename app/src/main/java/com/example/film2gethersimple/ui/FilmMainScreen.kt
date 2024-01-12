package com.example.film2gethersimple.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.NavigationScreens
import com.example.film2gethersimple.data.Genres
import com.example.film2gethersimple.ui.detailscreen.DetailScreen
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.ui.utils.ContentType
import com.example.film2gethersimple.ui.utils.NavigationType
import com.example.film2gethersimple.ui.navigation.AppNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmMainScreen(
    uiState: FilmUiState,
    navController: NavHostController,
    navigationType: NavigationType,
    contentType: ContentType,
    onBackButtonClicked: () -> Unit,
    onHomeScreenCardClick: (Film) -> Unit,
    modifier: Modifier = Modifier,
) {
    //Current screen
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NavigationScreens.valueOf(
        backStackEntry?.destination?.route ?: NavigationScreens.HomeScreen.name
    )
    //All screens
    val screens = listOf(
        NavigationScreens.HomeScreen,
        NavigationScreens.AccountScreen,
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            //Top app bar have the another functional, forEach screen the forEach function
            when (currentScreen) {
                //Top app bar for Home Screen
                NavigationScreens.HomeScreen -> {
                    TopFilmAppBar(
                        title = R.string.account_screen,
                        isShowingBackButton = false,
                        onBackButtonClicked = {} //On button clicked should to refactor
                    )
                }
                //Top app bar for AccountScreen
                NavigationScreens.AccountScreen -> {
                    TopFilmAppBar(title = R.string.account_screen,
                        isShowingBackButton = false,
                        onBackButtonClicked = {})
                }
                //Top app bar DetailsScreen
                NavigationScreens.DetailsScreen -> {
                    TopFilmAppBar(
                        title = uiState.currentSelectedFilm.name,
                        isShowingBackButton = true,
                        onBackButtonClicked = {
                            navController.popBackStack()
                        },
                    )
                }
            }
        },

        bottomBar = {
            //Bottom bar showing only when Navigation Type is BOTTOM_NAVIGATION
            if (navigationType == NavigationType.BOTTOM_NAVIGATION && currentScreen != NavigationScreens.DetailsScreen) {
                BottomFilmAppBar(
                    navController = navController,
                    screens = screens,
                )
            }
        },
    ) { innerPadding ->
        //Row for rail and exp
        if (navigationType == NavigationType.NAVIGATION_RAIL && currentScreen != NavigationScreens.DetailsScreen) {
            Row {
                FilmNavigationRail(
                    navController = navController,
                    screens = screens,
                    contentPadding = innerPadding,
                )
                AppNavHost(
                    navController = navController,
                    uiState = uiState,
                    contentPadding = innerPadding,
                    onHomeScreenCardClick = onHomeScreenCardClick,
                    goingHomeFunction = onBackButtonClicked,
                    contentType = contentType
                )
            }
        } else if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
            FilmPermanentDrawer(
                navController = navController,
                screens = screens,
                contentPadding = innerPadding,
                uiState = uiState,
                goingHomeFunction = onBackButtonClicked,
                onHomeScreenCardClick = onHomeScreenCardClick,
                contentType = contentType,
            )
        } else {
            AppNavHost(
                navController = navController,
                uiState = uiState,
                contentPadding = innerPadding,
                goingHomeFunction = onBackButtonClicked,
                onHomeScreenCardClick = onHomeScreenCardClick,
                contentType = contentType
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopFilmAppBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    isShowingBackButton: Boolean = false,
    onBackButtonClicked: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = title))
        },
        navigationIcon =
        if (isShowingBackButton) {
            {
                IconButton(
                    onClick = onBackButtonClicked
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back Icon")
                }
            }
        } else {
            {}
        },
    )
}

@Composable
fun BottomFilmAppBar(
    navController: NavHostController,
    screens: List<NavigationScreens>,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier.height(dimensionResource(id = R.dimen.bottom_app_bar_size))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true,
                onClick = {
                    navController.navigate(screen.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        screen.icon!!,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}


@Composable
fun FilmNavigationRail(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    screens: List<NavigationScreens>,
    contentPadding: PaddingValues = PaddingValues(0.dp),

    ) {
    NavigationRail(modifier = modifier.padding(contentPadding)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            NavigationRailItem(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.medium)),
                selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true,
                onClick = {
                    navController.navigate(screen.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        screen.icon!!,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmPermanentDrawer(
    navController: NavHostController,
    screens: List<NavigationScreens>,
    uiState: FilmUiState,
    goingHomeFunction: () -> Unit,
    onHomeScreenCardClick: (Film) -> Unit,
    contentType: ContentType,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
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
            goingHomeFunction = goingHomeFunction,
            onHomeScreenCardClick = onHomeScreenCardClick,
            contentType = contentType,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreensPermanentDrawerItem(
    screen: NavigationScreens,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationDrawerItem(
        modifier = modifier.padding(dimensionResource(id = R.dimen.medium)),
        label = {
            Row {
                Icon(
                    screen.icon!!, contentDescription = screen.name,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.medium))
                )
                Text(text = stringResource(id = screen.title))
            }
        },
        selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true,
        onClick = {
            navController.navigate(screen.name) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
            }
        }
    )
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

@Preview
@Composable
fun FilmListAndDetailScreenPreview() {
    val viewModel: FilmViewModel = viewModel()
    val homeUiState = viewModel.uiState.collectAsState().value
    FilmListAndDetailScreen(
        homeUiState.allFilms,
        homeUiState.currentSelectedFilm,
        onHomeScreenCardClick = { viewModel.updateDetailsScreenStates(it) }
    )
}

@Preview
@Composable
fun FilmList() {
    HomeScreenColumn(
        onHomeScreenCardClick = {},
        allFilms = listOf<Film>(
            Film(
                name = R.string.the_shawshank_redemption,
                image = R.drawable.shawshank_image2,
                description = R.string.the_shawshank_redemption_description,
                genres = setOf(Genres.Drama),
                iMDbRate = 9.2,
            ),
            Film(
                name = R.string.the_godfather,
                image = R.drawable.godfather_image,
                description = (R.string.the_godfather_description),
                genres = setOf(Genres.Drama),
                iMDbRate = 9.2,
            ),
            Film(
                name = R.string.the_dark_knight,
                image = R.drawable.the_dark_knight_image,
                description = (R.string.the_dark_knight_description),
                genres = setOf(Genres.Drama, Genres.Crime),
                iMDbRate = 9.0,
            )
        )
    )
}

