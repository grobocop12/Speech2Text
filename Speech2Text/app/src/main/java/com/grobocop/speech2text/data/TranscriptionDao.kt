package com.grobocop.speech2text.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

class TranscriptionDao {
    private val transcriptionsList = mutableListOf<Transcription>()
    private val transcriptions = MutableLiveData<List<Transcription>>()

    init {
        transcriptions.value = transcriptionsList
    }

    fun addTranscription(transcription: Transcription) {
        if (!transcriptionsList.contains(transcription) && transcription.text.isNotEmpty()) {
            transcriptionsList.add(transcription)
            transcriptions.value = transcriptionsList
        }
    }

    fun getTranscriptions() = transcriptions as LiveData<List<Transcription>>

    fun getTranscription(index: Int) = MutableLiveData<Transcription>().apply {
        value = transcriptionsList[index]
    } as LiveData<Transcription>

    fun getNew() = MutableLiveData<Transcription>().apply {
        value = Transcription("", Date())
    } as LiveData<Transcription>
}