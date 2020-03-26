package com.grobocop.speech2text.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.grobocop.speech2text.R
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.ui.viewModel.TranscriptionViewModel
import com.grobocop.speech2text.utils.SpeechRecognizerProvider
import java.util.*
import com.grobocop.speech2text.utils.SpeechRecognizerObserver as SpeechRecognizerObserver

class EditFragment : Fragment() {
    private lateinit var editViewModel: TranscriptionViewModel
    private lateinit var transcriptionET: EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var lastWordTV: TextView
    private var speechRecognizer: SpeechRecognizer? = null
    private var id: Int? = null
    private var isListening = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        id = arguments?.getInt("id")
        val root = inflater.inflate(R.layout.fragment_edit, container, false)
        setUI(root)
        setViewModel()
        setOnClickListeners()
        return root
    }

    override fun onStop() {
        val text = transcriptionET.text.toString()
        if (text.isNotEmpty()) {
            val newTranscription = Transcription(id, Date(), text)
            editViewModel.addItem(newTranscription)
        }
        super.onStop()
    }

    private fun setViewModel() {
        editViewModel = ViewModelProvider(this).get(TranscriptionViewModel::class.java)
        val id = this.id
        id?.let {
            editViewModel.getTranscription(id)?.observe(this.viewLifecycleOwner, Observer {
                transcriptionET.setText(it.text)
            })
        }

    }

    private fun setUI(root: View) {
        transcriptionET = root.findViewById(R.id.transcription_text_et)
        fab = root.findViewById(R.id.edit_fab)
        lastWordTV = root.findViewById(R.id.last_word_tv)
    }

    private fun setOnClickListeners() {
        fab.setOnClickListener {
            val theme = it.context.theme
            if (isListening) {
                speechRecognizer?.stopListening()
                fab.setImageDrawable(resources.getDrawable(R.drawable.ic_fiber_record_white, theme))
                lastWordTV.text = ""
            } else {
                val observer = getRecognizerObserver()
                speechRecognizer = SpeechRecognizerProvider.createSpeechRecognizer(
                    this.activity?.applicationContext,
                    observer
                )
                val intent = SpeechRecognizerProvider.createSpeechRecognitionIntent()
                speechRecognizer?.startListening(intent)
                fab.setImageDrawable(resources.getDrawable(R.drawable.ic_stop_white, theme))
            }
            isListening = !isListening
        }
    }

    private fun getRecognizerObserver(): SpeechRecognizerObserver {
        return object : SpeechRecognizerObserver {

            override fun onResult(result: String?) {
                result.let {
                    val text = transcriptionET.text.toString() + " " + it
                    transcriptionET.setText(text)
                }

            }

            override fun onReadyForSpeech() {
                Toast.makeText(context, "Speak now!", Toast.LENGTH_SHORT).show()
            }

            override fun onPartialResult(result: String?) {
                lastWordTV.text = result
            }

            override fun onError(error: Int) {
                Toast.makeText(
                    this@EditFragment.context,
                    "Error occurred, recognition stopped.",
                    Toast.LENGTH_SHORT
                ).show()
                onStop()
            }

            override fun onStop() {

                fab.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_fiber_record_white,
                        context?.theme
                    )
                )
                lastWordTV.text = ""
                isListening = false
            }
        }
    }

    private fun setUpIntent(): Intent {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Recognizer will close automatically after "
                    + 500
                    + " milliseconds of complete silence."
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 500
        )
        return intent
    }
}
