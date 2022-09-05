package com.moonlight.betterthannasa.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moonlight.betterthannasa.domain.model.Meteorite
import kotlinx.coroutines.flow.Flow

@Dao
interface BetterThanNasaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeteorites(meteorites: List<Meteorite>)

    @Query("DELETE FROM meteorite_table")
    suspend fun clearMeteorites()

    @Query("SELECT * FROM meteorite_table ORDER BY ABS(latitude - :latitude) + ABS(longitude - :longitude) ASC")
    suspend fun findByDistance(latitude: Double, longitude: Double): List<Meteorite>

}