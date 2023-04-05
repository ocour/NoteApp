package com.example.noteapp.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.noteapp.R
import com.example.noteapp.ui.components.MyTopAppbar
import com.example.noteapp.ui.components.NoteCard
import com.example.noteapp.ui.components.NoteDeletionDialog
import com.example.noteapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object NoteScreenDestination : NavigationDestination {
    override val route: String = "note_screen"
    override val titleRes: Int = R.string.note_screen_title
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    onNavigation: () -> Unit,
    onNoteSelection: (Int) -> Unit,
    viewModel: NoteScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var alertState by rememberSaveable { mutableStateOf(false) }

    val homeScreenUiState = viewModel.noteScreenUiState.collectAsStateWithLifecycle()

    if(alertState) {
        NoteDeletionDialog(
            onDismiss = {
                alertState = false
                viewModel.selectedNote.value = null
            },
            onConfirm = {
                alertState = false
                coroutineScope.launch {
                    viewModel.deleteNote()
                }
            },
            title = stringResource(id = R.string.note_deletion_alert_title),
            content = context.getString(R.string.note_deletion_alert_content, viewModel.selectedNote.value?.title)
        )
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            MyTopAppbar(
                title = stringResource(id = NoteScreenDestination.titleRes),
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(onClick = onNavigation) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(id = R.string.note_screen_fab_description)
                )
            }
        }
    ) { paddingValues ->
        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                homeScreenUiState.value.notes,
                key = { it.id }
            ) { note ->
                NoteCard(
                    note = note,
                    onClick = { onNoteSelection(note.id) },
                    onDeletion = { noteToDelete ->
                        alertState = true
                        viewModel.selectedNote.value = noteToDelete
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun NoteScreenPreview() {
    NoteScreen(
        snackBarHostState = SnackbarHostState(),
        onNavigation = {},
        onNoteSelection = {},
    )
}