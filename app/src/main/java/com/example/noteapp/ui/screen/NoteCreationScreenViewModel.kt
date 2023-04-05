package com.example.noteapp.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.repository.NoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "NoteCreationScreen"

@HiltViewModel
class NoteCreationScreenViewModel @Inject constructor(
    private val noteRepositoryImpl: NoteRepositoryImpl
) : ViewModel() {
    private val _noteUiState = MutableStateFlow(NoteUiState())
    val noteUiState: StateFlow<NoteUiState> = _noteUiState.asStateFlow()

    /**
     *  Updates NoteUiState
     */
    fun updateNoteUiState(newNoteUiState: NoteUiState) {
        _noteUiState.update {
            newNoteUiState
        }
    }

    /**
     *  Creates a Note in Database, before creation sets timestamps and checks validity
     */
    suspend fun createNote() {
        if(_noteUiState.value.isValid()) {

            // Get Epoch timestamp
            val currentTimeStamp = System.currentTimeMillis() / 1000L

            _noteUiState.update {
                it.copy(
                    creationTimestamp = currentTimeStamp,
                    editTimestamp = currentTimeStamp
                )
            }

            noteRepositoryImpl.insertNote(_noteUiState.value.toNote())
        }
    }
}
