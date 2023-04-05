package com.example.noteapp.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteapp.ui.screen.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteNavGraph() {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = NoteScreenDestination.route
    ) {
        composable(NoteScreenDestination.route) {
            NoteScreen(
                snackBarHostState = snackBarHostState,
                onNavigation = {
                    navController.navigate(NoteCreationScreenDestination.route)
                },
                onNoteSelection = { navController.navigate("${NoteEditScreenDestination.route}/$it") }
            )
        }
        composable(NoteCreationScreenDestination.route) {
            NoteCreationScreen(
                onDone = {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            "Note successfully created.",
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }
                    navController.navigateUp()
                },
                onNavigationBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = NoteEditScreenDestination.routeWithArgs,
            arguments = listOf(navArgument(NoteEditScreenDestination.noteIdArg) {
                type = NavType.IntType
            })
        ) {
            NoteEditScreen(
                onDone = {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(
                            "Note successfully edited.",
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }
                    navController.navigateUp()
                },
                onNavigationBack = { navController.popBackStack() }
            )
        }
    }
}