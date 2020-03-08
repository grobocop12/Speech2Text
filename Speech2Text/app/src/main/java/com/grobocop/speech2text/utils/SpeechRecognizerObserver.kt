package com.grobocop.speech2text.utils

interface SpeechRecognizerObserver {
    fun onResult(result: String?)
    fun onReadyForSpeech()
    fun onPartialResult(result: String?)
}