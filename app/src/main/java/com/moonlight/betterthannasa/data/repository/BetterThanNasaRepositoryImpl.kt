package com.moonlight.betterthannasa.data.repository

import com.moonlight.betterthannasa.data.local.BetterThanNasaDatabase
import com.moonlight.betterthannasa.data.remote.BetterThanNasaApi
import com.moonlight.betterthannasa.domain.model.Meteorite
import com.moonlight.betterthannasa.domain.repository.BetterThanNasaRepository
import com.moonlight.betterthannasa.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BetterThanNasaRepositoryImpl @Inject constructor(
    val api: BetterThanNasaApi,
    val db: BetterThanNasaDatabase
) : BetterThanNasaRepository {

    private val dao = db.dao()

    override suspend fun getMeteorites(
        fetchFromRemote: Boolean,
        latitude: Double,
        longitude: Double
    ): Flow<Resource<List<Meteorite>>> {
        return flow {
            emit(Resource.Loading(true))
            val meteoriteNearMe = dao.findByDistance(
                latitude,
                longitude
            )
            emit(
                Resource.Success(
                    data = meteoriteNearMe
                )
            )

            val loadFromCache = meteoriteNearMe.isNotEmpty() && !fetchFromRemote
            if (loadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val getMeteoriteFromRemote = try {
                val response = api.fetchMeteorites()
                response.Meteorites
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not load data"))
                null
            }

            getMeteoriteFromRemote?.let { meteorite ->
                dao.clearMeteorites()
                dao.insertMeteorites(meteorite)
                emit(
                    Resource.Success(
                        data = dao.findByDistance(
                            latitude,
                            longitude
                        )
                    )
                )
                emit(Resource.Loading(false))
            }
        }
    }
}