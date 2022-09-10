package com.moonlight.betterthannasa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MeteoriteEntity::class],
    version = 3
)
abstract class BetterThanNasaDatabase : RoomDatabase() {

    abstract fun dao(): BetterThanNasaDao
}