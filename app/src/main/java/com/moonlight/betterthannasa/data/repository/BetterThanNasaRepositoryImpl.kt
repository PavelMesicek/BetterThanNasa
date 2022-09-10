package com.moonlight.betterthannasa.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.moonlight.betterthannasa.data.local.BetterThanNasaDatabase
import com.moonlight.betterthannasa.data.mapper.toMeteorite
import com.moonlight.betterthannasa.data.mapper.toMeteoriteEntity
import com.moonlight.betterthannasa.data.remote.BetterThanNasaApi
import com.moonlight.betterthannasa.domain.model.Meteorite
import com.moonlight.betterthannasa.domain.repository.BetterThanNasaRepository
import com.moonlight.betterthannasa.util.Constants
import com.moonlight.betterthannasa.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES_NAME)

class BetterThanNasaRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val api: BetterThanNasaApi,
    private val db: BetterThanNasaDatabase
) : BetterThanNasaRepository {

    private val dao = db.dao()

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = Constants.PREFERENCES_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState =
                    preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

    override suspend fun getMeteorites(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Meteorite>>> {

        return flow {
            val meteoriteNearMe = dao.displayAllMeteorites()
            emit(
                Resource.Success(
                    data = meteoriteNearMe.map { it.toMeteorite() }
                )
            )

            val loadFromCache = meteoriteNearMe.isNotEmpty() && !fetchFromRemote
            if (loadFromCache) {
                return@flow
            }
            val getMeteoriteFromRemote = try {
                val response = api.fetchMeteorites()
                if (response.isSuccessful) {
                    response.body()
                } else null

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
                dao.insertMeteorites(meteorite.map { it.toMeteoriteEntity() })
                emit(
                    Resource.Success(
                        data = dao.displayAllMeteorites()
                            .map { it.toMeteorite() }
                    )
                )
            }
        }
    }
}