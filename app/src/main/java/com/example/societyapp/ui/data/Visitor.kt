package com.example.societyapp.ui.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Visitor(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    @ColumnInfo(name = "mobile_no")
    val mobileNo: String,
    val from: String,
    val date: String,
    val category: String,
    val mastersCode: Int?,
    @ColumnInfo("adhar_no")
    val adharNo: String?,
    val imageData: ByteArray?
)
