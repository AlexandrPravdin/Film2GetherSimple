package com.example.film2gethersimple.ui

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.film2gethersimple.FilmApplication
import com.example.film2gethersimple.ui.mainscreen.homescreen.FilmViewModel

object AppViewModelProvider {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {
        initializer {

            FilmViewModel(
                this.createSavedStateHandle(),
                appApplication ().container.filmRepository
            )
        }
    }
}

/*
companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application =
                (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FilmApplication)
            val filmRepository = application.container.filmRepository
            FilmViewModel(filmRepository = filmRepository)
        }
    }
}*/

fun CreationExtras.appApplication(): FilmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FilmApplication)