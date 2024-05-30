package com.skydev.gymexercise.ui.nav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.skydev.gymexercise.ui.screens.edit.EditScheduleScreen
import com.skydev.gymexercise.ui.screens.editExerciseSession.EditExerciseSessionScreen
import com.skydev.gymexercise.ui.screens.home.HomeScreen
import com.skydev.gymexercise.ui.screens.workout.ActiveWorkoutScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(navController = navController)
        }
        composable<EditScheduleRoute> {
            EditScheduleScreen(navController = navController)
        }
        composable<ActiveWorkoutRoute> {
            val workout: ActiveWorkoutRoute = it.toRoute()
            ActiveWorkoutScreen(navController = navController, workoutId = workout.workoutId)
        }
        composable<EditExerciseSessionRoute> {
            val exerciseSession: EditExerciseSessionRoute = it.toRoute()
            EditExerciseSessionScreen(
                navController = navController,
                exerciseId = exerciseSession.exerciseId
            )
        }
    }
}
