package com.example.film2gethersimple.data.network

import retrofit2.http.GET


//https://www.googleapis.com/books/v1/volumes?q=games

//
interface FilmApiService {
    @GET("volumes?q=game")
    suspend fun getPhotos(): Response
}