package com.example.film2gethersimple.ui.screens

import androidx.lifecycle.ViewModel
import com.example.film2gethersimple.R
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.data.local.LocalAccountDataProvider.account
import com.example.film2gethersimple.data.local.LocalFilmsDataProvider.allFilms
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class FilmViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FilmUiState())
    val uiState: StateFlow<FilmUiState> = _uiState


    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        _uiState.value = FilmUiState(
            currentSelectedFilm = allFilms[0],
            allFilms = allFilms,
            account = account,
            topAppBarTitle = (R.string.films)
        )
    }

    //Going to the detail screen
    fun updateDetailsScreenStates(film: Film) {
        _uiState.update {
            it.copy(
                currentSelectedFilm = film,
                isShowingHomePage = false,
                topAppBarTitle = film.name
            )
        }
    }

    fun goingToHomePage() {
        _uiState.update {
            it.copy(
                isShowingHomePage = true,
                topAppBarTitle = R.string.films
            )
        }
    }

    fun goingToAccountPage() {
        _uiState.update {
            it.copy(
                topAppBarTitle = R.string.account_information
            )
        }
    }

}