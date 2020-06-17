package com.velaphi.tabby.data.api

import com.velaphi.tabby.data.model.Response
import retrofit2.http.GET

interface ApiService {
    @GET("images/get?format=xml&results_per_page=100&size=medium&type=jpg")
    suspend fun getImages(): Response
}