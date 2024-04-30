package com.example.societyapp.ui.models

import androidx.lifecycle.ViewModel
import com.example.societyapp.ui.data.SocietyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SocietyViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(SocietyUiState())
    val uiState: StateFlow<SocietyUiState> = _uiState.asStateFlow()

    fun updateName(currentName: String) {
        _uiState.update {
            it.copy(
                name = currentName
            )
        }
    }

    fun updateFrom(from: String) {
        _uiState.update {
            it.copy(
                name = from
            )
        }
    }

    fun updateMobileNo(currentNum: String) {
        _uiState.update {
            it.copy(
                mobileNo = currentNum
            )
        }
    }

    fun visitorUpdate(visitor: Boolean ) {
        _uiState.update {
            it.copy(
                visitorChoose = !visitor,
                workerChoose = false

            )
        }
    }

    fun workerUpdate( worker: Boolean ) {
        _uiState.update {
            it.copy(
                visitorChoose = false,
                workerChoose = !worker

            )
        }
    }


}