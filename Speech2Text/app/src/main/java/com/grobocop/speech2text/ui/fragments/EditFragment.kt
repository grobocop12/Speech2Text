package com.grobocop.speech2text.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.grobocop.speech2text.R
import com.grobocop.speech2text.ui.viewModel.TranscriptionViewModel
import com.grobocop.speech2text.utils.InjectorUtils

class EditFragment : Fragment() {
    private lateinit var viewModel: TranscriptionViewModel
    private lateinit var transcriptionET: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val index = arguments?.getInt("index")
        val root = inflater.inflate(R.layout.edit_fragment, container, false)
        setViewModel()
        setupUI(index, root)
        return root
    }

    override fun onStop() {
        val text = transcriptionET.text.toString()
        viewModel.setText(text)
        viewModel.addItem()
        super.onStop()
    }

    private fun setViewModel() {
        val factory = InjectorUtils.provideTranscriptionViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(TranscriptionViewModel::class.java)

    }

    private fun setupUI(index: Int?, root : View) {
        transcriptionET = root.findViewById(R.id.transcription_text_et)
        if (index == null || index == -1) {
            viewModel.getNew().observe(this.viewLifecycleOwner, Observer {
                transcriptionET.setText(it.text)
            })
        } else {
            viewModel.getTranscription(index).observe(this.viewLifecycleOwner, Observer {
                transcriptionET.setText(it.text)
            })
        }
    }


}
