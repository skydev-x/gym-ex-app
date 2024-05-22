package com.skydev.gymexercise.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.skydev.gymexercise.ui.nav.EditSchedule

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Home Screen")
        Button(onClick = { navController.navigate(EditSchedule) }) {
            Text(text = "Goto")
        }
    }
}