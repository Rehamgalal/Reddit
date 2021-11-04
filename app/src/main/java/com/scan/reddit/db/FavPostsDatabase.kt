package com.scan.reddit.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.scan.reddit.other.Constants.DATABASE_NAME
import com.scan.reddit.other.Constants.DATABASE_VERSION

@Database(entities = [PostEntity::class], version = DATABASE_VERSION, exportSchema = false)
@TypeConverters(PostTypeConverter::class)
abstract class FavPostsDatabase : RoomDatabase() {

    abstract fun articlesDao(): PostDao

    companion object {
        fun buildDatabase(context: Context): FavPostsDatabase {
            return Room.databaseBuilder(context, FavPostsDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}