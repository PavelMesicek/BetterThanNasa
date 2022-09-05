package com.moonlight.betterthannasa.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moonlight.betterthannasa.domain.model.Meteorite

@Database(
    entities = [Meteorite::class],
    version = 1
)
abstract class BetterThanNasaDatabase : RoomDatabase() {

    abstract fun dao(): BetterThanNasaDao
}