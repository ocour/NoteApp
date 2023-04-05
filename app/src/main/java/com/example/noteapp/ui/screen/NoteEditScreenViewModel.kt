package com.example.noteapp.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.repository.NoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditScreenViewModel @Inject constructor(
    private val noteRepositoryImpl: NoteRepositoryImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteId: Int = checkNotNull(savedStateHandle[NoteEditScreenDestination.noteIdArg])

    private val _noteUiState = MutableStateFlow(NoteUiState())
    val noteUiState: StateFlow<NoteUiState> = _noteUiState.asStateFlow()

    init {
        viewModelScope.launch {
            _noteUiState.value = noteRepositoryImpl.getNote(noteId)
                .filterNotNull()
                .first()
                .toNoteUiState()
        }
    }

    /**
     *  Updates NoteUiState
     */
    fun updateNoteUiState(newNoteUiState: NoteUiState) {
        _noteUiState.update {
            newNoteUiState
        }
    }

    /**
     *  Update edited note,
     *  Set edit_timestamp to current timestamp
     */
    suspend fun updateNote() {
        if(_noteUiState.value.isValid()) {
            _noteUiState.update {
                it.copy(editTimestamp = (System.currentTimeMillis() / 1000L))
            }

            noteRepositoryImpl.updateNote(_noteUiState.value.toNote())
        }
    }
}