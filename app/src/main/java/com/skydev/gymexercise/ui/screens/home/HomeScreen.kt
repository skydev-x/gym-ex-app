package com.skydev.gymexercise.ui.screens.home

import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydev.gymexercise.ui.components.MuscleSelector
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.P)
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

    var query by remember {
        mutableStateOf("")
    }

    val filteredList by remember {
        derivedStateOf {
            if (query.isEmpty()) exercises
            else exercises.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current

//    LazyColumn(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(8.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//        stickyHeader {
//            Text(text = "Home Screen")
//            Button(onClick = {
//                //navController.navigate(ActiveWorkout("23"))
//                keyboardController?.hide()
//            }) {
//                Text(text = "Goto")
//            }
//            TextField(
//                value = query,
//                onValueChange = {
//                    query = it
//                },
//                keyboardActions = KeyboardActions {
//                    keyboardController?.hide()
//                },
//
//                )
//        }
//
//        items(
//            count = filteredList.size,
//            key = { index -> filteredList[index].id },
//        ) { index ->
//            val exercise = filteredList[index]
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
//                    .clickable {
//                        navController.navigate(EditExerciseSessionRoute(exercise.id))
//                    },
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Top
//            ) {
//                Spacer(modifier = Modifier.padding(8.dp))
////                    AsyncGifImage(url = exercise.gifUrl)
//                Text(
//                    text = exercise.name,
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.headlineSmall
//                )
//                Row {
//                    Chip(
//                        text = exercise.target,
//                        background = MaterialTheme.colorScheme.tertiary
//                    )
//                    Chip(
//                        text = exercise.bodyPart,
//                        background = MaterialTheme.colorScheme.secondary
//                    )
//                    exercise.secondaryMuscles.forEach {
//                        Chip(text = it, background = MaterialTheme.colorScheme.primary)
//                    }
//                }
//                Spacer(modifier = Modifier.padding(8.dp))
//            }
//        }
//    }
    MuscleSelector(modifier = Modifier.fillMaxSize())


}
