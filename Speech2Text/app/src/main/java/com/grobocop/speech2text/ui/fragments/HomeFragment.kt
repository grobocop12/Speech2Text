package com.grobocop.speech2text.ui.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.grobocop.speech2text.R
import com.grobocop.speech2text.ui.viewModel.TranscriptionsListViewModel
import com.grobocop.speech2text.ui.recyclerView.TranscriptionAdapter
import com.grobocop.speech2text.utils.InjectorUtils
import com.grobocop.speech2text.utils.ItemRemover

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: TranscriptionsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setViewModel()
        setupUI(root)
        return root
    }

    private fun setViewModel() {
        homeViewModel =
            ViewModelProvider(this).get(TranscriptionsListViewModel::class.java)
    }

    private fun setupUI(root: View) {
        val listRecyclerView = root.findViewById<RecyclerView>(R.id.transcriptions_list)
        val transcriptions = homeViewModel.getTranscriptions()
        val adapter = TranscriptionAdapter(transcriptions, this.context)
        adapter.itemRemover = object : ItemRemover {
            override fun remove(id: Int) {
                val dialog = AlertDialog.Builder(this@HomeFragment.context)
                    .setMessage("Are you sure you wan to delete this?")
                    .setNegativeButton("No", null)
                    .setPositiveButton(
                        "Yes"
                    ) { _, _ -> homeViewModel.deleteTranscription(id) }
                    .create()
                dialog.show()
            }
        }
        val floatingButton = root.findViewById<FloatingActionButton>(R.id.home_fab)
        listRecyclerView.layoutManager = LinearLayoutManager(this.context)
        listRecyclerView.adapter = adapter
        transcriptions?.observe(this.viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
        floatingButton?.setOnClickListener {
            navigateToEditFragment()
        }
    }

    private fun navigateToEditFragment() {
        findNavController().navigate(R.id.action_nav_home_to_nav_edit)
    }

}