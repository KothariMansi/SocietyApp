package com.example.societyapp.ui.data

data class Summary(
    val name: String,
    val category: String,
    val from: String,
    val flatNo: Int,
    val flatOwnerName: String,
    val time: String,
    val mobileNo: String,
    val adharNo: String?,
    val photo: ByteArray?
)
