package com.moonlight.betterthannasa.domain.repository

import com.moonlight.betterthannasa.domain.model.Meteorite
import com.moonlight.betterthannasa.util.Resource
import kotlinx.coroutines.flow.Flow

interface BetterThanNasaRepository {

    suspend fun getMeteorites(
        fetchFromRemote: Boolean,
    ): Flow<Resource<List<Meteorite>>>

    suspend fun saveOnBoardingState(completed: Boolean)

    fun readOnBoardingState(): Flow<Boolean>

}