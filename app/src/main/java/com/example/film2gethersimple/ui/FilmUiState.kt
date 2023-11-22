package com.example.film2gethersimple.ui

import com.example.film2gethersimple.data.Account
import com.example.film2gethersimple.data.Film
import com.example.film2gethersimple.data.local.LocalFilmsDataProvider.defaultFilm
import com.example.film2gethersimple.data.local.LocalAccountDataProvider.default

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