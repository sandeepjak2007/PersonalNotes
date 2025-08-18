package com.sandeep.personalnotes.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.sandeep.personalnotes.db.Note
import com.sandeep.personalnotes.db.NoteDatabase
import com.sandeep.personalnotes.db.NotesQueries
import com.sandeep.personalnotes.di.saveToFile
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

class NoteRepository(
    noteDatabase: NoteDatabase,
    private val httpClient: HttpClient
) {
    val notesQueries: NotesQueries = noteDatabase.notesQueries

    fun insertNote(title: String, bodyHtml: String, createdDate: String): Result<Unit> =
        runCatching {
            notesQueries.insertNote(title, bodyHtml, createdDate)
        }

    fun getAllNotesFlow(): Result<Flow<List<Note>>> = runCatching {
        notesQueries.selectAllNotes()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun getNoteById(id: Long): Result<Note?> = runCatching {
        notesQueries.selectNoteById(id).executeAsOneOrNull()
    }

    fun updateNote(id: Long, title: String, bodyHtml: String, createdDate: String): Result<Unit> =
        runCatching {
            notesQueries.updateNoteById(title, bodyHtml, createdDate, id)
        }

    fun deleteNoteById(id: Long): Result<Unit> = runCatching {
        notesQueries.deleteNoteById(id)
    }

    suspend fun downloadPdfToCache(url: String): String {
        val response: ByteArray = httpClient.get(url).body()
        return saveToFile(response)
    }


}