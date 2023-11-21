@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.film2gethersimple.ui

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.film2gethersimple.data.Film

//App at all
@Composable
fun FilmApp(
    modifier: Modifier = Modifier,
) {
    val viewModel: FilmViewModel = viewModel()
    val homeUiState = viewModel.uiState.collectAsState().value
    //Film screen with Home and Details screen
    Scaffold(topBar = {
        topAppBar(title = homeUiState.currentSelectedFilm.name,
            isShowingDetails = !homeUiState.isShowingHomePage,
            onBackButtonClicked = { viewModel.goingToHomePage() }
        )
    }) { innerPadding ->
        HomeFilmScreen(
            uiState = homeUiState,
            onHomeScreenCardClick = { film: Film -> viewModel.updateDetailsScreenStates(film) },
            onDetailsBackScreenPressed = { viewModel.goingToHomePage() },
            contentPadding = innerPadding
        )
    }
}


@Composable
fun topAppBar(
    @StringRes title: Int,
    isShowingDetails: Boolean = false,
    onBackButtonClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = title))
        },
        navigationIcon = if (isShowingDetails) {
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