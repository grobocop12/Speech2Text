package com.grobocop.speech2text.data

class TranscriptionRepository private constructor(private val transcriptionDao: TranscriptionDao) {
    fun addTranscription(transcription: Transcription) {
        transcriptionDao.addTranscription(transcription)
    }

    fun getTranscriptions() = transcriptionDao.getTranscriptions()

    fun getTranscription(index: Int) = transcriptionDao.getTranscription(index)

    fun getLastOrNew() = transcriptionDao.getLastOrNew()

    companion object {
        @Volatile
        private var instance: TranscriptionRepository? = null

        fun getInstance(transcriptionDao: TranscriptionDao) = instance ?: synchronized(this) {
            instance ?: TranscriptionRepository(transcriptionDao).also { instance = it }
        }
    }
}