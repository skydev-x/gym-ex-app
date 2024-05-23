package com.skydev.gymexercise.ui.screens.workout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.skydev.gymexercise.ui.components.CircularProgressBar
import com.skydev.gymexercise.ui.components.GifImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveWorkoutScreen(
    viewModel: WorkoutViewModel = koinViewModel(),
    navController: NavController,
    workoutId: String,
) {

    val progress by viewModel.currentProgress.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row {
            Text(text = "Day progress & current progress")
        }
        Row {
            GifImage()
        }
        Row {
            Text(text = "header and meta data $workoutId")
        }
        Row {
            CircularProgressBar(
                progress = progress,
                total = 6 * 60 * 1000
            ){
                GifImage()
            }
        }
        Row {
            Button(onClick = {
                viewModel.startWorkout(6)
            }) {
                Text(text = "Start")
            }
            Button(onClick = {
                viewModel.pauseWorkout()
            }) {
                Text(text = "Pause")
            }
            Button(onClick = {
                viewModel.resumeWorkout()
            }) {
                Text(text = "Resume")
            }
            Button(onClick = {
                viewModel.stopWorkout()
            }) {
                Text(text = "Stop")
            }
        }
    }

}