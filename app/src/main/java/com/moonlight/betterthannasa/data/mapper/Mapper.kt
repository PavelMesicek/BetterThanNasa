package com.moonlight.betterthannasa.data.mapper

import com.moonlight.betterthannasa.data.local.MeteoriteEntity
import com.moonlight.betterthannasa.data.remote.dto.MeteoriteDto
import com.moonlight.betterthannasa.domain.model.Meteorite
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun MeteoriteEntity.toMeteorite(): Meteorite {
    val roundedLatitude = String.format("%.3f", latitude)
    val roundedLongitude = String.format("%.3f", longitude)
    val roundedMass = String.format("%.3f", mass)
    return Meteorite(
        id = id,
        mass = roundedMass.toDouble(),
        name = name,
        recclass = recclass,
        year = year,
        longitude = roundedLongitude.toDouble(),
        latitude = roundedLatitude.toDouble()
    )
}

fun MeteoriteDto.toMeteoriteEntity(): MeteoriteEntity {
    val pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val localDateTime = LocalDate.parse(year, formatter)
    return MeteoriteEntity(
        latitude =  reclat.toDouble(),
        longitude = reclong.toDouble(),
        id = id,
        mass = mass.toDouble(),
        name = name,
        recclass = recclass,
        year = localDateTime.toString()
    )
}