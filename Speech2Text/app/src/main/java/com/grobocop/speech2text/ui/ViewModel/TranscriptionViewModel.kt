package com.grobocop.speech2text.ui.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.data.TranscriptionRepository

class TranscriptionViewModel(private val transcriptionRepository: TranscriptionRepository) :
    ViewModel() {
    private lateinit var transcription: LiveData<Transcription>

    fun getLastOrNew() = transcriptionRepository.getLastOrNew().also {
        transcription = it
    }

    fun addItem() = transcriptionRepository.addTranscription(this.transcription.value!!)
}