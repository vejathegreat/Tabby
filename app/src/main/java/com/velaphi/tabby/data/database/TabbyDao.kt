package com.velaphi.tabby.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TabbyDao {
    @Query("SELECT * FROM images")
    fun retrieveListOfAllImages(): LiveData<List<TabbyEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTabbyEntries(tabbyEntries: List<TabbyEntry>)
}