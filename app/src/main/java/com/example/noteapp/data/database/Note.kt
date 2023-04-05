package com.example.noteapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val content: String,
    @ColumnInfo(name = "creation_timestamp") val creationTimestamp: Long,
    @ColumnInfo(name = "edit_timestamp") val editTimestamp: Long,
    val pinned: Boolean,
    val color: String,
)
