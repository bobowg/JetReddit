package com.example.jetreddit.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetreddit.data.database.dao.PostDao
import com.example.jetreddit.data.database.model.PostDbModel

@Database(entities = [PostDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object{
        const val DATABASE_NAME = "jet-reddit-database"
    }
    abstract fun postDao():PostDao
}