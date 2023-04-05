package com.example.noteapp.data.repository

import com.example.noteapp.data.database.Note
import com.example.noteapp.data.database.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun insertNote(note: Note) =  noteDao.insertNote(note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    override fun getNote(id: Int): Flow<Note> = noteDao.getNote(id)
}