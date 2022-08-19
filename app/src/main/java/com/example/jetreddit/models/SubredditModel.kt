package com.example.jetreddit.models

import androidx.annotation.StringRes
import com.example.jetreddit.R

data class SubredditModel(
    @StringRes val nameStringRes: Int,
    @StringRes val membersStringRes:Int,
    @StringRes val descriptionStringRes: Int,
){
    companion object{
        val DEFALUT_SUBREDDIT = SubredditModel(R.string.android,R.string.members_400k,R.string.welcome_to_android)
    }
}
