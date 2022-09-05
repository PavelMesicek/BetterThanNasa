package com.moonlight.betterthannasa.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moonlight.betterthannasa.util.Constants.METEORITE_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = METEORITE_DATABASE_TABLE)
data class Meteorite(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val fall: String,
    @Embedded
    val geolocation: Geolocation,
    val mass: String,
    val name: String,
    val nametype: String,
    val recclass: String,
    val year: String
)