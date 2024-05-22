package com.skydev.gymexercise.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.skydev.gymexercise.ui.screens.edit.EditScheduleScreen
import com.skydev.gymexercise.ui.screens.home.HomeScreen

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
        composable<EditSchedule> {
            EditScheduleScreen(navController = navController)
        }
    }
}
