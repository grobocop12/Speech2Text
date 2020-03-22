package com.grobocop.speech2text.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.speech.SpeechRecognizer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.grobocop.speech2text.R
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.ui.viewModel.TranscriptionViewModel
import com.grobocop.speech2text.utils.SpeechRecognizerObserver
import com.grobocop.speech2text.utils.SpeechRecognizerProvider
import java.util.*

class EditFragment : Fragment() {
    private lateinit var editViewModel: TranscriptionViewModel
    private lateinit var transcriptionET: EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var recognizerObserver: SpeechRecognizerObserver
    private var id: Int? = null
    private var isListening = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        id = arguments?.getInt("id")
        val root = inflater.inflate(R.layout.fragment_edit, container, false)
        setViewModel()
        setUI(root)
        setRecognizerListener()
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
    }

    private fun setOnClickListeners() {
        fab.setOnClickListener {
            val theme = it.context.theme
            if (isListening) {
                speechRecognizer.stopListening()
                fab.setImageDrawable(resources.getDrawable(R.drawable.ic_play_arrow, theme))
            } else {
                val intent = SpeechRecognizerProvider.createSpeechRecognitionIntent()
                speechRecognizer.startListening(intent)
                fab.setImageDrawable(resources.getDrawable(R.drawable.ic_pause, theme))
            }
            isListening = !isListening
        }
    }

    private fun setRecognizerListener() {
        recognizerObserver = object : SpeechRecognizerObserver {

            val dialog = AlertDialog.Builder(this@EditFragment.context)
                .setCancelable(true)
                .create()

            override fun onResult(result: String?) {
                dialog.dismiss()
                fab.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_play_arrow,
                        this@EditFragment.context?.theme
                    )
                )
                isListening = false
            }

            override fun onReadyForSpeech() {
                Toast.makeText(this@EditFragment.context, "Speak now!", Toast.LENGTH_SHORT).show()
            }

            override fun onPartialResult(result: String?) {
            }
        }
        speechRecognizer =
            SpeechRecognizerProvider.createSpeechRecognizer(this.context, recognizerObserver)
    }

}
