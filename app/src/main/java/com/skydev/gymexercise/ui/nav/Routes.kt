package com.skydev.gymexercise.ui.nav

import kotlinx.serialization.Serializable

@Serializable
data object Home

/**
 * User will be able to edit the exercise session
 * or create new one from scratch
 * define reps, weights and time for each exercise
 */
@Serializable
data class EditExerciseSessionRoute(
    val exerciseId: String
)

/**
 * User will be able to edit the routine
 * using the routine builder
 * add or remove exercises / breaks
 */
@Serializable
data object EditRoutineRoute

@Serializable
data object EditScheduleRoute

@Serializable
data class ActiveWorkoutRoute(val workoutId: String)
