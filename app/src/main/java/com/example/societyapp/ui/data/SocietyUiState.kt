package com.example.societyapp.ui.data

import androidx.lifecycle.MutableLiveData

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
    val isFlatSelected: Boolean = false,
    val mastersList: List<Masters> = listOf(),
    val mobileNoButtonOne: String = "",
    val mobileNoButtonTwo: String = "",
    val recognizedText: MutableLiveData<String> = MutableLiveData<String>()


)