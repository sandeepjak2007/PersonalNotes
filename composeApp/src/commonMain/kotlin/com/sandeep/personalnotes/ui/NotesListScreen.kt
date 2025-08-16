package com.sandeep.personalnotes.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun NotesListScreen(
    viewModel: NoteViewModel,
    modifier: Modifier = Modifier,
    onNoteClick: (Long) -> Unit = {},
    onAddNote: () -> Unit = {}
) {
    val notes by viewModel.notes.collectAsState(initial = emptyList())
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf<String?>(null) }
    val statusEvent by viewModel.status.collectAsState(initial = null)

    // Listen for status events (optional, similar to your edit screen)
    LaunchedEffect(statusEvent) {
        when (val event = statusEvent) {
            is NoteEvent.NoteAdded -> {
                showSuccess = true
                showError = null
                kotlinx.coroutines.delay(1500)
                showSuccess = false
            }

            is NoteEvent.NoteError -> {
                showError = event.message
                showSuccess = false
                kotlinx.coroutines.delay(3000)
                showError = null
            }

            else -> {}
        }
    }

    Column(modifier = modifier.padding(18.dp)) {
        Text(
            text = "Your Notes",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Button(
            onClick = onAddNote,
            modifier = Modifier.align(Alignment.End).padding(bottom = 14.dp)
        ) {
            Text("Add Note")
        }

        if (notes.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No notes yet.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 28.dp)
            ) {
                items(notes) { note ->
                    NoteListItem(
                        title = note.title,
                        summary = note.bodyHtml,
                        date = note.createdDate,
                        onClick = { onNoteClick(note.id) }
                    )
                    Divider()
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(visible = showSuccess) {
            Text(
                "Note saved successfully!",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
        AnimatedVisibility(visible = showError != null) {
            Text(
                "Error: $showError",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}