package com.example.film2gethersimple.ui.mainscreen.homescreen

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.film2gethersimple.FilmApplication
import com.example.film2gethersimple.data.FilmRepository
import com.example.film2gethersimple.data.local.LocalAccountDataProvider.account

import com.example.film2gethersimple.ui.models.Account
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.ui.utils.convertDataToUi
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface FilmUiState {
    data class Success(
        val response: List<Film>,
        //Selected Film
        var currentSelectedItem: Film,

        //User's Account
        val account: Account?,

        var topAppBarTitle: String = ""
    ) : FilmUiState
    object Error : FilmUiState
    object Loading : FilmUiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class FilmViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    var uiState: FilmUiState by mutableStateOf(
        FilmUiState.Loading
    )
        private set

    init {
        initializeUIState()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun initializeUIState() {
        uiState = FilmUiState.Loading
        viewModelScope.launch {
            uiState = try {
                val response = filmRepository.getFilms().convertDataToUi()
                FilmUiState.Success(
                    response = response,
                    account = account,
                    currentSelectedItem = response[0],
                    topAppBarTitle = "Films"
                )
            } catch (e: IOException) {
                FilmUiState.Error
            } catch (e: HttpException) {
                FilmUiState.Error
            }
        }
    }

    //Going to the detail screen
    fun updateDetailsScreenStates(film: Film) {
        uiState = (uiState as FilmUiState.Success).copy(
            currentSelectedItem = film,
            topAppBarTitle = film.name,
        )
    }


//Узнать, как обновлять переменные


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FilmApplication)
                val filmRepository = application.container.filmRepository
                FilmViewModel(filmRepository = filmRepository)
            }
        }
    }

}