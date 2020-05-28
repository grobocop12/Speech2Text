package com.grobocop.speech2text.utils.recognizer

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.content.Context

class SpeechRecognizerFactory()  {

    fun createRecognizer(
        context: Context?,
        speechRecognizerObserver: SpeechRecognizerObserver
    ): SpeechRecognizer {
        val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
        recognizer.setRecognitionListener(object : RecognitionListener {
            var singleResult = true
            override fun onBeginningOfSpeech() {}
            override fun onEndOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
            override fun onReadyForSpeech(params: Bundle?) {
                speechRecognizerObserver.onReadyForSpeech()
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults
                    ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                matches?.get(0)?.trim().toString().let {
                    if (it.isNotEmpty()) {
                        speechRecognizerObserver.onPartialResult(it)
                    }
                }
            }

            override fun onError(error: Int) {
                speechRecognizerObserver.onError(error)
                speechRecognizerObserver.onStop()
            }

            override fun onResults(results: Bundle?) {
                if (singleResult) {
                    results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).let {
                        speechRecognizerObserver.onResult(it?.get(0))
                    }
                    singleResult = false
                }
                speechRecognizerObserver.onStop()
            }
        })
        return recognizer
    }
}
