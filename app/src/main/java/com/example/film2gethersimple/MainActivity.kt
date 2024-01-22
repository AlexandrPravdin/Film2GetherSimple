package com.example.film2gethersimple

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.example.film2gethersimple.ui.FilmApp
import com.example.film2gethersimple.ui.theme.Film2GetherSimpleTheme

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Film2GetherSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    FilmApp(windowSize.widthSizeClass)
                }
            }
        }
    }
}


/*
@Preview(showBackground = true, widthDp = 400)
@Composable
fun GreetingPreviewCompact() {
    Film2GetherSimpleTheme {
        FilmApp(windowSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(showBackground = true, widthDp = 800)
@Composable
fun GreetingPreviewMedium() {
    Film2GetherSimpleTheme {
        FilmApp(windowSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(showBackground = true, widthDp = 1200)
@Composable
fun GreetingPreviewExpanded() {
    Film2GetherSimpleTheme {
        FilmApp(windowSize = WindowWidthSizeClass.Expanded)
    }
}
*/
