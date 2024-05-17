package com.example.societyapp.ui.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MastersDao {
    @Insert
    suspend fun insertMasters(masters: Masters)

    @Query("SELECT id, flatNumber, name, mobileNoOne, mobileNoTwo FROM Masters Order By flatNumber")
    fun getMasters(): Flow<List<Masters>>
}