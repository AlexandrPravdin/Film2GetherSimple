package com.example.film2gethersimple.data.network

import com.example.film2gethersimple.data.FilmRepository
import com.example.film2gethersimple.data.utlis.asEntity
import com.example.film2gethersimple.ui.models.Film

class FilmRemoteRepository(
    private val filmApiService: FilmApiService //Network data source
) : FilmRepository {
    override suspend fun getFilms(): List<Film> {
        val response = filmApiService.getPhotos()
        val filmList: MutableList<Film> = mutableListOf()
        response.items.forEach { item ->
            filmList.add(item.asEntity())
        }
        return filmList
    }
}
