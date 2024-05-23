package com.example.societyapp.ui.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Masters::class, Visitor::class],
    version = 5
)
abstract class SocietyDatabase: RoomDatabase() {
    abstract fun dao(): SocietyDao

    companion object {
        @Volatile
        private var Instance: SocietyDatabase? = null

        fun getDatabase(context: Context): SocietyDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    SocietyDatabase::class.java,
                    "Society.db"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }

        }
    }

}