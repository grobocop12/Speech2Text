package com.grobocop.speech2text.ui.recyclerView

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transcription_list_item.view.*

class TranscriptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val titleTV: TextView = view.title_tv
    val dateTV: TextView = view.date_tv
    val shareButton: ImageButton = view.share_button
}