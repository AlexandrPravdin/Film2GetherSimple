package com.example.film2gethersimple.data.network

import com.example.film2gethersimple.data.FilmRepository

class FilmRemoteRepository(
    private val filmApiService: FilmApiService //Network data source
) : FilmRepository {
    override suspend fun getFilms(): Response = filmApiService.getPhotos()
}