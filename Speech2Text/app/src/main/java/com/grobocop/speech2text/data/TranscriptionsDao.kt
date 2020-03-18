package com.grobocop.speech2text.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TranscriptionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setTranscription(transcription: Transcription)

    @Query("SELECT * FROM transcriptions ORDER BY id ASC")
    fun getTranscriptions(): LiveData<List<Transcription>>

    @Query("SELECT * FROM transcriptions WHERE ID = :id")
    fun getTranscription(id: Int): LiveData<Transcription>

    @Query("DELETE FROM transcriptions WHERE ID = :id")
    fun deleteTranscription(id: Int)
}