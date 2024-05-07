package com.example.societyapp.ui.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Masters::class],
    version = 1
)
abstract class SocietyDatabase: RoomDatabase() {
    abstract val dao: MastersDao
}