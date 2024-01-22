package com.example.film2gethersimple.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.NavigationScreens


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