package com.grobocop.speech2text.ui

import androidx.lifecycle.ViewModel
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.data.TranscriptionRepository


class TranscriptionsListViewModel(private val transcriptionRepository: TranscriptionRepository) :
    ViewModel() {
    fun addTranscription(transcription: Transcription) =
        transcriptionRepository.addTranscription(transcription)

    fun getTranscriptions() = transcriptionRepository.getTranscriptions()

    fun getTranscription(index: Int) = transcriptionRepository.getTranscription(index)

}