package com.skydev.gymexercise.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.skydev.gymexercise.ui.components.BottomBar
import com.skydev.gymexercise.ui.nav.AppNavGraph
import com.skydev.gymexercise.ui.nav.EditScheduleRoute
import com.skydev.gymexercise.ui.theme.GymExerciseTheme

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun GymExApp() {
    GymExerciseTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomBar()
            },
            floatingActionButton = {
                IconButton(
                    onClick = {
                        navController.navigate(EditScheduleRoute)
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .border(
                            width = 2.dp,
                            color = Color.Transparent
                        )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavGraph(navController = navController)
            }
        }
    }
}