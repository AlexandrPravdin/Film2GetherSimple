package com.example.film2gethersimple.ui

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.NavigationScreens
import com.example.film2gethersimple.ui.mainscreen.homescreen.HomeUiState
import com.example.film2gethersimple.ui.mainscreen.homescreen.FilmViewModel
import com.example.film2gethersimple.ui.mainscreen.listanddetailsscreen.FilmPermanentDrawer
import com.example.film2gethersimple.ui.navigation.AppNavHost
import com.example.film2gethersimple.ui.navigation.BottomFilmAppBar
import com.example.film2gethersimple.ui.navigation.FilmNavigationRail
import com.example.film2gethersimple.ui.utils.ContentType
import com.example.film2gethersimple.ui.utils.NavigationType

private const val TAG = "FilmApp"

//App at all


//ToDo - вынести вью модел в нав хост
//ToDo - спросить насчет того, Где должен находиться top app bar.
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun FilmApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    //viewModel architecture
    val viewModel: FilmViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val context: Context = LocalContext.current

    //navigation logic
    val navController = rememberNavController()


    //ScreenSize logic
    val navigationType: NavigationType
    val contentType: ContentType
    Log.i(TAG, "Film App Started Screen is $windowSize")
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
                    if (contentType == ContentType.ListOnly) {
                        TopFilmAppBar(title = stringResource(id = R.string.home_screen),
                            isShowingBackButton = false,
                            onBackButtonClicked = {} //On button clicked should to refactor
                        )
                    } else {
                        TopFilmAppBar(title = stringResource(NavigationScreens.HomeScreen.title),
                            isShowingBackButton = false,
                            onBackButtonClicked = {},
                            isShowingShareButton = true,
                            onShareButtonClicked = { viewModel.shareCurrentBook(context = context) })

                    }
                }
                //Top app bar for AccountScreen
                NavigationScreens.AccountScreen -> {
                    TopFilmAppBar(title = stringResource(NavigationScreens.AccountScreen.title),
                        isShowingBackButton = false,
                        onBackButtonClicked = {})
                }
                //Top app bar DetailsScreen
                NavigationScreens.DetailsScreen -> {
                    TopFilmAppBar(title = (viewModel.uiState as HomeUiState.Success).currentSelectedItem.name,
                        isShowingBackButton = true,
                        onBackButtonClicked = {
                            navController.popBackStack()
                        },
                        isShowingShareButton = true,
                        onShareButtonClicked = { viewModel.shareCurrentBook(context = context) })
                }
                //Top app bar for ListAndDetailsScreen
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
        when (navigationType) {
            NavigationType.NAVIGATION_RAIL/* && currentScreen != NavigationScreens.DetailsScreen*/ -> {
                Row {
                    FilmNavigationRail(
                        navController = navController,
                        screens = screens,
                        contentPadding = innerPadding,
                    )
                    AppNavHost(navController = navController,
                        uiState = viewModel.uiState,
                        contentPadding = innerPadding,
                        onHomeScreenCardClick = { viewModel.updateDetailsScreenStates(it) },
                        contentType = contentType,
                        onRetryButtonClick = { viewModel.initializeUIState() })
                }
            }

            NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
                FilmPermanentDrawer(navController = navController,
                    screens = listOf(
                        NavigationScreens.HomeScreen, NavigationScreens.AccountScreen
                    ),
                    contentPadding = innerPadding,
                    uiState = viewModel.uiState,
                    onHomeScreenCardClick = { viewModel.updateDetailsScreenStates(it) },
                    contentType = contentType,
                    onRetryButtonClick = { viewModel.initializeUIState() })
            }

            else -> {
                AppNavHost(navController = navController,
                    uiState = viewModel.uiState,
                    contentPadding = innerPadding,
                    onHomeScreenCardClick = { viewModel.updateDetailsScreenStates(it) },
                    contentType = contentType,
                    onRetryButtonClick = { viewModel.initializeUIState() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopFilmAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit = {},
    isShowingBackButton: Boolean = false,
    onShareButtonClicked: () -> Unit = {},
    isShowingShareButton: Boolean = false,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title, overflow = TextOverflow.Ellipsis, maxLines = 1
            )
        },
        navigationIcon = if (isShowingBackButton) {
            {
                IconButton(
                    onClick = onBackButtonClicked
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = Icons.Filled.ArrowBack.name)
                }
            }
        } else {
            {}
        },
        actions = if (isShowingShareButton) {
            {
                IconButton(
                    onClick = onShareButtonClicked
                ) {
                    Icon(Icons.Filled.Share, contentDescription = Icons.Filled.Share.name)
                }
            }
        } else {
            {}
        },
    )
}
