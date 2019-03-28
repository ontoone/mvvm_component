package com.mansoul.mvvm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mansoul.mvvm.data.db.converters.StringConverters

@Entity(tableName = "android_table")
@TypeConverters(StringConverters::class)
data class Android(
    @PrimaryKey
    val _id: String,
    val createdAt: String,
    val desc: String,
    var images: List<String> = listOf(),
    val publishedAt: String,
    val source: String,
    val type: String,
    val url: String,
    val used: Boolean,
    val who: String
)