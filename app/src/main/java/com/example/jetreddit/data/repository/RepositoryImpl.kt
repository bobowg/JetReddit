package com.example.jetreddit.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetreddit.data.database.dao.PostDao
import com.example.jetreddit.data.database.dbmapper.DbMapper
import com.example.jetreddit.data.database.model.PostDbModel
import com.example.jetreddit.domain.model.PostModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val postDao: PostDao,
    private val mapper: DbMapper
):Repository {

    private var searchedText = ""
    private val allPostsLiveData: MutableLiveData<List<PostModel>> by lazy {
        MutableLiveData<List<PostModel>>()
    }
    private val ownerPostsLiveData: MutableLiveData<List<PostModel>> by lazy {
        MutableLiveData<List<PostModel>>()
    }

    init {
        initDatabase(this::updatePostLiveData)
    }

    private fun getAllPostsFromDatabase(): List<PostModel> =
        postDao.getAllPosts().map(mapper::mapPost)


    private fun getAllOwnedPostsFromDatabase(): List<PostModel> =
        postDao.getAllOwnedPosts("bobowg.pythonanywhere.com").map(mapper::mapPost)

    private fun updatePostLiveData() {
        allPostsLiveData.postValue(getAllPostsFromDatabase())
        ownerPostsLiveData.postValue(getAllOwnedPostsFromDatabase())
    }


    private fun initDatabase(postInitAction: () -> Unit) {
        GlobalScope.launch {
            val posts = PostDbModel.DEFAULT_POSTS.toTypedArray()
            val dpPosts = postDao.getAllPosts()
            if (dpPosts.isNullOrEmpty()) {
                postDao.insertAll(*posts)
            }
            postInitAction.invoke()
        }
    }

    override fun getAllPosts(): LiveData<List<PostModel>> {
        return allPostsLiveData
    }

    override fun getAllOwnedPosts(): LiveData<List<PostModel>> {
        return ownerPostsLiveData
    }

    override fun getAllSubreddits(searchedText: String): List<String> {
        this.searchedText = searchedText
        if (searchedText.isNotEmpty()){
            return postDao.getAllSubreddits().filter { it.contains(searchedText) }
        }
        return postDao.getAllSubreddits()
    }

    override fun insert(post: PostModel) {
        postDao.insert(mapper.mapDbPost(post))
        updatePostLiveData()
    }

    override fun deleteAll() {
       postDao.deleteAll()
        updatePostLiveData()
    }
}