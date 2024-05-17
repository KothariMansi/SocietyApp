package com.example.societyapp.ui.models

import android.content.Intent
import android.net.Uri
import android.speech.RecognizerIntent
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.societyapp.SocietyApplication
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
    mastersDao: MastersDao,
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

    fun getSpeechInput(activity: ComponentActivity) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")
        startActivityForResult(activity, intent, 101, null)
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

    fun updateSelectedFlat(selected: String) {
        _uiState.update {
            it.copy(
                selected = selected,
            )
        }
    }

    fun makeCallIfPermissionGranted(activity: ComponentActivity, phoneNumber: String) {
        makeCall(activity, phoneNumber)
    }


    private fun makeCall(activity: ComponentActivity, phoneNumber: String) {
        val intent = Intent(
            Intent.ACTION_CALL,
            Uri.parse("tel:$phoneNumber")
        ) // Initiates the Intent

        activity.startActivity(intent)

    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        //private const val CALL_PERMISSION_REQUEST_CODE = 123

        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SocietyApplication)
                SocietyViewModel(application.database.dao())
            }
        }

    }

}