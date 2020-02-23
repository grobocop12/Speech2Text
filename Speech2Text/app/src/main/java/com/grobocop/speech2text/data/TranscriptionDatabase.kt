package com.grobocop.speech2text.data

class TranscriptionDatabase private constructor() {
    var transcriptionDao = TranscriptionDao()
        private set

    companion object {
        @Volatile
        private var instance: TranscriptionDatabase? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: TranscriptionDatabase().also { instance = it }
        }
    }
}