package com.grobocop.speech2text.ui.recyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transcription_list_item.view.*

class TranscriptionViewHolder(view: View) :RecyclerView.ViewHolder(view) {
    val transcriptionText = view.transcription_text
}