package com.velaphi.tabby.data.repository

import com.velaphi.tabby.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
//TODO check room
    suspend fun getImages() = apiHelper.getImages()
}