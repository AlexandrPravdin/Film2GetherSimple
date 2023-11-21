package com.example.film2gethersimple.ui

import androidx.lifecycle.ViewModel
import com.example.film2gethersimple.data.Film
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
            allFilms = allFilms
        )
    }

    //Going to the detail screen
    fun updateDetailsScreenStates(film: Film) {
        _uiState.update {
            it.copy(
                currentSelectedFilm = film,
                isShowingHomePage = false
            )
        }
    }

    fun goingToHomePage() {
        _uiState.update {
            it.copy(
                isShowingHomePage = true
            )
        }
    }

}