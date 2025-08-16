package com.sandeep.personalnotes.ui

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
actual fun PlatformPdfViewer(viewModel: NoteViewModel) {
    var pages by remember { mutableStateOf<List<Bitmap>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    val filePath = viewModel.filePath.collectAsStateWithLifecycle()
    LaunchedEffect(filePath) {
        try {
            if (filePath.value.isNullOrBlank()) {
                val file = java.io.File(filePath.value!!)
                val pfd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                val renderer = PdfRenderer(pfd)
                val images = mutableListOf<Bitmap>()
                for (i in 0 until renderer.pageCount) {
                    val page = renderer.openPage(i)
                    val bmp = createBitmap(page.width, page.height)
                    page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    images.add(bmp)
                    page.close()
                }
                renderer.close()
                pfd.close()
                pages = images
            }
        } catch (e: Exception) {
            error = "Could not render PDF: ${e.localizedMessage}"
        }
    }
    when {
        error != null -> androidx.compose.material3.Text(error!!)
        pages == null -> androidx.compose.material3.CircularProgressIndicator()
        else -> LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(pages!!.size) { idx ->
                Image(pages!![idx].asImageBitmap(), null, Modifier.fillMaxSize())
            }
        }
    }
}