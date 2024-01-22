package com.example.film2gethersimple.ui.models

data class Film(
    val name: String,
    val imageLink: String,
    val description: String?,
    val publisherDate: String,
    val categories: List<String>
)

