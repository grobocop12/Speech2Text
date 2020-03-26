package com.grobocop.speech2text.utils

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import java.util.*

object SpeechRecognizerProvider {

    fun createSpeechRecognizer(
        context: Context?,
        speechRecognizerObserver: SpeechRecognizerObserver
    ): SpeechRecognizer {
        val recognizer = SpeechRecognizer.createSpeechRecognizer(context)

        recognizer.setRecognitionListener(object : RecognitionListener {

            var singleResult = true
            override fun onReadyForSpeech(params: Bundle?) {
                speechRecognizerObserver.onReadyForSpeech()
            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

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

            override fun onEvent(eventType: Int, params: Bundle?) {

            }

            override fun onBeginningOfSpeech() {

            }

            override fun onEndOfSpeech() {

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
                    speechRecognizerObserver.onStop()

                }
            }
        })
        return recognizer
    }

    fun createSpeechRecognitionIntent(): Intent? {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        return intent
    }
}
