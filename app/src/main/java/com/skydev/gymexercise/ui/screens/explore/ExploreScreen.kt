package com.skydev.gymexercise.ui.screens.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.skydev.gymexercise.ui.components.FrontMuscleSelector

@Composable
fun ExploreScreen(modifier: Modifier = Modifier, navController: NavController) {
    FrontMuscleSelector(modifier = modifier.fillMaxSize())

}