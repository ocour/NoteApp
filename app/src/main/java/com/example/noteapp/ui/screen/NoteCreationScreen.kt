package com.example.noteapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.noteapp.R
import com.example.noteapp.ui.components.MyTopAppbar
import com.example.noteapp.ui.components.NoteDetails
import com.example.noteapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object NoteCreationScreenDestination : NavigationDestination {
    override val route: String = "note_creation_screen"
    override val titleRes: Int = R.string.note_creation_screen_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteCreationScreen(
    modifier: Modifier = Modifier,
    onDone: () -> Unit,
    onNavigationBack: () -> Unit,
    viewModel: NoteCreationScreenViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val noteUiState = viewModel.noteUiState.collectAsStateWithLifecycle()

    var isTitleError by remember { mutableStateOf(false) }
    val errorMsg: String = stringResource(id = R.string.note_title_required)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            MyTopAppbar(
                title = stringResource(id = NoteCreationScreenDestination.titleRes),
                canNavigateBack = true,
                onNavigation = onNavigationBack
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(onClick = {
                if (noteUiState.value.title.isNotEmpty()) {
                    coroutineScope.launch {
                        viewModel.createNote()
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
                    contentDescription = stringResource(id = R.string.note_creation_screen_fab_desc)
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

@Preview
@Composable
fun NoteCreationScreenPreview() {
    NoteCreationScreen(onDone = {}, onNavigationBack = {})
}