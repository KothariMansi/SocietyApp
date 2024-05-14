package com.example.societyapp.ui.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.societyapp.ui.data.Masters
import com.example.societyapp.ui.data.MastersDao
import com.example.societyapp.ui.data.MastersUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MastersViewModel(
    private val mastersDao: MastersDao
): ViewModel() {
    private val _uiState = MutableStateFlow(MastersUiState())
    val uiState: StateFlow<MastersUiState> = _uiState.asStateFlow()

    fun updateFlatNo(flatNumber: String) {
        _uiState.update {
            it.copy(
                flatNo = flatNumber
            )
        }
    }

    fun clear() {
        _uiState.update {
            it.copy(
                flatNo = "",
                name = "",
                mobileNoOne = "",
                mobileNoTwo = ""
            )
        }
    }

    fun updateName(currentName: String) {
        _uiState.update {
            it.copy(
                name = currentName
            )
        }
    }
    fun updateMobileNoOne(currentNum: String) {
        _uiState.update {
            it.copy(
                mobileNoOne = currentNum
            )
        }
    }

    fun updateMobileNoTwo(currentNum: String) {
        _uiState.update {
            it.copy(
                mobileNoTwo = currentNum
            )
        }
    }

    fun save(
        flatNumber: String,
        name: String,
        mobileNoOne: String,
        mobileNoTwo: String
    ) {
        viewModelScope.launch {
            val masters = Masters(
                flatNumber = flatNumber.toInt(),
                name = name,
                mobileNoOne = mobileNoOne.toLong(),
                mobileNoTwo = mobileNoTwo.toLong()
            )
            try {
                mastersDao.insertMasters(masters = masters)
            }
            catch (e:Exception){
                Log.d("Exception", e.toString())
            }
        }
    }



}