package com.moonlight.betterthannasa.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val Meteorites: List<Meteorite> = emptyList()
)