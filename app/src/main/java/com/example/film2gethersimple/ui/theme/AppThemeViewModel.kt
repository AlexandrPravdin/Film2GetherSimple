package com.example.film2gethersimple.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.film2gethersimple.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppThemeViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {
    val uiState: StateFlow<ThemeUiState> =
        repository.isDynamicTheme.map { isDynamicTheme ->
            ThemeUiState(isDynamicTheme)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemeUiState()
        )


    fun selectLayout(isDynamicTheme: Boolean){
        viewModelScope.launch {
            repository.saveThemePreferences(isDynamicTheme)
        }
    }

    fun swapColorScheme(){
        viewModelScope.launch {
            repository.saveThemePreferences(!uiState.value.isDynamicTheme)
        }
    }

}

data class ThemeUiState(
    val isDynamicTheme: Boolean = true
)