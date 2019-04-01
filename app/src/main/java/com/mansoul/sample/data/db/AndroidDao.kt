package com.mansoul.sample.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mansoul.sample.data.entity.Android

/**
 * @author Mansoul
 * @create 2019/3/27 15:30
 * @des
 */
@Dao
interface AndroidDao {

    @Query("SELECT * from android_table")
    fun getAll(): List<Android>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(android: Android)

    @Query("DELETE FROM android_table")
    fun deleteAll()
}