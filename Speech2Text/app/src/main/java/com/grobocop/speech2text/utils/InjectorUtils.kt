package com.grobocop.speech2text.utils

import android.app.Application
import com.grobocop.speech2text.ui.viewModel.Factory.TranscriptionViewModelFactory
import com.grobocop.speech2text.ui.viewModel.Factory.TranscriptionsListViewModelFactory

object InjectorUtils {
    fun provideTranscriptionsListViewModelFactory(application: Application): TranscriptionsListViewModelFactory {
        return TranscriptionsListViewModelFactory(
            application
        )
    }

    fun provideTranscriptionViewModelFactory(application: Application): TranscriptionViewModelFactory {

        return TranscriptionViewModelFactory(
            application
        )
    }
}