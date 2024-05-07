package com.example.societyapp.ui.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MastersDao {
    @Insert
    suspend fun insertMasters(masters: Masters)
}