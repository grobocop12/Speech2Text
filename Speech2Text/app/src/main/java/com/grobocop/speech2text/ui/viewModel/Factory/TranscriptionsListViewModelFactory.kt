package com.grobocop.speech2text.ui.viewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.grobocop.speech2text.data.TranscriptionRepository
import com.grobocop.speech2text.ui.viewModel.TranscriptionsListViewModel

class TranscriptionsListViewModelFactory(private val transcriptionRepository: TranscriptionRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TranscriptionsListViewModel(
            transcriptionRepository
        ) as T
    }
}