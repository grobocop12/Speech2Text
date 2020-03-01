package com.grobocop.speech2text.utils

import android.app.AlertDialog
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import java.util.*

object SpeechRecognizerProvider {
    fun createSpeechRecognizer(context: Context?): SpeechRecognizer {
        val recognizer = SpeechRecognizer.createSpeechRecognizer(context)

        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {

            }

            override fun onRmsChanged(rmsdB: Float) {

            }

            override fun onBufferReceived(buffer: ByteArray?) {

            }

            override fun onPartialResults(partialResults: Bundle?) {

            }

            override fun onEvent(eventType: Int, params: Bundle?) {

            }

            override fun onBeginningOfSpeech() {

            }

            override fun onEndOfSpeech() {
                Toast.makeText(context, "Koniec", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: Int) {

            }

            override fun onResults(results: Bundle?) {
                Toast.makeText(context, "Koniec", Toast.LENGTH_SHORT).show()
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
        intent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 500
        )
        return intent
    }


}