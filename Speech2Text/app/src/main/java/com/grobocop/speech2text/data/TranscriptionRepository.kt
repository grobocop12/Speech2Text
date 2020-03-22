package com.grobocop.speech2text.data

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TranscriptionRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var transcriptionDao: TranscriptionsDao?

    init {
        val db = TranscriptionsDatabase.getDatabase(application)
        transcriptionDao = db?.transcriptionsDao()
    }

    fun addTranscription(transcription: Transcription) {
        launch {
            addTranscriptionBG(transcription)
        }
    }

    private suspend fun addTranscriptionBG(transcription: Transcription) {
        withContext(Dispatchers.IO) {
            transcriptionDao?.setTranscription(transcription)
        }
    }

    fun getTranscriptions() = transcriptionDao?.getTranscriptions()

    fun getTranscription(index: Int) = transcriptionDao?.getTranscription(index)
    fun deleteTranscription(id: Int) {
        launch {
            deleteTranscriptionBG(id)
        }
    }

    private suspend fun deleteTranscriptionBG(id: Int) {
        withContext(Dispatchers.IO) {
            transcriptionDao?.deleteTranscription(id)
        }
    }

}