package com.grobocop.speech2text.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Transcription::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TranscriptionsDatabase : RoomDatabase() {
    abstract fun transcriptionsDao(): TranscriptionsDao

    companion object {
        @Volatile
        private var instance: TranscriptionsDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""ALTER TABLE  Transcriptions ADD COLUMN title TEXT""")
            }
        }

        fun getDatabase(context: Context): TranscriptionsDatabase? {
            if (instance == null) {
                synchronized(TranscriptionsDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            TranscriptionsDatabase::class.java,
                            "TranscriptionDatabase"
                        )
                            .addMigrations(MIGRATION_1_2)
                            .build()
                    }
                }
            }
            return instance
        }
    }
}