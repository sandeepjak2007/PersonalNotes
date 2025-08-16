package com.sandeep.personalnotes.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun NoteAddEditScreen(
    viewModel: NoteViewModel,
    modifier: Modifier = Modifier,
    onDone: () -> Unit = {}
) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var createdDate by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()
        )
    }

    val statusEvent by viewModel.status.collectAsState(initial = null)

    val showSuccess = remember { mutableStateOf(false) }
    val showError = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(statusEvent) {
        when (val event = statusEvent) {
            is NoteEvent.NoteAdded -> {
                showSuccess.value = true
                showError.value = null
                delay(1500)
                showSuccess.value = false
            }

            is NoteEvent.NoteError -> {
                showError.value = event.message
                showSuccess.value = false
                delay(3000)
                showError.value = null
            }

            else -> {}
        }
    }
    Column(modifier = modifier.padding(18.dp)) {
        Text(
            text = "Create a new note",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 28.dp)
        )
        OutlinedTextField(
            label = { Text("Title") },
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            label = { Text("Body (HTML)") },
            value = body,
            onValueChange = { body = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(Modifier.height(8.dp))
        DatePickerLocalDate(
            LocalDate.parse(createdDate),
            onDateChange = { createdDate = it.toString() })
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { viewModel.addNote(title, body, createdDate) },
            enabled = title.isNotBlank() && body.isNotBlank(),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(visible = showSuccess.value) {
            Text(
                "Note saved successfully!",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )
            onDone()
        }
        AnimatedVisibility(visible = showError.value != null) {
            Text(
                "Error: ${showError.value}",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )
        }
    }
}