package com.grobocop.speech2text.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grobocop.speech2text.R
import com.grobocop.speech2text.ui.viewModel.TranscriptionViewModel
import com.grobocop.speech2text.utils.InjectorUtils

class SendFragment : Fragment() {

    private lateinit var sendViewModel: TranscriptionViewModel
    private lateinit var textView: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val index = arguments?.getInt("index")
        val root = inflater.inflate(R.layout.fragment_send, container, false)
        textView = root.findViewById(R.id.text_send)
        setViewModel(index)
        return root
    }

    override fun onStop() {
        val text = textView.text.toString()
//        sendViewModel.setText(text)
        //      sendViewModel.addItem()
        super.onStop()
    }

    private fun setViewModel(index: Int?) {

        sendViewModel = ViewModelProvider(this).get(TranscriptionViewModel::class.java)
        if (index != null) {
            sendViewModel.getTranscription(index)?.observe(this.viewLifecycleOwner, Observer {
                textView.setText(it.text)
            })
        }

    }
}