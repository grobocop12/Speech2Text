package com.grobocop.speech2text.utils

interface SpeechRecognizerObserver {
    fun onResult(result: String?)
}