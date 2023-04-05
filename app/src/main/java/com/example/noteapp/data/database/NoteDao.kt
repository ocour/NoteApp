package com.example.noteapp.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY edit_timestamp DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNote(id: Int): Flow<Note>
}