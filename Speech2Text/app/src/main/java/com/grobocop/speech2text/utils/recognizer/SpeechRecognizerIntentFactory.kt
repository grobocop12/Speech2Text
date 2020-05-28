package com.grobocop.speech2text.utils.recognizer

import android.content.Intent
import android.speech.RecognizerIntent
import android.view.textclassifier.TextClassifierEvent
import org.intellij.lang.annotations.Language
import java.util.*

class SpeechRecognizerIntentFactory {
    fun createIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            .putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    }

}
