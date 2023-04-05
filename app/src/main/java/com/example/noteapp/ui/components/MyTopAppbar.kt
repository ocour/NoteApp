package com.example.noteapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.noteapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppbar(
    modifier: Modifier = Modifier,
    title: String,
    canNavigateBack: Boolean = true,
    onNavigation: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = onNavigation) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = stringResource(id = R.string.navigate_back),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Preview()
@Composable
fun MyTopAppBarPreview() {
    MyTopAppbar(
        title = "Create a note",
        canNavigateBack = true
    )
}