package com.example.societyapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.societyapp.R
import com.example.societyapp.ui.models.MastersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MastersScreen(
    mastersViewModel: MastersViewModel,
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit
) {
    val mastersUiState by mastersViewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopBar(
                canNavigateBack = true,
                onBackPress = onBackPress,
                title = stringResource(id = R.string.masters)
            )
        }
    ) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(it)
            .padding(8.dp),
    ) {
        Row(
            modifier = modifier

        ) {
            Text(
                text = stringResource(R.string.flat_no),
                modifier = modifier
                    .padding(top = 16.dp)
                    .weight(1f)
            )
            Spacer(modifier = modifier.padding(16.dp))
            TextField(
                value = mastersUiState.flatNo, onValueChange = { mastersViewModel.updateFlatNo(it) },
                modifier = modifier
                    .wrapContentHeight()
                    .weight(3f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number)
                )
        }

        Row {
            Text(text = stringResource(id = R.string.name),
                modifier = modifier
                    .padding(top = 16.dp)
                    .weight(1f)
            )
            Spacer(modifier = modifier.padding(16.dp))
            TextField(
                value = mastersUiState.name, onValueChange = { mastersViewModel.updateName(it) },
                modifier = modifier
                    .wrapContentHeight()
                    .weight(3f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
        }

        Row {
            Text(text = stringResource(id = R.string.mobile_no),
                modifier = modifier
                    .padding(top = 16.dp)
                    .weight(1f)
            )
            Spacer(modifier = modifier.padding(16.dp))
            TextField(
                value = mastersUiState.mobileNoOne, onValueChange = { mastersViewModel.updateMobileNoOne(it)},
                modifier = modifier
                    .wrapContentHeight()
                    .weight(3f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Number)
            )
        }

        Row {
            Text(text = stringResource(id = R.string.mobile_no),
                modifier = modifier
                    .padding(top = 16.dp)
                    .weight(1f)
            )
            Spacer(modifier = modifier.padding(16.dp))
            TextField(
                value = mastersUiState.mobileNoTwo, onValueChange = { mastersViewModel.updateMobileNoTwo(it)},
                modifier = modifier
                    .wrapContentHeight()
                    .weight(3f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(
                    onDone = {
                        mastersViewModel.save(
                            flatNumber = mastersUiState.flatNo,
                            name = mastersUiState.name,
                            mobileNoOne = mastersUiState.mobileNoOne,
                            mobileNoTwo = mastersUiState.mobileNoTwo
                        )
                        mastersViewModel.clear()
                    }
                )
            )
        }

        Button(
            onClick = {
                mastersViewModel.save(
                    flatNumber = mastersUiState.flatNo,
                    name = mastersUiState.name,
                    mobileNoOne = mastersUiState.mobileNoOne,
                    mobileNoTwo = mastersUiState.mobileNoTwo
                )
                mastersViewModel.clear()
            },
            modifier = modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(text = stringResource(id = R.string.save))
        }

    }
}
}
