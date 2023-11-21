package com.example.film2gethersimple.data.local

import com.example.film2gethersimple.R
import com.example.film2gethersimple.data.Film
import com.example.film2gethersimple.data.Genres

object LocalFilmsDataProvider {
    val allFilms = listOf<Film>(
        Film(
            name = R.string.the_shawshank_redemption,
            image = R.drawable.shawshank_image2,
            description = R.string.the_shawshank_redemption_description,
            genres = setOf(Genres.Drama),
            iMDbRate = 9.2,
        ),
        Film(
            name = R.string.the_godfather,
            image = R.drawable.godfather_image,
            description = (R.string.the_godfather_description),
            genres = setOf(Genres.Drama),
            iMDbRate = 9.2,
        ),
        Film(
            name = R.string.the_dark_knight,
            image = R.drawable.the_dark_knight_image,
            description = (R.string.the_dark_knight_description),
            genres = setOf(Genres.Drama, Genres.Crime),
            iMDbRate = 9.0,
        ),
        Film(
            name = (R.string.the_godfather_part_ii),
            image = R.drawable.godfather_2_image,
            description = (R.string.the_godfather_part_ii_description),
            genres = setOf(Genres.Drama, Genres.Crime, Genres.Action),
            iMDbRate = 9.0,
        ),
        Film(
            name = (R.string._12_angry_men),
            image = R.drawable._12_angry_man_image,
            description = (R.string._12_angry_men_description),
            genres = setOf(Genres.Drama, Genres.Crime),
            iMDbRate = 8.9,
        ),
        Film(
            name = (R.string.schindler_s_list),
            image = R.drawable.shindler_list_image,
            description = (R.string.schindler_s_list_description),
            genres = setOf(Genres.Drama, Genres.History, Genres.Biography),
            iMDbRate = 8.9,
        ),
        Film(
            name = (R.string.the_lord_of_the_rings_the_return_of_the_king),
            image = R.drawable.the_lord_of_rings_2_image,
            description = (R.string.gandalf_and_aragorn_lead_the_world_of_men_against_sauron_s_army_to_draw_his_gaze_from_frodo_and_sam_as_they_approach_mount_doom_with_the_one_ring),
            genres = setOf(Genres.Drama, Genres.Action, Genres.Adventure),
            iMDbRate = 8.9,
        ),
        Film(
            name = (R.string.pulp_fiction),
            image = R.drawable.pulp_fiction_image,
            description = (R.string.the_lives_of_two_mob_hitmen_a_boxer_a_gangster_and_his_wife_and_a_pair_of_diner_bandits_intertwine_in_four_tales_of_violence_and_redemption),
            genres = setOf(Genres.Horror, Genres.Comedy, Genres.Action),
            iMDbRate = 8.9,
        ),
        Film(
            name = (R.string.the_lord_of_the_rings_the_fellowship_of_the_ring),
            image = R.drawable.the_lord_of_rings_1_image,
            description = (R.string.a_meek_hobbit_from_the_shire_and_eight_companions_set_out_on_a_journey_to_destroy_the_powerful_one_ring_and_save_middle_earth_from_the_dark_lord_sauron),
            genres = setOf(Genres.Action, Genres.Adventure),
            iMDbRate = 8.8,
        ),
        Film(
            name = (R.string.the_good_the_bad_and_the_ugly),
            image = R.drawable.the_good_bad_ugly_poster,
            description = (R.string.a_bounty_hunting_scam_joins_two_men_in_an_uneasy_alliance_against_a_third_in_a_race_to_find_a_fortune_in_gold_buried_in_a_remote_cemetery),
            genres = setOf(
                Genres.Action,
                Genres.Comedy,
                Genres.Adventure,
                Genres.Horror,
                Genres.Biography,
                Genres.Crime
            ),
            iMDbRate = 8.8,
        ),
    )

    val defaultFilm = Film(
        genres = emptySet(), iMDbRate = 0.0
    )
}