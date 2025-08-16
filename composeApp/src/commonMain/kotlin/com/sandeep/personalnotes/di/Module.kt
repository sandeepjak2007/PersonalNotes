package com.sandeep.personalnotes.di

import com.sandeep.personalnotes.db.HttpClientFactory
import com.sandeep.personalnotes.db.NoteDatabase
import com.sandeep.personalnotes.repo.NoteRepository
import com.sandeep.personalnotes.ui.NoteViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    viewModelOf(::NoteViewModel)
    single<NoteDatabase> {
        NoteDatabase(get())
    }
    single<NoteRepository> {
        NoteRepository(get(), get())
    }
}

expect fun saveToFile(path: String, data: ByteArray)