package com.skydev.gymexercise.ui.nav

import kotlinx.serialization.Serializable


interface AppRoutes

/**
 * Top Level Screens
 */
@Serializable
data object Home : AppRoutes

@Serializable
data object Schedule : AppRoutes

@Serializable
data object Explore : AppRoutes

@Serializable
data object Profile : AppRoutes


/**
 * User will be able to edit the exercise session
 * or create new one from scratch
 * define reps, weights and time for each exercise
 */
@Serializable
data class EditExerciseSessionRoute(
    val exerciseId: String
) : AppRoutes

/**
 * User will be able to edit the routine
 * using the routine builder
 * add or remove exercises / breaks
 */
@Serializable
data object EditRoutineRoute : AppRoutes

@Serializable
data object EditScheduleRoute : AppRoutes

@Serializable
data class ActiveWorkoutRoute(val workoutId: String) : AppRoutes
