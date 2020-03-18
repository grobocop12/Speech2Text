package com.grobocop.speech2text.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.data.TranscriptionRepository

class TranscriptionViewModel(application: Application) :
    AndroidViewModel(application) {

    private  var repository = TranscriptionRepository(application)

    fun addItem(transcription: Transcription) = repository.addTranscription(transcription)

    fun getTranscription(index: Int): LiveData<Transcription>? = repository.getTranscription(index)

}