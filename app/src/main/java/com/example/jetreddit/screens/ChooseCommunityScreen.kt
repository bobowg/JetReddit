package com.example.jetreddit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetreddit.R
import com.example.jetreddit.routing.BackButtonAction
import com.example.jetreddit.routing.JetRedditRouter
import com.example.jetreddit.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

private const val SEARCH_DELAY_MILLS = 300L
private val defaultCommunties = listOf("raywenderlich", "androiddev", "puppies", "bobowg")

@Composable
fun ChooseCommunityScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val communties: List<String> by viewModel.subreddits.observeAsState(emptyList())
    var searchedText by remember { mutableStateOf("") }
    var currentJob by remember { mutableStateOf<Job?>(null) }
    val activeColor = MaterialTheme.colors.onSurface

    LaunchedEffect(key1 = Unit) {
        viewModel.searchCommunities(searchedText)
    }

    Column {
        ChooseCommunityTopBar()
        TextField(
            value = searchedText,
            onValueChange = {
                searchedText = it
                currentJob?.cancel()
                currentJob = scope.async {
                    delay(SEARCH_DELAY_MILLS)
                    viewModel.searchCommunities(searchedText)
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search)
                )
            },
            label = { Text(text = stringResource(id = R.string.search)) },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = activeColor,
                focusedLabelColor = activeColor,
                cursorColor = activeColor,
                backgroundColor = MaterialTheme.colors.surface
            )
        )
        SeachedCommunties(communties = communties, viewModel = viewModel, modifier = modifier)
    }
    BackButtonAction{
        JetRedditRouter.goBack()
    }
}

@Composable
fun ChooseCommunityTopBar(modifier: Modifier = Modifier) {
    val colors = MaterialTheme.colors
    TopAppBar(
        title = {
            Text(
                fontSize = 16.sp,
                text = stringResource(id = R.string.choose_community),
                color = colors.primaryVariant
            )
        },
        navigationIcon = {
            IconButton(onClick = { JetRedditRouter.goBack() }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    tint = colors.primaryVariant
                )
            }
        },
        backgroundColor = colors.primary,
        elevation = 0.dp,
        modifier = modifier
            .height(48.dp)
            .background(Color.Blue)
    )
}

@Preview
@Composable
fun ChooseCommunityTopBarPreview() {
    ChooseCommunityTopBar()
}

@Composable
fun SeachedCommunties(
    communties: List<String>,
    viewModel: MainViewModel?,
    modifier: Modifier = Modifier
) {
    communties.forEach {
        Communtity(text = it, modifier = modifier, onCommunityClicked = {
            viewModel?.selectedCommunity?.postValue(it)
            JetRedditRouter.goBack()
        })
    }
}

@Preview
@Composable
fun SeachedCommuntiesPreview() {
    Column {
        SeachedCommunties(defaultCommunties, null, Modifier)
    }
}