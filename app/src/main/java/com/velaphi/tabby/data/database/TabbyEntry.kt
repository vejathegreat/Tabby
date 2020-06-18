package com.velaphi.tabby.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "images",
    indices = [Index(value = ["id"], unique = true)]
)

@Parcelize
data class TabbyEntry constructor(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val url: String
): Parcelable