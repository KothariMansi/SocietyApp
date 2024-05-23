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

    @Query("SELECT * FROM Masters Order By flatNumber")
    fun getMasters(): Flow<List<Masters>>

    @Query("""
        SELECT
        v.name AS name,
        v.category as category,
        v.`from` as `from`,
        m.flatNumber as flatNo,
        m.name as flatOwnerName,
        v.date as time,
        v.mobile_no as mobileNo,
        v.adhar_no as adharNo,
        v.imageData as photo
        FROM Visitor v 
        JOIN Masters m ON v.mastersCode = m.id
    """)
    fun getSummary(): Flow<List<Summary>>
}