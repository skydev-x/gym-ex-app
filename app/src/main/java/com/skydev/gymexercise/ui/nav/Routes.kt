package com.skydev.gymexercise.ui.nav

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object EditSchedule

@Serializable
data class ActiveWorkout(val workoutId: String)
