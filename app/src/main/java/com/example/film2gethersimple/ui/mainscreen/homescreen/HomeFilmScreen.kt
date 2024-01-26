package com.example.film2gethersimple.ui.mainscreen.homescreen

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
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.local.LocalAccountDataProvider
import com.example.film2gethersimple.ui.models.Film


//Home screen have column with all films
@Composable
fun HomeFilmScreen(
    uiState: HomeUiState,
    onHomeScreenCardClick: (Film) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    HomeScreenColumn(
        onHomeScreenCardClick = onHomeScreenCardClick,
        allFilms = (uiState as HomeUiState.Success).response,
        contentPadding = contentPadding,
    )
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
                    filmName = it.name,
                    iMDbRate = it.publisherDate,
                    image = it.imageLink,
                    onCardClick = {
                        onHomeScreenCardClick(it)
                    }
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
    filmName: String,
    image: String,
    iMDbRate: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
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
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = filmName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.film_card_image_size))
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.medium)))
            )
            Column(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.large))) {
                Text(
                    text = filmName,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
//                        Icons.Outlined.Star,
                        Icons.Outlined.DateRange,
                        contentDescription = Icons.Outlined.DateRange.name
                    )
                    Text(
                        text = iMDbRate,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(
                            top = dimensionResource(id = R.dimen.small),
                            start = dimensionResource(id = R.dimen.small)
                        )
                    )
                }
            }
        }
    }
}


@Preview(widthDp = 1000)
@Composable
fun HomeFilmScreenPreview() {
    val film = Film(
        name = "the Shawshank",
        imageLink = "http://books.google.com/books/content?id=iTsfAQAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
        description = "Film descr",
        categories = listOf(
            "Drama", "Adventure", "Action", "Biography", "Adventure"
        ),
        publisherDate = "1982",
        linkToGoogleBooks = "https://books.google.ru/books/about/Power_Play.html?hl=&id=je2dDAAAQBAJ&redir_esc=y"
    )
    val response = listOf(film, film, film)
    val homeUiState = HomeUiState.Success(
        response = response,
        account = LocalAccountDataProvider.account,
        currentSelectedItem = response[0],
        topAppBarTitle = "Films"
    )

    HomeFilmScreen(
        uiState = homeUiState,
        onHomeScreenCardClick = {},
    )
}


@Preview(showBackground = true)
@Composable
fun FilmCardPreview() {
    val film = Film(
        name = "the Shawshank",
        imageLink = "http://books.google.com/books/content?id=iTsfAQAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
        description = "Film descr",
        categories = listOf(
            "Drama", "Adventure", "Action", "Biography", "Adventure"
        ),
        publisherDate = "1982",
        linkToGoogleBooks = "https://books.google.ru/books/about/Power_Play.html?hl=&id=je2dDAAAQBAJ&redir_esc=y"
    )
    FilmCard(filmName = film.name,
        iMDbRate = film.publisherDate,
        image = film.imageLink,
        onCardClick = {})
}

