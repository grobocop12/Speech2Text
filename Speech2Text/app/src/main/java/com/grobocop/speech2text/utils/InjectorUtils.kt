package com.grobocop.speech2text.utils

import com.grobocop.speech2text.data.TranscriptionDatabase
import com.grobocop.speech2text.data.TranscriptionRepository
import com.grobocop.speech2text.ui.TranscriptionViewModelFactory
import com.grobocop.speech2text.ui.TranscriptionsListViewModelFactory

object InjectorUtils {
    fun provideTranscriptionsListViewModelFactory(): TranscriptionsListViewModelFactory {
        val transcriptionRepository =
            TranscriptionRepository.getInstance(TranscriptionDatabase.getInstance().transcriptionDao)
        return TranscriptionsListViewModelFactory(transcriptionRepository)
    }

    fun provideTranscriptionViewModelFactory(): TranscriptionViewModelFactory {
        val transcriptionRepository =
            TranscriptionRepository.getInstance(TranscriptionDatabase.getInstance().transcriptionDao)
        return TranscriptionViewModelFactory(transcriptionRepository)
    }
}