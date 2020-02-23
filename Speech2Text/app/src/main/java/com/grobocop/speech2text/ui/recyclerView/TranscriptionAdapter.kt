package com.grobocop.speech2text.ui.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.grobocop.speech2text.R
import com.grobocop.speech2text.data.Transcription

class TranscriptionAdapter(
    private val items: LiveData<List<Transcription>>,
    private val context: Context?
) :
    RecyclerView.Adapter<TranscriptionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranscriptionViewHolder =
        TranscriptionViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.transcription_list_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.value?.size ?: 0

    override fun onBindViewHolder(holder: TranscriptionViewHolder, position: Int) {
        val text = items.value?.get(position)?.text
        holder.transcriptionText?.text = if (text?.length!! < 15) {
            text
        } else {
            text.take(15) + "..."
        }
    }

}