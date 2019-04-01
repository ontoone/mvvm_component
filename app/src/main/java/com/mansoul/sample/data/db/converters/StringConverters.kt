package com.mansoul.sample.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author Mansoul
 * @create 2019/3/27 16:15
 * @des
 */
class StringConverters {
    @TypeConverter
    fun stringToObject(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}