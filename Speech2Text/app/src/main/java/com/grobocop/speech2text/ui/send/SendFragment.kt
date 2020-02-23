package com.grobocop.speech2text.ui.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.grobocop.speech2text.R
import com.grobocop.speech2text.ui.TranscriptionViewModel
import com.grobocop.speech2text.ui.TranscriptionsListViewModel
import com.grobocop.speech2text.utils.InjectorUtils

class SendFragment : Fragment() {

    private lateinit var sendViewModel: TranscriptionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = InjectorUtils.provideTranscriptionViewModelFactory()
        sendViewModel = ViewModelProvider(this, factory).get(TranscriptionViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_send, container, false)
        val textView: TextView = root.findViewById(R.id.text_send)
        sendViewModel.getLastOrNew().observe(this.viewLifecycleOwner, Observer {
            textView.text = it.text
        })
        return root
    }

    override fun onStop() {
        sendViewModel.addItem()
        super.onStop()
    }
}