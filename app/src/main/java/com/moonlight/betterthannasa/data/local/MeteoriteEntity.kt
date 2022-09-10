package com.moonlight.betterthannasa.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moonlight.betterthannasa.util.Constants

@Entity(tableName = Constants.METEORITE_DATABASE_TABLE)
data class MeteoriteEntity(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val mass: Double,
    val name: String,
    val recclass: String,
    val year: String,
)