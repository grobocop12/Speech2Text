package com.grobocop.speech2text.data

import android.content.Context
import androidx.room.*


@Database(entities = [Transcription::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TranscriptionDatabase : RoomDatabase() {
    abstract fun transcriptionsDao(): TranscriptionsDao

    companion object {
        @Volatile
        private var instance: TranscriptionDatabase? = null

        fun getDatabase(context: Context): TranscriptionDatabase? {
            if (instance == null) {
                synchronized(TranscriptionDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            TranscriptionDatabase::class.java,
                            "TranscriptionDatabase"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance
        }
    }
}