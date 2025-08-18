package com.sandeep.personalnotes

import android.app.Application
import android.content.Context
import com.sandeep.personalnotes.di.initKoin
import org.koin.android.ext.koin.androidContext

class PersonalNotes : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContext.appContext = applicationContext
        initKoin {
            androidContext(this@PersonalNotes)
        }
    }
}

object AppContext {
    lateinit var appContext: Context
}