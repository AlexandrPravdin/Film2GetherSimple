package com.example.film2gethersimple.data.network

import com.example.film2gethersimple.data.FilmRepository
import com.example.film2gethersimple.data.model.Response

class FilmRemoteRepository(
    private val filmApiService: FilmApiService //Network data source
) : FilmRepository {
    override suspend fun getFilms(): Response = filmApiService.getPhotos()
}