package com.grobocop.speech2text.ui.recyclerView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.grobocop.speech2text.R
import com.grobocop.speech2text.data.Transcription

class TranscriptionAdapter(
    private val items: LiveData<List<Transcription>>,
    private val context: Context?
) :
    RecyclerView.Adapter<TranscriptionViewHolder>() {

    var onItemClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranscriptionViewHolder {
        return TranscriptionViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.transcription_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.value?.size ?: 0

    override fun onBindViewHolder(holder: TranscriptionViewHolder, position: Int) {
        val item = items.value?.get(position)
        val text = items.value?.get(position)?.text
        holder.transcriptionText.text = if (text?.length!! < 15) {
            text.replace('\n', ' ')
        } else {
            text.take(15).replace('\n', ' ') + "..."
        }
        val index = items.value?.indexOf(item) ?: -1
        val args = Bundle()
        args.putInt("index", index)
        holder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_nav_home_to_nav_edit,
                args
            )
        )
    }


}