package com.grobocop.speech2text.ui.fragments

import android.os.Bundle
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
import com.grobocop.speech2text.ui.viewModel.TranscriptionViewModel
import com.grobocop.speech2text.utils.InjectorUtils

class EditFragment : Fragment() {
    private lateinit var editViewModel: TranscriptionViewModel
    private lateinit var transcriptionET: EditText
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val index = arguments?.getInt("index")
        val root = inflater.inflate(R.layout.fragment_edit, container, false)
        setViewModel()
        setupUI(index, root)
        return root
    }

    override fun onStop() {
        val text = transcriptionET.text.toString()
        editViewModel.setText(text)
        editViewModel.addItem()
        super.onStop()
    }

    private fun setViewModel() {
        val factory = InjectorUtils.provideTranscriptionViewModelFactory()
        editViewModel = ViewModelProvider(this, factory).get(TranscriptionViewModel::class.java)
    }

    private fun setupUI(index: Int?, root: View) {
        transcriptionET = root.findViewById(R.id.transcription_text_et)
        if (index == null || index < 0) {
            editViewModel.getNew().observe(this.viewLifecycleOwner, Observer {
                transcriptionET.setText(it.text)
            })
        } else {
            editViewModel.getTranscription(index).observe(this.viewLifecycleOwner, Observer {
                transcriptionET.setText(it.text)
            })
        }

        fab = root.findViewById(R.id.edit_fab)
        fab.setOnClickListener {

        }
    }

}
