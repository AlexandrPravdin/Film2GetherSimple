package com.example.film2gethersimple.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.film2gethersimple.data.BottomNavigationScreens
import com.example.film2gethersimple.data.Film


//App at all
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmApp(
    modifier: Modifier = Modifier,
) {
    //viewModel architecture
    val viewModel: FilmViewModel = viewModel()
    val homeUiState = viewModel.uiState.collectAsState().value

    //navigation architecture
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BottomNavigationScreens.valueOf(
        backStackEntry?.destination?.route ?: BottomNavigationScreens.HomeScreen.name
    )
    val screens = listOf(
        BottomNavigationScreens.HomeScreen,
        BottomNavigationScreens.AccountScreen,
    )

    //Film screen with Home and Details screen
    Scaffold(
        topBar = {
            when (currentScreen) {
                BottomNavigationScreens.HomeScreen -> {
                    TopFilmAppBar(
                        title = homeUiState.topAppBarTitle,
                        isShowingBackButton = !homeUiState.isShowingHomePage,
                        onBackButtonClicked = { viewModel.goingToHomePage() }//On button clicked should to refactor
                    )
                }

                BottomNavigationScreens.AccountScreen -> {
                    TopFilmAppBar(title = homeUiState.topAppBarTitle,
                        isShowingBackButton = false,
                        onBackButtonClicked = {})
                }
            }
        },
        bottomBar = {
            BottomFilmAppBar(
                navController = navController,
                viewModel = viewModel,
                screens = screens
            )
        }

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationScreens.HomeScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavigationScreens.HomeScreen.name) {
                HomeFilmScreen(
                    uiState = homeUiState,
                    onHomeScreenCardClick = { film: Film -> viewModel.updateDetailsScreenStates(film) },
                    onDetailsBackScreenPressed = { viewModel.goingToHomePage() },
                )
            }
            composable(BottomNavigationScreens.AccountScreen.name) {
                AccountScreen(
                    account = homeUiState.account
                )
            }
        }

    }
}

@Composable
fun BottomFilmAppBar(
    navController: NavHostController,
    viewModel: FilmViewModel,
    screens: List<BottomNavigationScreens>
) {

    BottomAppBar(modifier = Modifier.height(60.dp)) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopFilmAppBar(
    @StringRes title: Int,
    isShowingBackButton: Boolean = false,
    onBackButtonClicked: () -> Unit,
) {
    TopAppBar(
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