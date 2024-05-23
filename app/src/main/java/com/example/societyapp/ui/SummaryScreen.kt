package com.example.societyapp.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.societyapp.R
import com.example.societyapp.ui.data.Summary
import com.example.societyapp.ui.models.SummaryViewModel
import com.example.societyapp.ui.theme.SocietyAppTheme

@Composable
fun SummaryScreen(
    summaryViewModel: SummaryViewModel,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val summaryUiState by summaryViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopBar(canNavigateBack = true, onBackPress = { onBackPress() }, title = stringResource(R.string.summary))
        }
    ) {
        SummaryList(summaryList = summaryUiState.summaryList, modifier = modifier
            .padding(it)
            .fillMaxSize()
        )
    }
}

@Composable
fun SummaryList(
    summaryList: List<Summary>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(summaryList) {
            SummaryItem(summary = it, modifier = Modifier)
        }
    }
}

@Composable
fun SummaryItem(
    summary: Summary,
    modifier: Modifier = Modifier
) {
    val bitmap: Bitmap? = summary.photo?.let { BitmapFactory.decodeByteArray(summary.photo, 0, it.size) }

    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier.fillMaxWidth().heightIn(150.dp, 200.dp)
        ) {
            if (bitmap != null) {
                Image(bitmap = bitmap.asImageBitmap(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = modifier.width(120.dp)
                )
            }
            Column(
                modifier = modifier.padding(8.dp).heightIn(130.dp, 200.dp)
            ) {
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(text = "Name:", modifier = modifier.weight(1f))
                    Spacer(modifier = modifier.padding())
                    Text(text = summary.name, modifier = modifier.weight(2f))
                }
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(text = "Category:", modifier = modifier.weight(1f))
                    Spacer(modifier = modifier.padding())
                    Text(text = summary.category, modifier = modifier.weight(2f))
                }
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(text = "From:", modifier = modifier.weight(1f))
                    Spacer(modifier = modifier.padding())
                    Text(text = summary.from, modifier = modifier.weight(2f))
                }
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(text = "Visited:", modifier = modifier.weight(1f))
                    Spacer(modifier = modifier.padding())
                    Text(
                        text = summary.flatNo.toString() + ", " + summary.flatOwnerName,
                        modifier = modifier.weight(2f)
                    )
                }
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(text = "Date:", modifier = modifier.weight(1f))
                    Spacer(modifier = modifier.padding())
                    Text(text = summary.time, modifier = modifier.weight(2f))
                }
                Row(modifier = modifier.fillMaxWidth()) {
                    Text(text = "Mobile No.", modifier = modifier.weight(1f))
                    Spacer(modifier = modifier.padding())
                    Text(text = summary.mobileNo, modifier = modifier.weight(2f))
                }
                if (summary.category == "Worker") {
                    Row(modifier = modifier.fillMaxWidth()) {
                        Text(text = "Adhar No.", modifier = modifier.weight(1f))
                        Spacer(modifier = modifier.padding())
                        summary.adharNo?.let { Text(text = it, modifier = modifier.weight(2f)) }
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun SummaryItemPreview() {
    SocietyAppTheme {
        SummaryItem(
            summary = Summary(
                name = "Mansi",
                category = "Visitor",
                from = "Balotra",
                flatNo = 506,
                flatOwnerName = "Arvind",
                time = "02/06/2024",
                mobileNo = "6377225898",
                adharNo = null,
                photo = null
            )
        )
    }

}

