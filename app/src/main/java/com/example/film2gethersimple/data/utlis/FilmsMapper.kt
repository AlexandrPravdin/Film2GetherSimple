package com.example.film2gethersimple.data.utlis

import com.example.film2gethersimple.data.network.Item
import com.example.film2gethersimple.ui.models.Film


fun Item.asEntity() = Film(
    name = volumeInfo.title,
    imageLink = volumeInfo.imageLinks.thumbnail.replace("http", "https"),
    description = volumeInfo.description,
    publisherDate = volumeInfo.publishedDate,
    categories = volumeInfo.categories,
    linkToGoogleBooks = volumeInfo.canonicalVolumeLink
)
