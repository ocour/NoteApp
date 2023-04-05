package com.example.noteapp.ui.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.data.database.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: () -> Unit,
    onDeletion: (Note) -> Unit
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .heightIn(min = 30.dp, max = 260.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = { onDeletion(note) }
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                modifier = modifier.padding(8.dp),
                text = note.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSecondary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(
                modifier = modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                text = note.content,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun NoteCardPreview() {
    NoteCard(
        note = Note(
            id = 0,
            title = "Title 1",
            content = "Content 1",
            creationTimestamp = 0,
            editTimestamp = 0,
            color = "primary",
            pinned = false
        ),
        onClick = {},
        onDeletion = {}
    )
}