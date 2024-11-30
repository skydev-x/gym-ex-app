package com.skydev.gymexercise.ui.screens.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydev.gymexercise.ui.components.RearMuscleSelector
import com.skydev.gymexercise.ui.components.ShapePath

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier, navController: NavController
) {

    RearMuscleSelector(modifier = modifier.fillMaxSize())


}
