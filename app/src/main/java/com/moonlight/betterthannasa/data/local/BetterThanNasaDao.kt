package com.moonlight.betterthannasa.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BetterThanNasaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeteorites(meteorites: List<MeteoriteEntity>)

    @Query("DELETE FROM meteorite_table")
    suspend fun clearMeteorites()

    @Query("SELECT * FROM meteorite_table")
    suspend fun displayAllMeteorites(): List<MeteoriteEntity>
}