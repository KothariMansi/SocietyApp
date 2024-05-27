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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.societyapp.R
import com.example.societyapp.ui.data.workerCategoryList
import com.example.societyapp.ui.models.SocietyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerScreen(
    societyViewModel: SocietyViewModel,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val workerCategory by societyViewModel.fetchWorkerCategory().collectAsState(
        initial = workerCategoryList
    )
    workerCategoryList = workerCategory.toMutableList()

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
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.adhar_no), modifier = modifier.padding(vertical = 16.dp))
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

            Row(
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.category), modifier = modifier.padding(vertical = 16.dp))
                Spacer(modifier = modifier.padding(8.dp))
                Column(
                    modifier = modifier.verticalScroll(rememberScrollState())
                ) {
                    ExposedDropdownMenuBox(expanded = societyUiState.expanded,
                        onExpandedChange = { societyViewModel.expandDropdown() }
                    ) {
                        TextField(
                            value = societyUiState.workerCategory, onValueChange = { societyViewModel.updateWorkerCategory(it) },
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = societyUiState.expanded) },
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = societyUiState.expanded,
                            onDismissRequest = { societyViewModel.onDismissRequest() },
                            modifier = modifier.exposedDropdownSize()
                        ) {
                            workerCategory.forEach { worker ->
                                DropdownMenuItem(
                                    text = { Text(text = worker) },
                                    onClick = { societyViewModel.updateWorkerCategory(worker) }
                                )
                            }
                        }
                    }
                }
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
                if(societyUiState.adharNo.length == 14) {
                    navigateBack()
                } else {
                    societyViewModel.onWrongAdhar()
                }
            }) {
                Text(text = stringResource(R.string.ok))
            }

            if (societyUiState.isError) {
                AlertDialog(
                    onDismissRequest = {societyViewModel.onWrongAdhar() },
                ) {
                    Text(text = "Adhar no should have 12 characters.")
                }
            }
            Row {
                Button(onClick = { societyViewModel.updateIsAddNew() }) {
                    Text(text = "Click for New")
                }
                if (societyUiState.isAddNew) {
                    Column {
                        TextField(value = societyUiState.newCategory, onValueChange = {societyViewModel.updateNewCategory(it)})
                        Button(onClick = {
                            societyViewModel.saveNewWorkerCategory(workerCategoryList)
                            societyViewModel.updateIsAddNew()
                        }) {
                            Text(text = "Save")
                        }
                    }
                }
            }
        }
    }
}
