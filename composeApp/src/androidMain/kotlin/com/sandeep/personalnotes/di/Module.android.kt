package com.sandeep.personalnotes.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
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

actual fun saveToFile(path: String, data: ByteArray) {
    val file = File(path)
    file.writeBytes(data)
}