package com.velaphi.tabby.data.api

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


object RetrofitBuilder {
    private const val BASE_URL = "https://api.thecatapi.com/api/"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(SimpleXmlConverterFactory.create())
//            .addConverterFactory(TikXmlConverterFactory.create(tikxml))
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}