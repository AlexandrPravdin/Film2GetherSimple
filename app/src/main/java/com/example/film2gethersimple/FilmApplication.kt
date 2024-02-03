package com.example.film2gethersimple

import android.app.Application
import com.example.film2gethersimple.data.AppContainer
import com.example.film2gethersimple.data.AppDataContainer



class FilmApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(/*context = this*/)
    }
}