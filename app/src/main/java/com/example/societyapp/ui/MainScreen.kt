package com.example.societyapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.societyapp.R
import com.example.societyapp.ui.data.flatData
import com.example.societyapp.ui.models.SocietyViewModel
import com.example.societyapp.ui.theme.SocietyAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    societyViewModel: SocietyViewModel,
    add: () -> Unit,

) {
    val societyUiState by societyViewModel.uiState.collectAsState()


    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(8.dp)
    ) {
        Text(text = "Society App",
            style = MaterialTheme.typography.displayMedium,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 12.dp)
        )

        Button(onClick = { add() }) {
            Text(text = "Add")
            
        }

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
            )
            /* Todo Icon Mic */
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
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
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
            //Spacer(modifier = Modifier.padding(16.dp))

            Row(
                modifier = modifier.selectable(
                    selected = societyUiState.visitorChoose,
                    onClick = { societyViewModel.visitorUpdate(visitor = societyUiState.visitorChoose) }
                )
            ) {
                RadioButton(selected = societyUiState.visitorChoose,
                    onClick = { societyViewModel.visitorUpdate(visitor = societyUiState.visitorChoose) }
                )
                Text(text = "Visitor", modifier = modifier
                    .padding(top = 12.dp)
                    )
            }

            Spacer(modifier = Modifier.padding(start = 32.dp))

            Row(
                modifier = modifier.selectable(
                    selected = societyUiState.workerChoose,
                    onClick = { societyViewModel.workerUpdate(worker = societyUiState.workerChoose) }
                )
            ) {
                RadioButton(selected = societyUiState.workerChoose,
                    onClick = { societyViewModel.workerUpdate(worker = societyUiState.workerChoose) })
                Text(text = "Worker", modifier = modifier
                    .padding(top = 12.dp, end = 4.dp)
                )

            }
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = modifier.verticalScroll(scrollState),
        ) {
            Row {
                ExposedDropdownMenuBox(
                    expanded = societyUiState.expanded,
                    onExpandedChange = { societyViewModel.expandDropdown()},

                ) {

                    TextField(
                        value = societyUiState.selected,
                        onValueChange = {societyViewModel.updateSelectedFlat(it)},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = societyUiState.expanded) },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = societyUiState.expanded,
                        onDismissRequest = { societyViewModel.onDismissRequest()},
                        modifier = modifier
                            .padding(4.dp)
                            //.align(Alignment.BottomEnd)


                    ) {
                        flatData.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it) },
                                onClick = { societyViewModel.updateSelectedFlat(it)
                                    societyViewModel.updateIsFlatSelected()
                                }
                            )
                        }
                    }
                }

                if (societyUiState.isFlatSelected) {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "123454534")
                        Icon(imageVector = Icons.Filled.Call, contentDescription = "")
                    }
                }
            }

        }

    }

}

@Preview
@Composable
fun MainScreenPreview() {
    SocietyAppTheme {
        MainScreen(
            societyViewModel = viewModel(),
            add = {}
        )
    }
}

