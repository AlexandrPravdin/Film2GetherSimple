package com.example.film2gethersimple.data.preferences

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository (
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val IS_DYNAMIC_THEME = booleanPreferencesKey("is_dynamic_theme")
        const val TAG = "UserPreferencesRepo"
    }

    val isDynamicTheme: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_DYNAMIC_THEME] ?: true
        }

        suspend fun saveThemePreferences(isDynamicTheme: Boolean){
            dataStore.edit { preferences ->
                preferences[IS_DYNAMIC_THEME] = isDynamicTheme
            }
        }
}