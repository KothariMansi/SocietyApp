package com.example.societyapp.ui.data

import androidx.room.ColumnInfo

data class FlatData(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "mobileNoOne") val mobileNoOne: String,
    @ColumnInfo(name = "mobileNoTwo") val mobileNoTwo: String

)