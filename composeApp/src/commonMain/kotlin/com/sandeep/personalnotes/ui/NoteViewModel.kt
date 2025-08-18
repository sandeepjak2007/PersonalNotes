package com.sandeep.personalnotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeep.personalnotes.db.Note
import com.sandeep.personalnotes.repo.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _status = MutableSharedFlow<NoteEvent>(extraBufferCapacity = 1)
    val status = _status.asSharedFlow()

    private val _filePath = MutableStateFlow<String?>(null)
    val filePath = _filePath.asStateFlow()

    private val _currentNote = MutableStateFlow<Note?>(null)
    val currentNote: StateFlow<Note?> = _currentNote.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllNotesFlow().onSuccess { notesList ->
                notesList.collect {
                    println("Notes fetched successfully $it.")
                    _notes.value = it
                }

            }.onFailure {
                _status.emit(NoteEvent.NoteError(it.message ?: "Unknown error"))
            }
        }
    }

    fun getNoteById(id: Long) = viewModelScope.launch {
        repository.getNoteById(id).onSuccess {
            _currentNote.value = it
        }.onFailure { e ->
            _status.emit(NoteEvent.NoteError("Failed to fetch note: ${e.message}"))
        }
    }

    fun clearCurrentNote() {
        _currentNote.value = null
    }

    fun addNote(title: String, bodyHtml: String, createdDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(title, bodyHtml, createdDate).onSuccess {
                _status.emit(NoteEvent.NoteAdded)
            }.onFailure { e ->
                _status.emit(NoteEvent.NoteError("Failed to add note: ${e.message}"))
            }
        }
    }

    fun updateNote(id: Long, title: String, bodyHtml: String, createdDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(id, title, bodyHtml, createdDate).onSuccess {
                _status.emit(NoteEvent.NoteUpdated)
            }.onFailure { e ->
                _status.emit(NoteEvent.NoteError("Failed to update note: ${e.message}"))
            }
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNoteById(id).onSuccess {
                _status.emit(NoteEvent.NoteDeleted)
            }.onFailure { e ->
                _status.emit(NoteEvent.NoteError("Failed to delete note: ${e.message}"))
            }
        }
    }

    fun loadPdf(url: String) = viewModelScope.launch {
        val path = repository.downloadPdfToCache(url)
        _filePath.value = path
    }
}