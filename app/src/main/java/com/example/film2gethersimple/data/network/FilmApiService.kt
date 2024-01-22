package com.example.film2gethersimple.data.network

import com.example.film2gethersimple.data.model.Response
import retrofit2.http.GET


//https://www.googleapis.com/books/v1/volumes?q=films
interface FilmApiService {
    @GET("volumes?q=game")
    suspend fun getPhotos(): Response
}