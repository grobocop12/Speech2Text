package com.grobocop.speech2text.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "TRANSCRIPTIONS")
data class Transcription(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var date: Date? = null,
    var text: String? = null
)