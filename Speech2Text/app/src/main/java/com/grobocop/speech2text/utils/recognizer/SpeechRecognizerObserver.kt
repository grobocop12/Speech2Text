package com.grobocop.speech2text.utils.recognizer

interface SpeechRecognizerObserver {
    fun onResult(result: String?)
    fun onReadyForSpeech()
    fun onPartialResult(result: String?)
    fun onError(error: Int)
    fun onStop()
}