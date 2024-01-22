package com.example.film2gethersimple.ui.mainscreen.detailscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.film2gethersimple.R
import com.example.film2gethersimple.ui.models.Film


//DetailsScreen where user see film information
@Composable
fun DetailScreen(
    film: Film,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler {
        onBackPressed()
    }
    LazyColumn(
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.large),
                end = dimensionResource(id = R.dimen.large),
                bottom = dimensionResource(id = R.dimen.large)
            )
            .background(MaterialTheme.colorScheme.background)
    ) {
        item {
            //Row with name and rating of film
            NameAndRateRow(
                filmName = film.name,
                filmRate = film.publisherDate,
                modifier = Modifier.fillMaxWidth()
            )
            //Film description text
            FilmDescription(film.description)
            GenresGrid(film.categories)
            FilmPoster(film = film)
        }
    }
}

@Composable
fun NameAndRateRow(
    filmName: String,
    filmRate: String,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(bottom = dimensionResource(id = R.dimen.large))
            .fillMaxWidth()
    ) {
        Text(
            text = filmName,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.medium))
        ) {
            Icon(Icons.Outlined.DateRange, contentDescription = Icons.Outlined.DateRange.name)
        }
        Text(
            text = filmRate,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun FilmDescription(
    filmDescription: String?,
    modifier: Modifier = Modifier
) {
    if (filmDescription != null) {
        Text(
            text = filmDescription,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.large)),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun GenresGrid(
    filmGenres: List<String>, modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier
            .fillMaxWidth(1f)
            .sizeIn(maxHeight = dimensionResource(id = R.dimen.genre_item_min_size)), //Maybe right
        verticalItemSpacing = dimensionResource(id = R.dimen.small),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.large)),
        columns = StaggeredGridCells.Adaptive(
            minSize = dimensionResource(id = R.dimen.min_genre_grid_size)
        ),
        content = {
            items(filmGenres) { genre ->
                GenreChip(genre = genre)
            }
        })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenreChip(
    genre: String,
    modifier: Modifier = Modifier,
) {
    Chip(
        modifier = modifier,
        onClick = {},
        content = {
            Text(
                text = genre, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            )
        },
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium)),
        colors = ChipDefaults.chipColors(
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    )
}

@Composable
fun FilmPoster(
    film: Film, modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(film.imageLink)
            .crossfade(true)
            .build(),
        contentDescription = film.name,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.large))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.large)))

    )
}

@Preview(showBackground = true)
@Composable
fun NameAndRateRowPreview() {
    val film = Film(
        name = "the Shawshank",
        imageLink = "http://books.google.com/books/content?id=iTsfAQAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
        description = "Film descr",
        categories = listOf(
            "Drama", "Adventure", "Action", "Biography", "Adventure"
        ),
        publisherDate = "1982",
    )
    NameAndRateRow(filmName = film.name, filmRate = "1981")
}


@Preview(showBackground = true, widthDp = 450)
@Composable
fun FilmDetailPreview() {
    val film = Film(
        name = "the Shawshank",
        imageLink = "http://books.google.com/books/content?id=iTsfAQAAMAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
        description = "Film description",
        categories = listOf(
            "Drama", "Adventure", "Action", "Biography", "Adventure"
        ),
        publisherDate = "1982",
    )
    DetailScreen(
        film = film,
        onBackPressed = {},
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    )
}

