package com.example.noteapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.noteapp.R
import com.example.noteapp.ui.components.MyTopAppbar
import com.example.noteapp.ui.components.NoteDetails
import com.example.noteapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object NoteEditScreenDestination : NavigationDestination {
    override val route: String = "note_edit_screen"
    override val titleRes: Int = R.string.note_edit_screen_title
    const val noteIdArg = "noteId"
    val routeWithArgs = "$route/{$noteIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    onDone: () -> Unit,
    viewModel: NoteEditScreenViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var isTitleError by remember { mutableStateOf(false) }
    val errorMsg: String = stringResource(id = R.string.note_title_required)

    val noteUiState = viewModel.noteUiState.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            MyTopAppbar(
                title = stringResource(id = NoteEditScreenDestination.titleRes),
                canNavigateBack = true,
                onNavigation = onNavigationBack
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(onClick = {
                /* Update note on click */
                if (noteUiState.value.title.isNotEmpty()) {
                    coroutineScope.launch {
                        viewModel.updateNote()
                        onDone()
                    }
                }
                else {
                    isTitleError = true
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message = errorMsg)
                    }
                }
            }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(id = R.string.note_edit_screen_fab_desc)
                )
            }
        }
    ) { paddingValues ->
        NoteDetails(
            modifier = modifier.padding(paddingValues),
            noteUiState = noteUiState.value,
            onTitleChange = { viewModel.updateNoteUiState(noteUiState.value.copy(title = it)) },
            onContentChange = { viewModel.updateNoteUiState(noteUiState.value.copy(content = it)) },
            titleErrorState = isTitleError
        )
    }
}