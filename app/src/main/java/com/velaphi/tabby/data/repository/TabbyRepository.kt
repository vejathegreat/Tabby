package com.velaphi.tabby.data.repository

import androidx.lifecycle.LiveData
import com.velaphi.tabby.data.database.TabbyEntry

interface TabbyRepository {
    fun loadFromApi()
    fun loadFromDB(): LiveData<List<TabbyEntry>>
}