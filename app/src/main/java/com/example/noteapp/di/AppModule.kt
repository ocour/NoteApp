package com.example.noteapp.di

import android.content.Context
import androidx.room.Room
import com.example.noteapp.data.database.AppDatabase
import com.example.noteapp.data.database.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "noteapp_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}