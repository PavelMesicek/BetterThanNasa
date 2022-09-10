package com.moonlight.betterthannasa.di

import com.moonlight.betterthannasa.data.location.LocationTrackerImpl
import com.moonlight.betterthannasa.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    abstract fun bindLocationTracker(locationTrackerImpl: LocationTrackerImpl): LocationTracker

}