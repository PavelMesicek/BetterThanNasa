package com.moonlight.betterthannasa.data.remote

import com.moonlight.betterthannasa.data.remote.dto.MeteoriteDto
import com.moonlight.betterthannasa.util.Constants.X_APP_TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface BetterThanNasaApi {

    @Headers("X-App-Token: $X_APP_TOKEN")
    @GET("/resource/gh4g-9sfh.json?\$where=year>='2011-01-01T00:00:00.000'")
    suspend fun fetchMeteorites(): Response<List<MeteoriteDto>>

}