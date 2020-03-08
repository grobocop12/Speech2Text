package com.grobocop.speech2text.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.data.TranscriptionRepository

class TranscriptionViewModel(private val transcriptionRepository: TranscriptionRepository) :
    ViewModel() {
    private var transcription: MutableLiveData<Transcription>? = null

    fun getNew(): LiveData<Transcription> = transcription ?: transcriptionRepository.getNew().also {
        transcription = it
    }

    fun addItem() = transcriptionRepository.addTranscription(this.transcription?.value!!)

    fun getTranscription(index: Int): LiveData<Transcription> =
        transcription ?: transcriptionRepository.getTranscription(index).also {
            transcription = it
        }

    fun setText(text: String) {
        transcription?.value?.text = text
    }

    fun appendText(text: String?) {
        transcription?.value?.text = transcription?.value?.text + " $text"
        transcription?.postValue(transcription!!.value)
    }
}