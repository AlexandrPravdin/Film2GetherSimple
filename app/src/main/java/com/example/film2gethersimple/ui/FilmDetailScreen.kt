package com.example.film2gethersimple.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.Film
import com.example.film2gethersimple.data.Genres


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
    //Information about film
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        //Row with name and rating of film
        NameAndRateRow(
            filmName = film.name,
            filmRate = film.iMDbRate,
            modifier = Modifier.fillMaxWidth()
        )
        //Film description text
        Text(
            text = stringResource(id = film.description),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

//      LazyHorizontalStaggeredGrid with film genres
        LazyVerticalStaggeredGrid(modifier = Modifier.fillMaxWidth(1f),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            //columns = StaggeredGridCells.Fixed(3),
            columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
            content = {
                items(film.genres.toList()) { genre ->
                    OutlinedButton(
                        onClick = {},
                    ) {
                        Text(
                            text = genre.name, modifier = Modifier.wrapContentWidth()
                        )
                    }
                }
            })
        Image(
            painter = painterResource(id = film.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(16.dp))/*                .blur(
                                radiusX = 20.dp,
                                radiusY = 20.dp,
                                edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                            )
                            .padding(16.dp),*//*colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.primaryContainer,
                blendMode = BlendMode.Darken
            ),*/
        )
    }
}

@Composable
fun NameAndRateRow(
    modifier: Modifier = Modifier,
    filmName: Int,
    filmRate: Double
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = filmName),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(Icons.Outlined.Star, contentDescription = "Star")
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = filmRate.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = "iMDb",
                )
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun NameAndRateRowPreview() {
    val film = Film(
        name = R.string.the_shawshank_redemption,
        image = R.drawable.shawshank_image,
        description = R.string.the_shawshank_redemption_description,
        genres = setOf(Genres.Drama),
        iMDbRate = 9.2,
    )
    NameAndRateRow(filmName = film.name, filmRate = film.iMDbRate)
}


@Preview(showBackground = true, widthDp = 450)
@Composable
fun FilmDetailPreview() {
    val film = Film(
        name = R.string.the_shawshank_redemption,
        image = R.drawable.shawshank_image,
        description = R.string.the_shawshank_redemption_description,
        genres = setOf(
            Genres.Drama,
            Genres.Adventure,
            Genres.Action,
            Genres.Biography,
            Genres.Adventure
        ),
        iMDbRate = 9.2,
    )
    DetailScreen(
        film = film,
        onBackPressed = {},
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    )
}
