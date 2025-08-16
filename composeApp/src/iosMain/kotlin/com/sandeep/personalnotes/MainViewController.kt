package com.sandeep.personalnotes

import androidx.compose.ui.window.ComposeUIViewController
import com.sandeep.personalnotes.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}