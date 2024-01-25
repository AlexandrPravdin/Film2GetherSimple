package com.example.film2gethersimple.data

import com.example.film2gethersimple.data.model.Item
import com.example.film2gethersimple.data.model.Response
import com.example.film2gethersimple.ui.models.Film

//DomainLayer
class GetFilmUseCase(
    private val filmRepository: FilmRepository
) {

    suspend fun getFormattedFilms(): List<Film> {
        val response = filmRepository.getFilms()
        return convertDataToUi(response = response)
    }

    //Transform response to list of Film
    private fun convertDataToUi(
        response: Response,
    ): List<Film> {
        val filmList: MutableList<Film> = mutableListOf()
        response.items.forEach { item ->
            filmList.add(convertResponseItemToFilmUiItem(item))
        }
        return filmList
    }

    private fun convertResponseItemToFilmUiItem(
        item: Item
    ): Film {
        return Film(
            name = item.volumeInfo.title,
            imageLink = item.volumeInfo.imageLinks.thumbnail.replace("http", "https"),
            description = item.volumeInfo.description,
            publisherDate = item.volumeInfo.publishedDate,
            categories = item.volumeInfo.categories
        )
    }
}