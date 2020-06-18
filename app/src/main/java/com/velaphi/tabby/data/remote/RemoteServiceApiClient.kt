package com.velaphi.tabby.data.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteServiceApiClient {
    private const val BASE_URL = "https://api.thecatapi.com/api/"
    var apiservice: ApiService? = null

    @JvmStatic
    val instance: ApiService?
        get() {
            val retrofit: Retrofit
            if (apiservice == null) {
                val gson = GsonBuilder().create()
                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build()
                apiservice = retrofit.create(ApiService::class.java)
            }
            return apiservice
        }
}