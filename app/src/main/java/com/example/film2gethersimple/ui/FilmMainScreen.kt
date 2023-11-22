package com.example.film2gethersimple.ui

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.film2gethersimple.data.BottomNavigationScreens
import com.example.film2gethersimple.data.Film
import com.example.film2gethersimple.ui.utils.ContentType
import com.example.film2gethersimple.ui.utils.NavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmMainScreen(
    uiState: FilmUiState,
    viewModel: FilmViewModel,
    navController: NavHostController,
    navigationType: NavigationType,
    contentType: ContentType,
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
                        isShowingBackButton = !uiState.isShowingHomePage /*&& contentType == ContentType.ListOnly*/,
                        onBackButtonClicked = { viewModel.goingToHomePage() }//On button clicked should to refactor
                    )
                }
                //Top app bar for AccountScreen
                BottomNavigationScreens.AccountScreen -> {
                    TopFilmAppBar(title = uiState.topAppBarTitle,
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
                    viewModel = viewModel,
                    screens = screens
                )
            }
        },
    ) { innerPadding ->
        //Row for rail and exp
        if (navigationType == NavigationType.NAVIGATION_RAIL && uiState.isShowingHomePage) {
            Row {
                FilmNavigationRail(
                    navController = navController,
                    viewModel = viewModel,
                    screens = screens,
                    contentPadding = innerPadding
                )
                AppNavHost(
                    navController = navController,
                    uiState = uiState,
                    viewModel = viewModel,
                    contentPadding = innerPadding
                )
            }
        } else if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER && uiState.isShowingHomePage) {
            FilmPermanentDrawer(
                navController = navController,
                viewModel = viewModel,
                screens = screens,
                contentPadding = innerPadding,
                uiState = uiState
            )
        } else {
            AppNavHost(
                navController = navController,
                uiState = uiState,
                viewModel = viewModel,
                contentPadding = innerPadding
            )
        }

    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    uiState: FilmUiState,
    viewModel: FilmViewModel,


    ) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.HomeScreen.name,
        modifier = modifier.padding(contentPadding)
    ) {
        composable(BottomNavigationScreens.HomeScreen.name) {
            HomeFilmScreen(
                uiState = uiState,
                onHomeScreenCardClick = { film: Film ->
                    viewModel.updateDetailsScreenStates(
                        film
                    )
                },
                onDetailsBackScreenPressed = { viewModel.goingToHomePage() },
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
        navigationIcon = if (isShowingBackButton) {
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
    viewModel: FilmViewModel,
    screens: List<BottomNavigationScreens>,
    modifier: Modifier = Modifier
) {
    BottomAppBar(modifier = modifier.height(60.dp)) {
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
                    when (screen) {
                        BottomNavigationScreens.HomeScreen -> {
                            viewModel.goingToHomePage()
                        }

                        BottomNavigationScreens.AccountScreen -> {
                            viewModel.goingToAccountPage()
                        }
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
    viewModel: FilmViewModel,
    screens: List<BottomNavigationScreens>,
    contentPadding: PaddingValues = PaddingValues(0.dp),

    ) {
    NavigationRail(modifier = modifier.padding(contentPadding)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            NavigationRailItem(
                modifier = Modifier.padding(bottom = 8.dp),
                selected = currentDestination?.hierarchy?.any { it.route == screen.name } == true,
                onClick = {
                    navController.navigate(screen.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                    when (screen) {
                        BottomNavigationScreens.HomeScreen -> {
                            viewModel.goingToHomePage()
                        }

                        BottomNavigationScreens.AccountScreen -> {
                            viewModel.goingToAccountPage()
                        }
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
    viewModel: FilmViewModel,
    screens: List<BottomNavigationScreens>,
    uiState: FilmUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    PermanentNavigationDrawer(
        modifier = modifier,
        drawerContent = {
            PermanentDrawerSheet(
                Modifier
                    .width(200.dp)
                    .padding(contentPadding)
            ) {
                screens.forEach { screen ->
                    ScreensPermanentDrawerItem(
                        screen = screen,
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }) {
        AppNavHost(
            navController = navController,
            uiState = uiState,
            viewModel = viewModel,
            contentPadding = contentPadding
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreensPermanentDrawerItem(
    screen: BottomNavigationScreens,
    navController: NavHostController,
    viewModel: FilmViewModel,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationDrawerItem(
        modifier = modifier.padding(8.dp),
        label = {
            Row {
                Icon(
                    screen.icon, contentDescription = screen.name,
                    modifier = Modifier.padding(horizontal = 8.dp)
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
            when (screen) {
                BottomNavigationScreens.HomeScreen -> {
                    viewModel.goingToHomePage()
                }

                BottomNavigationScreens.AccountScreen -> {
                    viewModel.goingToAccountPage()
                }
            }
        }
    )
}

