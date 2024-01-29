package com.example.film2gethersimple.data

import com.example.film2gethersimple.ui.models.Film

interface FilmRepository {
    suspend fun getFilms(): List<Film>
}

