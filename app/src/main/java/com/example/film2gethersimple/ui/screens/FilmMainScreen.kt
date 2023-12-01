package com.example.film2gethersimple.ui.screens

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.BottomNavigationScreens
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.ui.utils.ContentType
import com.example.film2gethersimple.ui.utils.NavigationType

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
    val currentScreen = BottomNavigationScreens.valueOf(
        backStackEntry?.destination?.route ?: BottomNavigationScreens.HomeScreen.name
    )
    //All screens
    val screens = listOf(
        BottomNavigationScreens.HomeScreen,
        BottomNavigationScreens.AccountScreen,
    )
    Scaffold(
        modifier = modifier,
        topBar = {
            //Top app bar have the another functional, forEach screen the forEach function
            when (currentScreen) {
                //Top app bar for Home Screen
                BottomNavigationScreens.HomeScreen -> {
                    TopFilmAppBar(
                        title = uiState.topAppBarTitle,
                        isShowingBackButton = !uiState.isShowingHomePage && contentType != ContentType.ListAndDetails,
                        onBackButtonClicked = onBackButtonClicked //On button clicked should to refactor
                    )
                }
                //Top app bar for AccountScreen
                BottomNavigationScreens.AccountScreen -> {
                    TopFilmAppBar(title = R.string.account_screen,
                        isShowingBackButton = false,
                        onBackButtonClicked = {})
                }
            }
        },

        bottomBar = {
            //Bottom bar showing only when Navigation Type is BOTTOM_NAVIGATION
            if (navigationType == NavigationType.BOTTOM_NAVIGATION && uiState.isShowingHomePage) {
                BottomFilmAppBar(
                    navController = navController,
                    screens = screens,
                )
            }
        },
    ) { innerPadding ->
        //Row for rail and exp
        if (navigationType == NavigationType.NAVIGATION_RAIL && uiState.isShowingHomePage) {
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

@Composable
fun AppNavHost(
    navController: NavHostController,
    uiState: FilmUiState,
    onHomeScreenCardClick: (Film) -> Unit,
    goingHomeFunction: () -> Unit,
    contentType: ContentType,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.HomeScreen.name,
        modifier = modifier.padding(contentPadding),
    ) {
        composable(BottomNavigationScreens.HomeScreen.name) {
            HomeFilmScreen(
                uiState = uiState,
                onHomeScreenCardClick = { film: Film ->
                    onHomeScreenCardClick(film)
                },
                onDetailsBackScreenPressed = goingHomeFunction,
                contentType = contentType
            )
        }
        composable(BottomNavigationScreens.AccountScreen.name) {
            AccountScreen(
                account = uiState.account
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
    screens: List<BottomNavigationScreens>,
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
                        screen.icon,
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
    screens: List<BottomNavigationScreens>,
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
                        screen.icon,
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
    screens: List<BottomNavigationScreens>,
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
    screen: BottomNavigationScreens,
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
                    screen.icon, contentDescription = screen.name,
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

