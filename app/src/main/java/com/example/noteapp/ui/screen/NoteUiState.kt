package com.example.noteapp.ui.screen

import com.example.noteapp.data.database.Note

data class NoteUiState(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val creationTimestamp: Long = 0,
    val editTimestamp: Long = 0,
    val pinned: Boolean = false,
    val color: String = "primary"
)

/**
 *  Checks validity of Note,
 *  [title] must not be empty,
 */
fun NoteUiState.isValid(): Boolean {
    return title.isNotEmpty()
}

fun NoteUiState.toNote(): Note = Note(
    id = id,
    title = title.trim(),
    content = content.trim(),
    creationTimestamp = creationTimestamp,
    editTimestamp = editTimestamp,
    pinned = pinned,
    color = color
)

fun Note.toNoteUiState(): NoteUiState = NoteUiState(
    id = id,
    title = title,
    content = content,
    creationTimestamp = creationTimestamp,
    editTimestamp = editTimestamp,
    pinned = pinned,
    color = color
)