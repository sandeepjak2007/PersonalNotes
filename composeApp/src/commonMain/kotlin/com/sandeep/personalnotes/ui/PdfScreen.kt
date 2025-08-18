package com.sandeep.personalnotes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun PdfScreen(viewModel: NoteViewModel, pdfUrl: String) {
    val state by viewModel.filePath.collectAsState()

    LaunchedEffect(pdfUrl) {
        viewModel.loadPdf(pdfUrl)

    }

    state?.let { filePath ->
        PlatformPdfViewer(filePath = filePath)
    }
}