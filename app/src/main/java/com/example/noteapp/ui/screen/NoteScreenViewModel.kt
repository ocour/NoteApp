package com.example.noteapp.ui.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.database.Note
import com.example.noteapp.data.repository.NoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NoteScreenViewModel @Inject constructor(
    private val noteRepositoryImpl: NoteRepositoryImpl
) : ViewModel() {

    var selectedNote = mutableStateOf<Note?>(null)

    // Fetch all notes from db
    val noteScreenUiState: StateFlow<NoteScreenUiState> = noteRepositoryImpl
        .getAllNotes()
        .map { NoteScreenUiState(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            NoteScreenUiState()
        )

    /**
     *  Delete selected note from db
     */
    suspend fun deleteNote() {
        if (selectedNote.value != null) {
            noteRepositoryImpl.deleteNote(selectedNote.value!!)
        }
    }
}

data class NoteScreenUiState(
    val notes: List<Note> = listOf()
)