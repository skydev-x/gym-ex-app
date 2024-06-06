package com.skydev.gymexercise.ui.screens.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.skydev.gymexercise.ui.nav.EditScheduleRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        topBar = {
            MediumTopAppBar(title = {
                Text(text = "Schedule")
            }, actions = {
                IconButton(onClick = {
                    navController.navigate(EditScheduleRoute)
                }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Edit Schedule")
                }
            })
        }
    ) {
        Column(modifier = modifier.padding(it)) {
            Text(text = "Schedule Screen")
        }
    }

}