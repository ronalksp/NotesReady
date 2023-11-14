package com.apu.notesready

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.apu.notesready.presentation.bookmark.BookMarkViewModel
import com.apu.notesready.presentation.detail.DetailAssistedFactory
import com.apu.notesready.presentation.home.HomeViewModel
import com.apu.notesready.presentation.navigation.NoteNavigation
import com.apu.notesready.presentation.navigation.Screen
import com.apu.notesready.presentation.navigation.navigateToSingleTop
import com.apu.notesready.ui.theme.NotesReadyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var assistedFactory: DetailAssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesReadyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    NoteApp()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NoteApp() {
        val homeViewModel: HomeViewModel = viewModel()
        val bookMarkViewModel: BookMarkViewModel = viewModel()
        val navHostController = rememberNavController()
        var currentTab by remember {
            mutableStateOf(TabScreen.Home)
        }

        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            InputChip(
                                selected = currentTab == TabScreen.Home,
                                onClick = {
                                    currentTab = TabScreen.Home
                                    navHostController.navigateToSingleTop(route = Screen.Home.name)
                                },
                                label = {
                                    Text(text = "")
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Home, contentDescription = ""
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.Companion.size(12.dp))

                            InputChip(
                                selected = currentTab == TabScreen.Home,
                                onClick = {
                                    currentTab = TabScreen.Bookmark
                                    navHostController.navigateToSingleTop(route = Screen.Bookmark.name)
                                },
                                label = {
                                    Text(text = "Bookmark")
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark, contentDescription = ""
                                    )
                                }
                            )
                        }
                        

                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navHostController.navigateToSingleTop(route = Screen.Detail.name)
                        }) {
                            Icon(imageVector = Icons.Default.Add , contentDescription = null)
                        }
                    }
                )
            }
        ) {
            NoteNavigation(
                modifier = Modifier.padding(it),
                navHostController = navHostController,
                homeViewModel = homeViewModel,
                bookMarkViewModel = bookMarkViewModel,
                assistedFactory = assistedFactory
            )
        }
    }

}

enum class TabScreen {
    Home, Bookmark
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesReadyTheme {
        Greeting("Android")
    }
}