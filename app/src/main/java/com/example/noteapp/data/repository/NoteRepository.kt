package com.example.noteapp.data.repository

import com.example.noteapp.data.database.Note
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, delete, update and retrieves [item] or [items] from given data source
 */
interface NoteRepository {

    /**
     *  Inserts a note to data source
     */
    suspend fun insertNote(note: Note)

    /**
     *  Updates a note in data source
     */
    suspend fun updateNote(note: Note)

    /**
     *  Deletes note from the data source
     */
    suspend fun deleteNote(note: Note)

    /**
     *  Gets all notes from data source as a flow
     */
    fun getAllNotes(): Flow<List<Note>>

    /**
     *  Retrieves a note from data source which matches with [id]
     */
    fun getNote(id: Int): Flow<Note>
}