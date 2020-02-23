package com.grobocop.speech2text.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.data.TranscriptionRepository

class TranscriptionViewModel(private val transcriptionRepository: TranscriptionRepository) :
    ViewModel() {
    private lateinit var transcription: LiveData<Transcription>

    fun getNew() = transcriptionRepository.getNew().also {
        transcription = it
    }

    fun addItem() = transcriptionRepository.addTranscription(this.transcription.value!!)
    fun getTranscription(index: Int) = transcriptionRepository.getTranscription(index).also {
        transcription = it
    }

    fun setText(text: String) {
        transcription.value?.text = text
    }
}