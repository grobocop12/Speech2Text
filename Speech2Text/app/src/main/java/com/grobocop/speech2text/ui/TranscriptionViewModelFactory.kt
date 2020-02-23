package com.grobocop.speech2text.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grobocop.speech2text.data.TranscriptionRepository

class TranscriptionViewModelFactory(private val transcriptionRepository: TranscriptionRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TranscriptionViewModel(transcriptionRepository) as T
    }

}