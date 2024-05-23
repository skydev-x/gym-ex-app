package com.skydev.gymexercise.ui.screens.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel : ViewModel() {

    var currentProgress = MutableStateFlow(0L)
        private set

    private var workoutJob: Job? = null

    fun startWorkout(timeInMin: Int) {
        currentProgress.value = timeInMin * 60 * 1000L
        // Start workout
        workoutJob = viewModelScope.launch(Dispatchers.Default) {
            while (currentProgress.value > 0) {
                currentProgress.value -= 1000
                delay(1000)
            }
        }
        workoutJob?.start()
    }

    fun stopWorkout() {
        // Stop workout
        viewModelScope.launch(Dispatchers.IO) {
            currentProgress.value = 0
            workoutJob?.cancel()
            workoutJob = null
        }
    }

    fun pauseWorkout() {
        // Pause workout
        workoutJob?.cancel()
    }

    fun resumeWorkout() {
        // Resume workout
        viewModelScope.launch(Dispatchers.Default) {
            workoutJob = launch(Dispatchers.Default) {
                while (currentProgress.value > 0) {
                    currentProgress.value -= 1000
                    delay(1000)
                }
            }
            workoutJob?.start()
        }
    }

}