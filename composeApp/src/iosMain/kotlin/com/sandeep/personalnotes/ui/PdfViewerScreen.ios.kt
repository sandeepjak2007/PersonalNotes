package com.sandeep.personalnotes.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.PDFKit.PDFView
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformPdfViewer(filePath: String) {
    androidx.compose.ui.interop.UIKitView(
        factory = {
            val pdfView = PDFView()
            pdfView.autoScales = true
            pdfView.document =
                platform.PDFKit.PDFDocument(NSURL.fileURLWithPath(filePath))
            pdfView as UIView
        },
        modifier = Modifier.fillMaxSize()
    )
}