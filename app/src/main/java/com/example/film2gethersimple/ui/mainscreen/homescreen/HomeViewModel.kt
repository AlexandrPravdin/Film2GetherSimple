package com.example.film2gethersimple.ui.mainscreen.homescreen

import android.content.Context
import android.content.Intent
import android.net.http.HttpException
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film2gethersimple.data.FilmRepository
import com.example.film2gethersimple.data.local.LocalAccountDataProvider.account
import com.example.film2gethersimple.ui.models.Account
import com.example.film2gethersimple.ui.models.Film
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface HomeUiState {
    data class Success(
        val response: List<Film>,
        //Selected Film
        var currentSelectedItem: Film,

        //User's Account
        val account: Account?,

        var topAppBarTitle: String = ""
    ) : HomeUiState

    data class Error(
        val errorName: String?,
    ) : HomeUiState

    object Loading : HomeUiState
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class FilmViewModel(
    savedStateHandle: SavedStateHandle,
    private val filmRepository: FilmRepository,
) : ViewModel() {

    var uiState: HomeUiState by mutableStateOf(
        HomeUiState.Loading
    )
        private set

    init {
        initializeUIState()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun initializeUIState() {
        uiState = HomeUiState.Loading
        viewModelScope.launch {
            uiState = try {
                val response = filmRepository.getFilms()
                HomeUiState.Success(
                    response = response,
                    account = account,
                    currentSelectedItem = response[0],
                    topAppBarTitle = "Films"
                )
            } catch (e: IOException) {
                HomeUiState.Error(errorName = e.message)
            } catch (e: HttpException) {
                HomeUiState.Error(errorName = e.message)
            }
        }
    }

    //Going to the detail screen
    fun updateDetailsScreenStates(film: Film) {
        uiState = (uiState as HomeUiState.Success).copy(
            currentSelectedItem = film,
            topAppBarTitle = film.name,
        )
    }

}