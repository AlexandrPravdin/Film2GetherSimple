package com.example.film2gethersimple.ui

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.film2gethersimple.FilmApplication
import com.example.film2gethersimple.ui.mainscreen.homescreen.FilmViewModel
import com.example.film2gethersimple.ui.theme.AppThemeViewModel

object AppViewModelProvider {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    val Factory = viewModelFactory {
        initializer {
            FilmViewModel(
                appApplication().container.filmRepository
            )
        }
        initializer {
            AppThemeViewModel(
                appApplication().userPreferencesRepository
            )
        }
    }
}


fun CreationExtras.appApplication(): FilmApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FilmApplication)