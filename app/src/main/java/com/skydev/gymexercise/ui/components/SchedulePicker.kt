package com.skydev.gymexercise.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SchedulePicker(
    modifier: Modifier = Modifier,
    scheduleDays: List<String>,
    onScheduleDayAdded: (String) -> Unit = {},
    onScheduleDayRemoved: (String) -> Unit = {},
    onScheduleUpdate: (List<String>) -> Unit = {}
) {
    val daysList by remember {
        mutableStateOf(
            listOf(
                "Mon",
                "Tue",
                "Wed",
                "Thu",
                "Fri",
                "Sat",
                "Sun"
            )
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        daysList.forEach { dayKey ->
            DayButton(
                text = dayKey,
                isSelected = scheduleDays.contains(dayKey)
            ) {
                if (scheduleDays.contains(it)) {
                    onScheduleDayRemoved(it)
                } else {
                    onScheduleDayAdded(it)
                }
                onScheduleUpdate(scheduleDays)
            }
        }
    }
}


@Preview
@Composable
fun SchedulePickerPreview() {
    SchedulePicker(
        scheduleDays = listOf("Mon", "Tue", "Wed"),
        onScheduleDayAdded = {},
        onScheduleDayRemoved = {},
        onScheduleUpdate = {}
    )
}