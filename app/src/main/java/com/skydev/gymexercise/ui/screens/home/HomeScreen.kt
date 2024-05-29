package com.skydev.gymexercise.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.skydev.gymexercise.ui.components.AsyncGifImage
import com.skydev.gymexercise.ui.nav.ActiveWorkout
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController,
) {
    LaunchedEffect(true) {
        //viewModel.loadExercisesInDb()
        viewModel.getAllExercisesFromDb()
    }

    //val exercises = viewModel.getAllExercises().collectAsLazyPagingItems()
    val exercises by viewModel.exerciseList.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        stickyHeader {
            Text(text = "Home Screen")
            Button(onClick = { navController.navigate(ActiveWorkout("23")) }) {
                Text(text = "Goto")
            }
        }
        items(
            count = exercises.size,
            key = { index -> exercises[index].id },
        ) { index ->
            val exercise = exercises[index]
            Row {
                Text(
                    text = exercise.name,
                    modifier = Modifier.fillMaxWidth(),
                )
                AsyncGifImage(url = exercise.gifUrl)
            }
        }
    }
}