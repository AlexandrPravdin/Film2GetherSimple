package com.example.film2gethersimple.data.network

import com.example.film2gethersimple.data.FilmRepository
import com.example.film2gethersimple.data.model.Response

class NetworkFilmRepository(
    private val filmApiService: FilmApiService
) : FilmRepository {
    override suspend fun getFilms(): Response = filmApiService.getPhotos()
}