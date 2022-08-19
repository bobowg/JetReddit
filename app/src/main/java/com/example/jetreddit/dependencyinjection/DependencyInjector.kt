package com.example.jetreddit.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.jetreddit.data.AppDatabase
import com.example.jetreddit.data.database.dbmapper.DbMapper
import com.example.jetreddit.data.database.dbmapper.DbMapperImpl
import com.example.jetreddit.data.repository.Repository
import com.example.jetreddit.data.repository.RepositoryImpl

class DependencyInjector(application:Context) {
    val repository:Repository by lazy { provideRepostory(database) }

    private val database:AppDatabase by lazy { provideDatabase(application) }
    private val dbMapper:DbMapper = DbMapperImpl()
    private fun provideDatabase(application: Context):AppDatabase  =
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()

    private fun provideRepostory(database: AppDatabase):Repository{
        val postDao = database.postDao()
        return RepositoryImpl(postDao,dbMapper)
    }
}