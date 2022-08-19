package com.example.jetreddit.routing

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class MyProfileScreenType(){
    object Posts:MyProfileScreenType()
    object About:MyProfileScreenType()
}

object MyProfileRouter {
    var currentScreen:MutableState<MyProfileScreenType> = mutableStateOf(MyProfileScreenType.Posts)
    fun navigateTo(destination:MyProfileScreenType){
        currentScreen.value = destination
    }
}