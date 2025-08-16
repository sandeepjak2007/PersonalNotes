package com.sandeep.personalnotes.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.sandeep.personalnotes.db.NoteDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.koin.dsl.module
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithBytes
import platform.Foundation.writeToURL

actual val platformModule = module {
    single<HttpClientEngine> { Darwin.create() }
    single<SqlDriver> {
        NativeSqliteDriver(NoteDatabase.Schema, "notes.sq")
    }
}

@OptIn(ExperimentalForeignApi::class)
actual fun saveToFile(path: String, data: ByteArray) {
    data.usePinned { pinned ->
        val nsData = NSData.dataWithBytes(pinned.addressOf(0), data.size.toULong())
        // Create NSURL file path string
        val fileUrl = NSURL.fileURLWithPath(path)
        nsData.writeToURL(fileUrl, true)
    }
}