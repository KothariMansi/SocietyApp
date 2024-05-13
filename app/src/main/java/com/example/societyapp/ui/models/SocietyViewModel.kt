package com.example.societyapp.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.societyapp.ui.data.MastersDao
import com.example.societyapp.ui.data.SocietyUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SocietyViewModel(
    private val mastersDao: MastersDao
): ViewModel() {
    private val _uiState = MutableStateFlow(SocietyUiState())
    val uiState: StateFlow<SocietyUiState> = _uiState.asStateFlow()

    val fetchedUiState: StateFlow<SocietyUiState>  = mastersDao.getMasters().map { SocietyUiState(mastersList = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SocietyUiState()
        )

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
                from = from
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

    fun getCurrentDate() {
        val sdf = SimpleDateFormat("dd:MM:yyyy", Locale.getDefault())
        _uiState.update {
            it.copy(
                date = sdf.format(Date())
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

    fun expandDropdown() {
        _uiState.update {
            it.copy(
                expanded = true
            )
        }
    }
    fun onDismissRequest() {
        _uiState.update {
            it.copy(
                expanded = false
            )
        }
    }

    fun updateSelectedFlat(selected: String, mobileNo: String) {
        _uiState.update {
            it.copy(
                selected = selected,
                mobileNo = mobileNo
            )
        }
    }

    fun updateIsFlatSelected() {
        _uiState.update{
            it.copy(
                isFlatSelected = true
            )
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}