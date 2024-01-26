package com.example.film2gethersimple.data

import com.example.film2gethersimple.data.network.Item
import com.example.film2gethersimple.data.network.Response
import com.example.film2gethersimple.ui.models.Film

//DomainLayer
class GetFilmUseCase(
    private val filmRepository: FilmRepository
) {

    suspend fun getFormattedFilms(): List<Film> {
        val response = filmRepository.getFilms()
        return convertDataToUi(response = response)
    }
//    Transform response to list of Film

//    This thing can have the name mapper an can be a external function of data class.
    /*
    fun NetworkAuthor.asEntity() = AuthorEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    twitter = twitter,
    mediumPage = mediumPage,
    bio = bio,
)*/
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
        val volumeInfo = item.volumeInfo
        return Film(
            name = volumeInfo.title,
            imageLink = volumeInfo.imageLinks.thumbnail.replace("http", "https"),
            description = volumeInfo.description,
            publisherDate = volumeInfo.publishedDate,
            categories = volumeInfo.categories,
            linkToGoogleBooks = volumeInfo.canonicalVolumeLink
        )
    }
}