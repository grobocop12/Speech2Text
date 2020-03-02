package com.grobocop.speech2text.ui.fragments

import android.os.Bundle
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.grobocop.speech2text.R
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.ui.viewModel.TranscriptionViewModel
import com.grobocop.speech2text.utils.InjectorUtils
import com.grobocop.speech2text.utils.SpeechRecognizerObserver
import com.grobocop.speech2text.utils.SpeechRecognizerProvider

class EditFragment : Fragment() {
    private lateinit var editViewModel: TranscriptionViewModel
    private lateinit var transcriptionET: EditText
    private lateinit var fab: FloatingActionButton
    private lateinit var recognizer: SpeechRecognizer
    private lateinit var recognizerObserver: SpeechRecognizerObserver
    private var isListening = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val index = arguments?.getInt("index")
        val root = inflater.inflate(R.layout.fragment_edit, container, false)
        setViewModel()
        setUI(root)
        setRecognizerListener()
        setObservers(index)
        recognizer =
            SpeechRecognizerProvider.createSpeechRecognizer(this.context, recognizerObserver)
        return root
    }


    override fun onStop() {
        editViewModel.addItem()
        super.onStop()
    }

    private fun setViewModel() {
        val factory = InjectorUtils.provideTranscriptionViewModelFactory()
        editViewModel = ViewModelProvider(this, factory).get(TranscriptionViewModel::class.java)
    }

    private fun setUI(root: View) {
        transcriptionET = root.findViewById(R.id.transcription_text_et)
        fab = root.findViewById(R.id.edit_fab)
    }

    private fun setRecognizerListener() {
        recognizerObserver = object : SpeechRecognizerObserver {
            override fun onResult(result: String?) {
                editViewModel.appendText(result)
                fab.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_play_arrow,
                        this@EditFragment.context?.theme
                    )
                )
                isListening = false
            }
        }
    }

    private fun setObservers(index: Int?) {
        val observer = Observer<Transcription> {
            transcriptionET.setText(it.text)
        }
        if (index == null || index < 0) {
            editViewModel.getNew().observe(this.viewLifecycleOwner, observer)
        } else {
            editViewModel.getTranscription(index).observe(this.viewLifecycleOwner, observer)
        }
        transcriptionET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                editViewModel.setText(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        fab.setOnClickListener {
            if (isListening) {
                recognizer.stopListening()
                isListening = false
                fab.setImageDrawable(
                    resources.getDrawable(
                        R.drawable.ic_play_arrow,
                        it.context.theme
                    )
                )
            } else {
                val intent = SpeechRecognizerProvider.createSpeechRecognitionIntent()
                recognizer.startListening(intent)
                isListening = true
                fab.setImageDrawable(resources.getDrawable(R.drawable.ic_pause, it.context.theme))
            }
        }
    }

}
