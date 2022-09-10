package com.moonlight.betterthannasa.di

import com.moonlight.betterthannasa.data.repository.BetterThanNasaRepositoryImpl
import com.moonlight.betterthannasa.domain.repository.BetterThanNasaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindBetterThanNasaRepository(
        betterThanNasaRepositoryImpl: BetterThanNasaRepositoryImpl
    ): BetterThanNasaRepository

}