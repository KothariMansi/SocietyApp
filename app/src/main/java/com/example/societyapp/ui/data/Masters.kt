package com.example.societyapp.ui.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Masters(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val flatNumber: Int,
    val name: String,
    val mobileNoOne: Long,
    val mobileNoTwo: Long
)