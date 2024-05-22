package com.skydev.gymexercise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DayButton(text: String, isSelected: Boolean, onSelectedChange: (String) -> Unit) {
    var selection by remember {
        mutableStateOf(isSelected)
    }
    Card(
        modifier = Modifier
            .clip(CircleShape)
            .size(40.dp)
            .background(if (isSelected) MaterialTheme.colorScheme.inverseSurface else MaterialTheme.colorScheme.secondary)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .clickable {
                selection = !selection
                onSelectedChange(text)
            }
            .padding(2.dp),
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
                .border(
                    width = 2.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.inverseSurface else MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
            )
        }

    }
}

@Preview
@Composable
fun DayButtonPreview() {
    DayButton(text = "Mon", isSelected = false, onSelectedChange = {})
}
