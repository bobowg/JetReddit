package com.example.jetreddit

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.jetreddit.appdrawer.AppDrawer
import com.example.jetreddit.routing.JetRedditRouter
import com.example.jetreddit.routing.Screen
import com.example.jetreddit.screens.*
import com.example.jetreddit.theme.JetRedditTheme
import com.example.jetreddit.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun JetRedditApp(viewModel: MainViewModel) {
    JetRedditTheme {
        AppContent(viewModel)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun AppContent(viewModel: MainViewModel) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    Crossfade(targetState = JetRedditRouter.currentScreen) { screenState: MutableState<Screen> ->
        Scaffold(
            topBar = getTopBar(screenState.value, scaffoldState, coroutineScope),
            drawerContent = {
                AppDrawer(
                    closeDrawerAction = { coroutineScope.launch { scaffoldState.drawerState.close() } }
                )
            },
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigationComponent(screenState = screenState)
            },
            content = {
                MainScreenContainer(
                    screenState = screenState,
                    modifier = Modifier.padding(bottom = 56.dp),
                    viewModel = viewModel
                )
            }
        )
    }
}

@Composable
private fun MainScreenContainer(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>,
    viewModel: MainViewModel
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ) {
        when (screenState.value) {
            Screen.Home -> HomeScreen(viewModel)
            Screen.Subscriptions -> SubredditsScreen()
            Screen.NewPost -> AddScreen(viewModel)
            Screen.MyProfile -> MyProfileScreen(viewModel)
            Screen.ChooseCommunity -> ChooseCommunityScreen(viewModel)
        }
    }
}

@Composable
private fun BottomNavigationComponent(
    modifier: Modifier = Modifier,
    screenState: MutableState<Screen>
) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        NavigationItem(0, R.drawable.ic_home, R.string.home_icon, Screen.Home),
        NavigationItem(1, R.drawable.ic_format, R.string.subscriptions_icon, Screen.Subscriptions),
        NavigationItem(2, R.drawable.ic_add, R.string.post_icon, Screen.NewPost)
    )
    BottomNavigation(modifier = modifier) {
        items.forEach {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = it.vectorResourceId),
                        contentDescription = stringResource(
                            id = it.contentDescriptionResourceId
                        )
                    )
                },
                selected = selectedItem == it.Index,
                onClick = {
                    selectedItem = it.Index
                    screenState.value = it.screen
                }
            )
        }
    }
}

fun getTopBar(
    screenState: Screen,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
): @Composable (() -> Unit) {
    if (screenState == Screen.MyProfile) {
        return {}
    } else {
        return {
            TopAppBar(
                scaffoldState = scaffoldState,
                coroutineScope = coroutineScope
            )
        }
    }
}

@Composable
private fun TopAppBar(
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope
) {
    val colors = MaterialTheme.colors
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = JetRedditRouter.currentScreen.value.titleResId),
                color = colors.primaryVariant
            )
        },
        backgroundColor = colors.surface,
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch { scaffoldState.drawerState.open() }
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = stringResource(
                        id = R.string.account
                    ),
                    tint = Color.LightGray
                )
            }
        }
    )

}

data class NavigationItem(
    val Index: Int,
    val vectorResourceId: Int,
    val contentDescriptionResourceId: Int,
    val screen: Screen
)