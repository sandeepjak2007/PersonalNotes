package com.sandeep.personalnotes.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.sandeep.personalnotes.AppContext
import com.sandeep.personalnotes.db.NoteDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.dsl.module
import java.io.File

actual val platformModule = module {
    single<HttpClientEngine> { OkHttp.create() }
    single<SqlDriver> {
        AndroidSqliteDriver(NoteDatabase.Schema, get(), "notes.sq")
    }
}

actual fun saveToFile(data: ByteArray): String {
    val file = File(cacheFilePath())
    file.writeBytes(data)
    return file.absolutePath
}

private fun cacheFilePath(): String {
    return File(AppContext.appContext.cacheDir, "download.pdf").absolutePath
}