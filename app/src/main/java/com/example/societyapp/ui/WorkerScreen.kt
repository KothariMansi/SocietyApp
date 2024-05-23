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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.societyapp.ui.models.SocietyViewModel

@Composable
fun WorkerScreen(
    societyViewModel: SocietyViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val societyUiState by societyViewModel.uiState.collectAsState()
    // Image capture
    val captureImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as? Bitmap
            bitmap?.let { societyViewModel.setImageBitmap(it) }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                canNavigateBack = true,
                onBackPress = {
                    navigateBack()
                    societyViewModel.clearWorkerDetail()
                },
                title = "Worker"
            )
        }
    ) {
        Column(modifier = modifier
            .padding(it)
            .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Adhar No.", modifier = modifier.padding(vertical = 16.dp))
                Spacer(modifier = modifier.padding(4.dp))
                TextField(
                    value = TextFieldValue(
                        text = societyUiState.adharNo,
                        selection = TextRange(societyUiState.adharNo.length)
                    ),
                    onValueChange = { societyViewModel.updateAdharNo(it.text)},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = societyUiState.adharNo.length > 14
                )
            }
            OutlinedButton(
                onClick = {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    captureImageLauncher.launch(cameraIntent)
                },
                shape = RectangleShape,
                modifier = modifier
                    .height(300.dp)
                    .width(250.dp)
                    .padding(20.dp)
            ) {
                societyUiState.imageBitmap?.let { imageBitmap ->
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier.fillMaxSize()
                    )
                }
            }

            Button(onClick = {
                navigateBack()
            }) {
                Text(text = "OK")
            }

        }
    }
}
