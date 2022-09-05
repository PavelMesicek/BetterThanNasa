package com.moonlight.betterthannasa.data.remote

import com.moonlight.betterthannasa.domain.model.ApiResponse
import com.moonlight.betterthannasa.util.Constants.X_APP_TOKEN
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers

interface BetterThanNasaApi {

    @Headers("X-App-Token: $X_APP_TOKEN")
    @GET("/resource/gh4g-9sfh")
    suspend fun fetchMeteorites(): ApiResponse

}