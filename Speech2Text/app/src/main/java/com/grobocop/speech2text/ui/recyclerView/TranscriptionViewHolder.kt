package com.grobocop.speech2text.ui.recyclerView

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.transcription_list_item.view.*

class TranscriptionViewHolder(view: View) :RecyclerView.ViewHolder(view){
    val transcriptionText : TextView = view.transcription_text
}