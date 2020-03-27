package com.grobocop.speech2text.ui.fragments

import android.os.Bundle
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grobocop.speech2text.R
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.ui.viewModel.TranscriptionViewModel
import com.grobocop.speech2text.utils.SpeechRecognizerObserver
import com.grobocop.speech2text.utils.SpeechRecognizerProvider
import kotlinx.android.synthetic.main.fragment_edit.*
import java.util.*

class EditFragment : Fragment() {
    private lateinit var editViewModel: TranscriptionViewModel
    private var speechRecognizer: SpeechRecognizer? = null
    private var id: Int? = null
    private var isListening = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        id = arguments?.getInt("id")
        val root = inflater.inflate(R.layout.fragment_edit, container, false)
        setViewModel()
        setOnClickListeners()
        return root
    }

    override fun onStop() {
        val text = transcription_text_et.text.toString()
        val title = title_et.text.toString()
        if (text.isNotEmpty() || title.isNotEmpty()) {
            val newTranscription = Transcription(id, Date(), title, text)
            editViewModel.addItem(newTranscription)
        }
        super.onStop()
    }

    private fun setViewModel() {
        editViewModel = ViewModelProvider(this).get(TranscriptionViewModel::class.java)
        val id = this.id
        id?.let {
            editViewModel.getTranscription(id)?.observe(this.viewLifecycleOwner, Observer {
                transcription_text_et.setText(it.text)
            })
        }
    }

    private fun setOnClickListeners() {
        edit_fab.setOnClickListener {
            val theme = it.context.theme
            if (isListening) {
                speechRecognizer?.stopListening()
                edit_fab.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_fiber_record_white,
                        theme
                    )
                )
                last_word_tv.text = ""
                isListening = false
            } else {
                speechRecognizer = SpeechRecognizerProvider.createSpeechRecognizer(
                    this.activity?.applicationContext,
                    getRecognizerObserver()
                )
                val intent = SpeechRecognizerProvider.createSpeechRecognitionIntent()
                speechRecognizer?.startListening(intent)
                edit_fab.setImageDrawable(resources.getDrawable(R.drawable.ic_stop_white, theme))
                isListening = true
            }

        }
    }

    private fun getRecognizerObserver(): SpeechRecognizerObserver {
        return object : SpeechRecognizerObserver {
            override fun onResult(result: String?) {
                result.let {
                    val text = transcription_text_et.text.toString() + " " + it
                    transcription_text_et.setText(text)
                }
            }

            override fun onReadyForSpeech() {
                Toast.makeText(context, "Speak now!", Toast.LENGTH_SHORT).show()
            }

            override fun onPartialResult(result: String?) {
                last_word_tv.text = result
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
                edit_fab.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_fiber_record_white,
                        context?.theme
                    )
                )
                last_word_tv.text = ""
                isListening = false
            }
        }
    }
}
