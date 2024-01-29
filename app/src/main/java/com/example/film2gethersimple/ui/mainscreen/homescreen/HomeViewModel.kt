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


    fun shareCurrentBook(context: Context) {
        when (uiState) {
            is HomeUiState.Success -> {
                val currentFilm = (uiState as HomeUiState.Success).currentSelectedItem
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Just look at the \"${currentFilm.name}\"!\n\n" + currentFilm.linkToGoogleBooks
                    )
                    type = "text/plan"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }

            is HomeUiState.Loading -> {
                val toast = Toast.makeText(context, "We are just loading!", Toast.LENGTH_SHORT)
                toast.show()
            }

            is HomeUiState.Error -> {
                val toast = Toast.makeText(context, "Some problems", Toast.LENGTH_SHORT)
                toast.show()
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