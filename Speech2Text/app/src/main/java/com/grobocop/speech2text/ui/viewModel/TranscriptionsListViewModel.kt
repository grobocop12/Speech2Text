package com.grobocop.speech2text.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.data.TranscriptionRepository


class TranscriptionsListViewModel(application: Application) : AndroidViewModel(application) {

    private var transcriptionRepository: TranscriptionRepository =
        TranscriptionRepository(application)

    fun addTranscription(transcription: Transcription) =
        transcriptionRepository.addTranscription(transcription)

    fun getTranscriptions() = transcriptionRepository.getTranscriptions()

    fun getTranscription(index: Int) = transcriptionRepository.getTranscription(index)

}