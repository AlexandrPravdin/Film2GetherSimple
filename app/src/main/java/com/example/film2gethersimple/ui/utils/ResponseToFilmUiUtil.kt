package com.example.film2gethersimple.ui.utils

import com.example.film2gethersimple.data.model.Item
import com.example.film2gethersimple.data.model.Response
import com.example.film2gethersimple.ui.models.Film


//Transform response to list of Film
fun Response.convertDataToUi(
): List<Film> {
    val filmList: MutableList<Film> = mutableListOf()
    this.items.forEach { item ->
        filmList.add(item.convertResponseItemToFilmUiItem())
    }
    return filmList
}

fun Item.convertResponseItemToFilmUiItem(): Film {
    return Film(
        name = this.volumeInfo.title,
        imageLink = this.volumeInfo.imageLinks.thumbnail.replace("http", "https"),
        description = this.volumeInfo.description,
        publisherDate = this.volumeInfo.publishedDate,
        categories = this.volumeInfo.categories
    )
}