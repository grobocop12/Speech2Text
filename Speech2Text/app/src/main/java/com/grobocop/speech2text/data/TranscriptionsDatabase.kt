package com.grobocop.speech2text.data

import android.content.Context
import androidx.room.*


@Database(entities = [Transcription::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TranscriptionsDatabase : RoomDatabase() {
    abstract fun transcriptionsDao(): TranscriptionsDao

    companion object {
        @Volatile
        private var instance: TranscriptionsDatabase? = null

        fun getDatabase(context: Context): TranscriptionsDatabase? {
            if (instance == null) {
                synchronized(TranscriptionsDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            TranscriptionsDatabase::class.java,
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