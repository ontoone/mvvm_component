package com.mansoul.mvvm.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mansoul.mvvm.data.entity.GankImage

/**
 * @author Mansoul
 * @create 2019/3/21 19:18
 * @des
 */
@Dao
interface ImageDao {

    @Query("SELECT * from image_table ORDER BY _id ASC")
//    fun getAllImage(): LiveData<List<GankImage>>
    fun getAllImage(): List<GankImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gift: GankImage)

    @Query("DELETE FROM image_table")
    fun deleteAll()
}