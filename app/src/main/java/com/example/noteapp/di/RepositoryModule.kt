package com.example.noteapp.di

import com.example.noteapp.data.repository.NoteRepository
import com.example.noteapp.data.repository.NoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepositoryImpl(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository
}