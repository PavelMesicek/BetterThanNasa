package com.moonlight.betterthannasa.domain.model

data class Meteorite(
    val latitude: Double,
    val longitude: Double,
    val id: String,
    val mass: Double,
    val name: String,
    val recclass: String,
    val year: String
)