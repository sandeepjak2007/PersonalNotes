package com.sandeep.personalnotes

import android.app.Application
import com.sandeep.personalnotes.di.initKoin
import org.koin.android.ext.koin.androidContext

class PersonalNotes : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@PersonalNotes)
        }
    }
}