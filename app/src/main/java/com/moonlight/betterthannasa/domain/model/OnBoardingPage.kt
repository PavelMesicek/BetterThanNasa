package com.moonlight.betterthannasa.domain.model

import androidx.annotation.DrawableRes
import com.moonlight.betterthannasa.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object  First : OnBoardingPage(
        image = R.drawable.nato,
        title = "Greetings!",
        description = "Did you know that this app is better than Nato's own?"
    )

    object  Second : OnBoardingPage(
        image = R.drawable.shock,
        title = "Find them all!",
        description = "In the sheet you can see all meteorites sorted since 2011!"
    )

    object  Third : OnBoardingPage(
        image = R.drawable.map,
        title = "Near me!",
        description = "On the map you can see the nearest Meteorites around you! That's cool, right?"
    )
}
