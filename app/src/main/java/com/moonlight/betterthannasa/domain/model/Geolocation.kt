package com.moonlight.betterthannasa.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Geolocation(
    val latitude: String,
    val longitude: String
)