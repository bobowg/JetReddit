package com.example.jetreddit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetreddit.data.database.model.PostDbModel

@Dao
interface PostDao {
    @Query("SELECT * FROM PostDbModel")
    fun getAllPosts():List<PostDbModel>

    @Query("SELECT * FROM postdbmodel WHERE username=:username")
    fun getAllOwnedPosts(username:String):List<PostDbModel>

    @Query("SELECT DISTINCT subreddit FROM POSTDBMODEL")
    fun getAllSubreddits():List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(postDbModel: PostDbModel)

    @Query("DELETE FROM POSTDBMODEL")
    fun deleteAll()

    @Insert
    fun insertAll(vararg postDbModel: PostDbModel)

}