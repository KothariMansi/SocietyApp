package com.example.societyapp.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Composable
fun WorkerScreen(
    workerViewModel: WorkerViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val workerUiState by workerViewModel.uiState.collectAsState()
    // Image capture
    val captureImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as? Bitmap
            bitmap?.let { workerViewModel.setImageBitmap(it) }
        }
    }

    Scaffold(
        topBar = {
            TopBar(canNavigateBack = true, onBackPress = { navigateBack() }, title = "Worker")
        }
    ) {
        Column(modifier = modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(text = "Adhar No.")

                TextField(
                    value = workerUiState.adharNo,
                    onValueChange = { workerViewModel.updateAdharNo(it) }
                )
            }
            Button(
                onClick = {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    captureImageLauncher.launch(cameraIntent)
                },
                shape = RectangleShape,
                modifier = modifier.height(300.dp).width(250.dp).padding(16.dp)
            ) {
                workerUiState.imageBitmap?.let { imageBitmap ->
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.size(400.dp)
                    )
                }
            }

        }
    }
}

class WorkerViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(WorkerUiState())
    val uiState: StateFlow<WorkerUiState> = _uiState.asStateFlow()

    fun updateAdharNo(adharNo: String) {
        _uiState.update { it.copy(adharNo = adharNo ) }
    }

    fun setImageBitmap(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    imageBitmap = bitmap
                )
            }
        }
    }
}

data class WorkerUiState(
    val adharNo: String = "",
    val imageBitmap: Bitmap? = null
)