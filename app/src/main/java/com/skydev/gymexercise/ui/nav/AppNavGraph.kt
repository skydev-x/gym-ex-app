package com.skydev.gymexercise.ui.nav

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.skydev.gymexercise.ui.components.NavBottomBar
import com.skydev.gymexercise.ui.screens.edit.EditScheduleScreen
import com.skydev.gymexercise.ui.screens.editExerciseSession.EditExerciseSessionScreen
import com.skydev.gymexercise.ui.screens.explore.ExploreScreen
import com.skydev.gymexercise.ui.screens.home.HomeScreen
import com.skydev.gymexercise.ui.screens.profile.ProfileScreen
import com.skydev.gymexercise.ui.screens.schedule.ScheduleScreen
import com.skydev.gymexercise.ui.screens.workout.ActiveWorkoutScreen

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val isNavBottomBarVisible by remember(currentRoute) {
        derivedStateOf {
            Log.d("AppNavGraph", "currentRoute: $currentRoute")
            currentRoute == Home.javaClass.name ||
                    currentRoute == Schedule.javaClass.name ||
                    currentRoute == Explore.javaClass.name ||
                    currentRoute == Profile.javaClass.name
        }
    }

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            Home.javaClass.name -> selectedIndex = 0
            Schedule.javaClass.name -> selectedIndex = 1
            Explore.javaClass.name -> selectedIndex = 2
            Profile.javaClass.name -> selectedIndex = 3
        }
    }



    Scaffold(
        bottomBar = {

            AnimatedVisibility(
                modifier = Modifier.background(Color.Transparent),
                visible = isNavBottomBarVisible,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                NavBottomBar(selectedIndex = selectedIndex) {
                    selectedIndex = it
                    when (it) {
                        0 -> navController.navigate(
                            route = Home,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(Home, inclusive = true)
                                .setLaunchSingleTop(true)
                                .build()
                        )

                        1 -> navController.navigate(
                            route = Schedule,
                            navOptions = NavOptions.Builder()
                                .setPopUpTo(Schedule, inclusive = true)
                                .build()
                        )

                        2 -> navController.navigate(
                            route = Explore,
                            navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
                        )

                        3 -> navController.navigate(
                            route = Profile,
                            navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
                        )
                    }
                }
            }

        }
    ) { paddingValues ->
        NavHost(
            modifier = modifier.padding(paddingValues),
            navController = navController,
            startDestination = Home
        ) {
            composable<Home> {
                HomeScreen(navController = navController)
            }
            composable<Schedule> {
                ScheduleScreen(navController = navController)
            }
            composable<Explore> {
                ExploreScreen(navController = navController)
            }
            composable<Profile> {
                ProfileScreen(navController = navController)
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

}
