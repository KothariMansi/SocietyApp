package com.example.societyapp.ui.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SocietyDao {
    @Insert
    suspend fun insertMasters(masters: Masters)

    @Insert
    suspend fun insertVisitors(visitor: Visitor)

    @Query("SELECT id, flatNumber, name, mobileNoOne, mobileNoTwo FROM Masters Order By flatNumber")
    fun getMasters(): Flow<List<Masters>>
}