package com.example.film2gethersimple.ui.screens

import com.example.film2gethersimple.ui.models.Account
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.data.local.LocalAccountDataProvider.default
import com.example.film2gethersimple.data.local.LocalFilmsDataProvider.defaultFilm

data class FilmUiState(
    //Selected Film
    val currentSelectedFilm: Film = defaultFilm,
    //Boolean flag of showing home page
    val isShowingHomePage: Boolean = true,
    //Films, that shown in homepage and information about it
    val allFilms: List<Film> = emptyList(),
    //User's Account
    val account: Account = default,

    val topAppBarTitle: Int = -1
)