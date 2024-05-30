package com.skydev.gymexercise.ui.screens.editExerciseSession

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel


@Composable
fun EditExerciseSessionScreen(
    modifier: Modifier = Modifier,
    viewModel: EditExerciseSessionViewModel = koinViewModel(),
    navController: NavController,
    exerciseId: String,
) {

    LaunchedEffect(true) {
        viewModel.getExerciseForSession(exerciseId)
    }

    val exercise by viewModel.exercise.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = exercise?.name ?: "Loading...")
    }

}