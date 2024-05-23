package com.example.societyapp.ui.data

import android.graphics.Bitmap

data class SocietyUiState(
    val name: String = "",
    val mobileNo: String = "",
    val from: String = "",
    val date: String = "",
    val visitorChoose: Boolean = false,
    val workerChoose: Boolean = false,
    val expanded: Boolean = false,
    val selected: String = "Select Flat",
    val selectedId: Int? = null,
    val mastersList: List<Masters> = listOf(),
    val mobileNoButtonOne: String = "",
    val mobileNoButtonTwo: String = "",
    val adharNo: String = "",
    val imageBitmap: Bitmap? = null,
    val enabled: Boolean = false


)