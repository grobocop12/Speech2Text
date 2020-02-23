package com.grobocop.speech2text.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.grobocop.speech2text.R
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.ui.TranscriptionViewModel
import com.grobocop.speech2text.ui.TranscriptionsListViewModel
import com.grobocop.speech2text.ui.recyclerView.TranscriptionAdapter
import com.grobocop.speech2text.ui.send.SendFragment
import com.grobocop.speech2text.utils.InjectorUtils
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: TranscriptionsListViewModel
    var i = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = InjectorUtils.provideTranscriptionsListViewModelFactory()
        homeViewModel =
            ViewModelProvider(this, factory).get(TranscriptionsListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setupUI(root)

        return root
    }

    private fun setupUI(root: View) {
        val listRecyclerView = root.findViewById<RecyclerView>(R.id.transcriptions_list)
        val transcriptions = homeViewModel.getTranscriptions()
        listRecyclerView.layoutManager = LinearLayoutManager(this.context)
        val adapter = TranscriptionAdapter(transcriptions, this.context)
        listRecyclerView.adapter = adapter
        transcriptions.observe(this.viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
        val floatingButton = root.findViewById<FloatingActionButton>(R.id.home_fab)
        floatingButton?.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_nav_send)
        }
    }

}