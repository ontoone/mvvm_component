package com.mansoul.mvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mansoul.mvvm.data.entity.Android
import com.mansoul.mvvm.data.entity.GankImage


/**
 * @author Mansoul
 * @create 2019/3/21 20:41
 * @des
 */
@Database(entities = arrayOf(GankImage::class, Android::class), version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao

    abstract fun androidDao(): AndroidDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                return Room
                    .databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "my_database"
                    )
//                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
        }

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL(
                    "CREATE TABLE android_table (_id TEXT, createdAt TEXT, 'desc' TEXT, images TEXT, publishedAt TEXT,source TEXT,type TEXT,url TEXT,who TEXT, PRIMARY KEY(_id))"
                )
            }
        }
    }
}