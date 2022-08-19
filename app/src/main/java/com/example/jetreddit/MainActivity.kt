package com.example.jetreddit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.jetreddit.theme.JetRedditTheme
import com.example.jetreddit.viewmodel.MainViewModel
import com.example.jetreddit.viewmodel.MainViewModelFactory


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels(factoryProducer = {
        MainViewModelFactory(
            this,
            (application as JetRedditApplication).dependencyInjector.repository
        )
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetRedditTheme {
                JetRedditApp(viewModel)
            }
        }
    }
}

