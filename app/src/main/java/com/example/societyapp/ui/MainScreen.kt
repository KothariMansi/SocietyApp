package com.example.societyapp.ui

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.societyapp.R
import com.example.societyapp.ui.models.SocietyViewModel
import com.example.societyapp.ui.theme.SocietyAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    canNavigateBack: Boolean,
    onBackPress:() -> Unit,
    title: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            if(canNavigateBack) {
                IconButton(onClick = onBackPress) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    societyViewModel: SocietyViewModel,
    add: () -> Unit,
    activity: ComponentActivity,
    navigateToWorkerScreen: () -> Unit,
    navigateToSummaryScreen: () -> Unit
) {
    val societyUiState by societyViewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    remember {
        lifecycleOwner.lifecycleScope.launch {
            societyViewModel.getMastersData()
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                canNavigateBack = false,
                onBackPress = {},
                title = stringResource(id = R.string.societyApp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { add() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }


    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .padding(8.dp)
                .padding(it)
        ) {

            Row(
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.name),
                    modifier = modifier
                        .padding(top = 16.dp)
                        .weight(1f)
                )
                Spacer(modifier = modifier.padding(16.dp))
                TextField(value = societyUiState.name,
                    onValueChange = { societyViewModel.updateName(it) },
                    modifier = modifier
                        .wrapContentHeight()
                        .weight(3f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    trailingIcon = {
                        IconButton(onClick = { societyViewModel.getSpeechInput(activity = activity) }) {
                            Icon(
                                painter = painterResource(R.drawable.mic_filled),
                                contentDescription = ""
                            )
                        }
                    }
                )
            }

            Row {
                Text(
                    text = stringResource(R.string.mobile_no),
                    modifier = modifier
                        .padding(top = 16.dp)
                        .weight(1f)
                )
                Spacer(modifier = modifier.padding(16.dp))
                TextField(value = societyUiState.mobileNo,
                    onValueChange = {societyViewModel.updateMobileNo(it)},
                    Modifier.weight(3f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number),
                    isError = societyUiState.mobileNo.length > 10
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.from),
                    modifier = modifier
                        .padding(top = 16.dp)
                        .weight(1f)
                )
                Spacer(modifier = modifier.padding(16.dp))
                TextField(value = societyUiState.from,
                    onValueChange = {societyViewModel.updateFrom(it)},
                    modifier = modifier.weight(3f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {societyViewModel.getCurrentDate()})
                )
            }
            Row {
                Text(
                    text = stringResource(R.string.date),
                    modifier = modifier
                        .padding(top = 16.dp)
                        .weight(1f)
                )
                Spacer(modifier = modifier.padding(16.dp))
                TextField(value = societyUiState.date,
                    onValueChange = {societyViewModel.getCurrentDate()},
                    modifier = modifier.weight(3f)

                )
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = modifier.fillMaxWidth()
            ){

                Row(
                    modifier = modifier.selectable(
                        selected = societyUiState.visitorChoose,
                        onClick = { societyViewModel.visitorUpdate(visitor = societyUiState.visitorChoose) }
                    )
                ) {
                    RadioButton(selected = societyUiState.visitorChoose,
                        onClick = { societyViewModel.visitorUpdate(visitor = societyUiState.visitorChoose) }
                    )
                    Text(text = "Visitor", modifier = modifier.padding(top = 12.dp))
                }
                Spacer(modifier = Modifier.padding(start = 32.dp))
                Row(
                    modifier = modifier.selectable(
                        selected = societyUiState.workerChoose,
                        onClick = {
                            societyViewModel.workerUpdate(worker = societyUiState.workerChoose)
                            navigateToWorkerScreen()
                        }
                    )
                ) {
                    RadioButton(selected = societyUiState.workerChoose,
                        onClick = {
                            societyViewModel.workerUpdate(worker = societyUiState.workerChoose)
                            navigateToWorkerScreen()
                        }
                    )
                    Text(text = "Worker", modifier = modifier
                        .padding(top = 12.dp, end = 4.dp)
                    )
                }
            }
            val scrollState = rememberScrollState()
            Column(
                modifier = modifier
                    .verticalScroll(scrollState)
                    .fillMaxWidth(),
            ) {
                ExposedDropdownMenuBox(
                    expanded = societyUiState.expanded,
                    onExpandedChange = { societyViewModel.expandDropdown() },
                    modifier = modifier.fillMaxWidth()
                    ) {
                    TextField(
                        value = societyUiState.selected,
                        onValueChange = {
                            societyViewModel.updateSelectedFlat(it, societyUiState.selectedId)
                        },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = societyUiState.expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = societyUiState.expanded,
                        onDismissRequest = { societyViewModel.onDismissRequest() },
                        modifier = modifier
                            .exposedDropdownSize(matchTextFieldWidth = false)
                            .padding(4.dp)
                            .heightIn(2.dp, 150.dp)
                            .fillMaxWidth()
                    ) {
                        societyUiState.mastersList.forEach {
                            DropdownMenuItem(
                                modifier = modifier.fillMaxWidth(),
                                leadingIcon = { Text(text = it.flatNumber.toString()) },
                                text = { Text(text = it.name, modifier = modifier) },
                                onClick = {
                                          societyViewModel.updateSelectedFlat(selected = it.name, id= it.id )
                                },
                                trailingIcon = {
                                    Row {
                                        IconButton(
                                            onClick = {
                                                societyViewModel.makeCallIfPermissionGranted(
                                                    activity = activity,
                                                    phoneNumber = it.mobileNoOne.toString()
                                                )
                                            }, modifier = modifier
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Call,
                                                contentDescription = "",
                                            )
                                        }
                                        IconButton(onClick = {
                                            societyViewModel.makeCallIfPermissionGranted(
                                                activity = activity,
                                                phoneNumber = it.mobileNoTwo.toString()
                                            )
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.Call,
                                                contentDescription = ""
                                            )
                                        }
                                    }

                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = modifier.padding(80.dp))
            Button(
                onClick = {
                    societyViewModel.save()
                },  modifier = modifier.fillMaxWidth(),
                enabled = societyViewModel.checkConditions()
            ) {
                Text(text = stringResource(id = R.string.save))
            }

            Button(onClick = navigateToSummaryScreen) {
                Text(text = stringResource(R.string.summary))
            }
        }
    }
}



@Preview
@Composable
fun MainScreenPreview() {
    SocietyAppTheme {
        MainScreen(
            societyViewModel = viewModel(factory = SocietyViewModel.factory),
            add = {},
            activity = ComponentActivity(),
            navigateToWorkerScreen = {},
            navigateToSummaryScreen = {}
        )
    }
}

