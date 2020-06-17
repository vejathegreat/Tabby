package com.velaphi.tabby.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getImages() = apiService.getImages()
}