package com.example.film2gethersimple.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.film2gethersimple.R
import com.example.film2gethersimple.ui.models.Film
import com.example.film2gethersimple.ui.utils.ContentType


//Home screen have column with all films
@Composable
fun HomeFilmScreen(
    uiState: FilmUiState,
    onHomeScreenCardClick: (Film) -> Unit,
    onDetailsBackScreenPressed: () -> Unit,
    contentType: ContentType,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    if (contentType == ContentType.ListAndDetails) {
        FilmListAndDetailScreen(
            allFilms = uiState.allFilms,
            currentFilm = uiState.currentSelectedFilm,
            onHomeScreenCardClick = onHomeScreenCardClick
        )
    }
    else {
        //Moving to detail screen
        if (uiState.isShowingHomePage) {
            HomeScreenColumn(
                allFilms = uiState.allFilms,
                onHomeScreenCardClick = onHomeScreenCardClick,
                contentPadding = contentPadding
            )
        } else {
            DetailScreen(
                film = uiState.currentSelectedFilm,
                onBackPressed = onDetailsBackScreenPressed
            )
        }
    }

}


//List of films with [filmCard]
@Composable
fun HomeScreenColumn(
    onHomeScreenCardClick: (Film) -> Unit,
    allFilms: List<Film>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        content = {
            items(allFilms) {
                FilmCard(
                    name = it.name,
                    iMDbRate = it.iMDbRate,
                    image = it.image,
                    onCardClick = { onHomeScreenCardClick(it) }
                )
            }
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.large),
            contentPadding.calculateTopPadding()
        ), //contentPadding

        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large)),
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    )
}


//Card that contain one film information
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmCard(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    @DrawableRes image: Int,
    iMDbRate: Double,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium)),
        onClick = onCardClick,
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.small)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.medium)))
        {
            Image(
                painter = painterResource(id = image),
                contentDescription = stringResource(id = name),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.film_card_image_size))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.medium)))
            )
            Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.large))) {
                Text(
                    text = stringResource(id = name),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = stringResource(id = R.string.star)
                    )
                    Text(
                        text = iMDbRate.toString(),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.small),
                            start = dimensionResource(id = R.dimen.small))
                    )
                }
            }

        }
    }
}


@Preview(widthDp = 1000)
@Composable
fun HomeFilmScreenPreview() {
    val viewModel: FilmViewModel = viewModel()
    val homeUiState = viewModel.uiState.collectAsState().value
    HomeFilmScreen(uiState = homeUiState,
        onDetailsBackScreenPressed = {},
        onHomeScreenCardClick = {},
        contentType = ContentType.ListAndDetails)
}


@Preview(showBackground = true)
@Composable
fun FilmCardPreview() {
    val viewModel: FilmViewModel = viewModel()
    val homeUiState = viewModel.uiState.collectAsState().value
    val film = homeUiState.allFilms[0]
    FilmCard(name = film.name,
        iMDbRate = film.iMDbRate,
        image = film.image,
        onCardClick = {})
}
