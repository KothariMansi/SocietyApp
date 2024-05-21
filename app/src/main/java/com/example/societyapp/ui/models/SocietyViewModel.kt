package com.example.societyapp.ui.models

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.societyapp.SocietyApplication
import com.example.societyapp.ui.data.SocietyDao
import com.example.societyapp.ui.data.SocietyUiState
import com.example.societyapp.ui.data.Visitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class SocietyViewModel(
    private val societyDao: SocietyDao,
    application: SocietyApplication
): ViewModel() {
    private val _uiState = MutableStateFlow(SocietyUiState())
    val uiState: StateFlow<SocietyUiState> = _uiState.asStateFlow()

    fun save(visitor: Visitor) {
        viewModelScope.launch {
            try {
                societyDao.insertVisitors(visitor = visitor)
            }
            catch (e:Exception){
                Log.d("Exception", e.toString())
            }
        }
    }

    fun clear() {
        _uiState.update {
            it.copy(
                name = "",
                mobileNo = "",
                from = "",
                date = "",
                selected = "Select Flat",
                visitorChoose = false,
                workerChoose = false
            )
        }
    }

    fun getMastersData() {
        viewModelScope.launch(Dispatchers.IO) {
            societyDao.getMasters().map {
                SocietyUiState(mastersList = it)
            }
            .collect { state ->
                _uiState.update {
                    it.copy(
                        mastersList = state.mastersList
                    )
                }
            }
        }
    }

    fun updateName(currentName: String) {
        _uiState.update {
            it.copy(
                name = currentName
            )
        }
    }

    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(application).apply {
        setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                Log.e("SpeechRecognizer", "Error: $error")
            }
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (!matches.isNullOrEmpty()) {
                    updateName("")
                    updateName(matches[0])
                }
            }
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    fun getSpeechInput(activity: ComponentActivity) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        ActivityCompat.startActivityForResult(activity, intent, 101, null)
        speechRecognizer.startListening(intent)
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizer.destroy()
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

    fun updateSelectedFlat(selected: String, id: Int?) {
        _uiState.update {
            it.copy(
                selected = selected,
                selectedId = id
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
        //private const val TIMEOUT_MILLIS = 5_000L
        //private const val CALL_PERMISSION_REQUEST_CODE = 123

        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SocietyApplication)
                SocietyViewModel(application.database.dao(), application)
            }
        }
    }

}