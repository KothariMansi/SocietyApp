package com.example.societyapp.ui.data

data class SocietyUiState(
    val name: String = "",
    val mobileNo: String = "",
    val from: String = "",
    val date: String = "",
    val visitorChoose: Boolean = false,
    val workerChoose: Boolean = false,
    val expanded: Boolean = false,
    val selected: String = "Select Flat",
    val isFlatSelected: Boolean = false,

)