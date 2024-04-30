package com.example.societyapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.societyapp.R
import com.example.societyapp.ui.models.SocietyViewModel
import com.example.societyapp.ui.theme.SocietyAppTheme

@Composable
fun MainScreen(
    societyViewModel: SocietyViewModel = viewModel(),
    modifier: Modifier = Modifier,

) {
    val societyUiState by societyViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(8.dp)
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
                    .weight(3f)
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
                Modifier.weight(3f)
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
                modifier = modifier.weight(3f)
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
            TextField(value = "",
                onValueChange = {},
                modifier = modifier.weight(3f)

            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = modifier.fillMaxWidth()
        ){
            //Spacer(modifier = Modifier.padding(16.dp))

            Row {
                RadioButton(selected = societyUiState.visitorChoose,
                    onClick = { societyViewModel.visitorUpdate(visitor = societyUiState.visitorChoose) }
                )
                Text(text = "Visitor", modifier = modifier
                    .padding(top = 12.dp)
                    )
            }
            Spacer(modifier = Modifier.padding(start = 32.dp))

            Row {
                RadioButton(selected = societyUiState.workerChoose,
                    onClick = { societyViewModel.workerUpdate(worker = societyUiState.workerChoose) })
                Text(text = "Worker", modifier = modifier
                    .padding(top = 12.dp, end = 4.dp)
                )

            }
        }



    }

}

@Preview
@Composable
fun MainScreenPreview() {
    SocietyAppTheme {
        MainScreen()
    }
}

