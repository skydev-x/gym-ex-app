package com.skydev.gymexercise.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.skydev.gymexercise.ui.nav.AppNavGraph
import com.skydev.gymexercise.ui.theme.GymExerciseTheme

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun GymExApp() {
    GymExerciseTheme {
        val navController = rememberNavController()

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavGraph(navController = navController)
        }

    }
}