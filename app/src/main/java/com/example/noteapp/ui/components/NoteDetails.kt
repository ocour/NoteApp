package com.example.noteapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.ui.screen.NoteUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetails(
    modifier: Modifier = Modifier,
    noteUiState: NoteUiState,
    titleErrorState: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            value = noteUiState.title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(id = R.string.note_title_field)) },
            supportingText = { Text(text = "required*") },
            maxLines = 2,
            textStyle = MaterialTheme.typography.titleMedium,
            isError = titleErrorState
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .height(520.dp),
            value = noteUiState.content,
            onValueChange = { onContentChange(it) },
            label = { Text(text = stringResource(id = R.string.note_content_field)) },
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}