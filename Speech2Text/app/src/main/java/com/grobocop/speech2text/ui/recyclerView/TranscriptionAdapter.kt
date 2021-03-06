package com.grobocop.speech2text.ui.recyclerView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.grobocop.speech2text.R
import com.grobocop.speech2text.data.Transcription
import com.grobocop.speech2text.utils.ItemRemover
import java.text.SimpleDateFormat


class TranscriptionAdapter(
    private val items: LiveData<List<Transcription>>?,
    private val context: Context?
) :
    RecyclerView.Adapter<TranscriptionViewHolder>() {

    var itemRemover: ItemRemover? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranscriptionViewHolder {
        return TranscriptionViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.transcription_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items?.value?.size ?: 0

    override fun onBindViewHolder(holder: TranscriptionViewHolder, position: Int) {
        val item = items?.value?.get(position)
        val id = item?.id
        val title = item?.title
        var text = item?.text
        val date = item?.date
        val args = Bundle()
        title?.let { holder.titleTV.text = it }
        date?.let {
            val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val dataString = format.format(it)
            holder.dateTV.text = dataString
        }
        holder.shareButton.setOnClickListener {
            if (text != null) {
                Toast.makeText(it.context, "share", Toast.LENGTH_SHORT).show()
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, text)
                sendIntent.type = "text/plain"
                startActivity(it.context, sendIntent, null)
            }
        }
        id?.let { args.putInt("id", id) }
        holder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_nav_home_to_nav_edit,
                args
            )
        )
        holder.itemView.setOnLongClickListener {
            id?.let { itemRemover?.remove(id) }
            true
        }
    }


}