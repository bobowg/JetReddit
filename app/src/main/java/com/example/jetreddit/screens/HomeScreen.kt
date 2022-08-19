package com.example.jetreddit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetreddit.components.ImagePost
import com.example.jetreddit.components.JoinedToast
import com.example.jetreddit.components.TextPost
import com.example.jetreddit.domain.model.PostModel
import com.example.jetreddit.domain.model.PostType
import com.example.jetreddit.viewmodel.MainViewModel
import java.util.*
import kotlin.concurrent.schedule

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val posts: List<PostModel> by viewModel.allPosts.observeAsState(listOf())
    var isToastVisible by remember { mutableStateOf(false) }
    val onJoinClickAction: (Boolean) -> Unit = { jioned ->
        isToastVisible = jioned
        if (isToastVisible){
            Timer().schedule(3000){
                isToastVisible = false
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.background(color = MaterialTheme.colors.secondary)) {
            items(posts) {
                if (it.type == PostType.TEXT) {
                    TextPost(it, onJoinButtonClick = onJoinClickAction)
                } else {
                    ImagePost(it, onJoinButtonClick = onJoinClickAction)
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            JoinedToast(visible = isToastVisible)
        }
    }
}
