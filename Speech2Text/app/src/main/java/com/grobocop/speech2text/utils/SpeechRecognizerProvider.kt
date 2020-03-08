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
            override fun onReadyForSpeech(params: Bundle?) {
                speechRecognizerObserver.onReadyForSpeech()
            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches =
                    partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null && matches[0].isNotBlank()) {
                    speechRecognizerObserver.onPartialResult(matches[0])
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }

            override fun onBeginningOfSpeech() {

            }

            override fun onEndOfSpeech() {

            }

            override fun onError(error: Int) {

            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (matches != null) {
                    speechRecognizerObserver.onResult(matches[0])
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