package com.sandeep.personalnotes.ui

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

fun millisToLocalDate(millis: Long): LocalDate =
    Instant.fromEpochMilliseconds(millis)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

fun localDateToMillis(date: LocalDate): Long =
    date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()

fun LocalDate.formatDate(): String {
    val day = dayOfMonth.toString().padStart(2, '0')
    val month = monthNumber.toString().padStart(2, '0')
    val year = year.toString()
    return "$day-$month-$year"
}

sealed class Screen(val route: String) {
    object Notes : Screen("notes")
    object PdfViewer : Screen("pdf_viewer")
    data class NoteEdit(val noteId: Long?) : Screen("note_edit?noteId={noteId}") {
        fun createRoute(noteId: Long?) = "note_edit?noteId=${noteId ?: ""}"
    }
}

val bottomNavScreens = listOf(Screen.Notes, Screen.PdfViewer)