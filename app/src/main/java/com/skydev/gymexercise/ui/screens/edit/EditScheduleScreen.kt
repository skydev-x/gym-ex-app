package com.skydev.gymexercise.ui.screens.edit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.skydev.gymexercise.ui.components.SchedulePicker

@Composable
fun EditScheduleScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Text(text = "Edit Schedule Screen")
    SchedulePicker(scheduleDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"))
}