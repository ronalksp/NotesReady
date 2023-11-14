package com.apu.notesready.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.apu.notesready.presentation.bookmark.BookMarkViewModel
import com.apu.notesready.presentation.bookmark.BookmarkScreen
import com.apu.notesready.presentation.detail.DetailAssistedFactory
import com.apu.notesready.presentation.detail.DetailScreen
import com.apu.notesready.presentation.home.HomeScreen
import com.apu.notesready.presentation.home.HomeViewModel

enum class Screen {
    Home, Detail, Bookmark
}

@Composable
fun NoteNavigation(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    bookMarkViewModel: BookMarkViewModel,
    assistedFactory: DetailAssistedFactory
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.name,

        ) {
        composable(route = Screen.Home.name) {
            val state by homeViewModel.state.collectAsState()
            HomeScreen(
                state = state,
                onBookMarkChange = homeViewModel::onBookMarkedChange,
                onDelete = homeViewModel::deleteNote,
                onNoteClicked = {
                    navHostController.navigateToSingleTop(
                        route = "${Screen.Detail.name}?id=${it}"
                    )
                }
            )
        }

        composable(route = Screen.Bookmark.name) {
            val state by bookMarkViewModel.state.collectAsState()
            BookmarkScreen(
                state = state,
                modifier = Modifier,
                onBookMarkChange = bookMarkViewModel::obBookmarkChange,
                onDelete = bookMarkViewModel::deleteNote,
                onNoteClicked = {
                    navHostController.navigateToSingleTop(
                        route = "${Screen.Detail.name}?id=${it}"
                    )
                }
            )
        }

        composable(
            route = "${Screen.Detail.name}?id={id}",
            arguments = listOf(navArgument("id") {
                NavType.LongType
                defaultValue = -1L
            }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: -1
            DetailScreen(
                noteId = id,
                assistedFactory = assistedFactory,
                navigateUp = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}

fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}