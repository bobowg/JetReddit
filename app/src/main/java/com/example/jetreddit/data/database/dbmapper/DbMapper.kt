package com.example.jetreddit.data.database.dbmapper

import com.example.jetreddit.data.database.model.PostDbModel
import com.example.jetreddit.domain.model.PostModel

interface DbMapper {
    fun mapPost(dbPostDbModel: PostDbModel):PostModel
    fun mapDbPost(postModel: PostModel):PostDbModel
}