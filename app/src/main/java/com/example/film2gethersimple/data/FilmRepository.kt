package com.example.film2gethersimple.data

import com.example.film2gethersimple.data.model.Response

interface FilmRepository {
    suspend fun getFilms(): Response
}

