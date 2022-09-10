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
        description = "There are more than 45,000 recorded meteorites and you can find them all here!"
    )

    object  Third : OnBoardingPage(
        image = R.drawable.map,
        title = "Near me!",
        description = "Don't forget to look at the map to see where the meteorite was closest to you."
    )
}
