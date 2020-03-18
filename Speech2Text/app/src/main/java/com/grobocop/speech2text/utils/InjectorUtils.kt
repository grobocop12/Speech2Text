package com.grobocop.speech2text.utils

import android.app.Application
import com.grobocop.speech2text.data.TranscriptionDatabase
import com.grobocop.speech2text.data.TranscriptionRepository
import com.grobocop.speech2text.ui.viewModel.Factory.TranscriptionViewModelFactory
import com.grobocop.speech2text.ui.viewModel.Factory.TranscriptionsListViewModelFactory
import android.content.Context

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