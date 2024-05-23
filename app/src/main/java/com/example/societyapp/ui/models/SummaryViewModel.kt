package com.example.societyapp.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.societyapp.SocietyApplication
import com.example.societyapp.ui.data.SocietyDao
import com.example.societyapp.ui.data.Summary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SummaryViewModel(private val societyDao: SocietyDao): ViewModel() {
    private val _uiState: MutableStateFlow<SummaryUiState> = MutableStateFlow(SummaryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getSummaryData()
    }

    private fun getSummaryData() {
        viewModelScope.launch {
            societyDao.getSummary().map {
                SummaryUiState(
                    summaryList = it
                )
            }. collect {state ->
                _uiState.update {
                    it.copy(
                        summaryList = state.summaryList
                    )
                }
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SocietyApplication)
                SummaryViewModel(application.database.dao())
            }
        }
    }
}

data class SummaryUiState(
    var summaryList: List<Summary> = listOf()
)