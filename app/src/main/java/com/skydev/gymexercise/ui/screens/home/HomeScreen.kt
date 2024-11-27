package com.skydev.gymexercise.ui.screens.home

import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydev.gymexercise.R
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
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {

        val height = 300.dp


        Box(
            modifier = Modifier
                .size(height)
                .aspectRatio(0.5f)
                .clip(ShapePath(pathHuman))
                .background(Color.LightGray)
                .clickable { },
        )

        var chest by remember {
            mutableStateOf(false)
        }
        val chestColor by remember {
            derivedStateOf {
                if (chest) Color.Cyan else Color.Gray
            }
        }

        var frontDelt by remember {
            mutableStateOf(false)
        }
        val frontDeltColor by remember {
            derivedStateOf {
                if (frontDelt) Color.Red else Color.Gray
            }
        }


        var sideDelt by remember {
            mutableStateOf(false)
        }
        val sideDeltColor by remember {
            derivedStateOf {
                if (sideDelt) Color.Green else Color.Gray
            }
        }

        var frontBicep by remember {
            mutableStateOf(false)
        }
        val frontBicepColor by remember {
            derivedStateOf {
                if (frontBicep) Color.Magenta else Color.Gray
            }
        }


        var frontTricep by remember {
            mutableStateOf(false)
        }
        val frontTricepColor by remember {
            derivedStateOf {
                if (frontTricep) Color.Blue else Color.Gray
            }
        }


        var frontForearm by remember {
            mutableStateOf(false)
        }
        val frontForearmColor by remember {
            derivedStateOf {
                if (frontForearm) Color.Yellow else Color.Gray
            }
        }


        var abs by remember {
            mutableStateOf(false)
        }
        val absColor by remember {
            derivedStateOf {
                if (abs) Color.Red else Color.Gray
            }
        }


        var oblique by remember {
            mutableStateOf(false)
        }
        val obliqueColor by remember {
            derivedStateOf {
                if (oblique) Color.Green else Color.Gray
            }
        }

        ChestPec(
            height = height, color = chestColor
        ) {
            chest = !chest
        }


        SideDelt(
            height = height, color = sideDeltColor
        ) {
            sideDelt = !sideDelt
        }

        FrontDelt(
            height = height, color = frontDeltColor
        ) {
            frontDelt = !frontDelt
        }

        FrontTricep(
            height = height, color = frontTricepColor
        ) {
            frontTricep = !frontTricep
        }

        FrontForeArm(
            height = height, color = frontForearmColor
        ) {
            frontForearm = !frontForearm
        }

        FrontBicep(height = height, color = frontBicepColor) {
            frontBicep = !frontBicep
        }

        Abs(
            height = height, color = absColor
        ) {
            abs = !abs
        }

        Oblique(
            height = height,
            color = obliqueColor
        ) {
            oblique = !oblique
        }


        Box(
            Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(Color.Red)
        )

    }


}

@Composable
fun BoxScope.Oblique(
    height: Dp, color: Color, onClick: () -> Unit
) {

    Box(modifier = Modifier.align(Alignment.Center)) {

        Box(
            modifier = Modifier
                .offset(x = -height.times(0.18f), y = -height.times(0.38f))
                .graphicsLayer {
                    rotationZ = 94f
                    rotationX = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(6f), height.div(30f))
                .clip(ShapePath(oblique1))
                .border(
                    shape = ShapePath(oblique1), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = -height.times(0.085f), y = -height.times(0.36f))
                .graphicsLayer {
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(10f), height.div(18f))
                .clip(ShapePath(oblique2))
                .border(
                    shape = ShapePath(oblique2), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier
                .offset(x = -height.times(0.085f), y = -height.times(0.33f))
                .graphicsLayer {
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(14f), height.div(20f))
                .clip(ShapePath(oblique3))
                .border(
                    shape = ShapePath(oblique3), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = -height.times(0.085f), y = -height.times(0.31f))
                .graphicsLayer {
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(20f), height.div(20f))
                .clip(ShapePath(oblique4))
                .border(
                    shape = ShapePath(oblique4), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = -height.times(0.075f), y = -height.times(0.26f))
                .graphicsLayer {
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(22f), height.div(20f))
                .clip(ShapePath(oblique4))
                .border(
                    shape = ShapePath(oblique4), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = -height.times(0.075f), y = -height.times(0.28f))
                .graphicsLayer {
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(20f), height.div(20f))
                .clip(ShapePath(oblique5))
                .border(
                    shape = ShapePath(oblique5), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier
                .offset(x = -height.times(0.07f), y = -height.times(0.22f))
                .graphicsLayer {
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(24f), height.div(20f))
                .clip(ShapePath(oblique6))
                .border(
                    shape = ShapePath(oblique6), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = -height.times(0.07f), y = -height.times(0.20f))
                .graphicsLayer {
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(20f), height.div(12f))
                .clip(ShapePath(oblique7))
                .border(
                    shape = ShapePath(oblique7), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = -height.times(0.072f), y = -height.times(0.16f))
                .graphicsLayer {
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(14f), height.div(5f))
                .clip(ShapePath(oblique8))
                .border(
                    shape = ShapePath(oblique8), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

    }

    //reflection
    Box(
        modifier = Modifier
            .align(Alignment.Center)
            .offset(y = -height.times(0.07f)),
        contentAlignment = Alignment.CenterEnd
    ) {

        Box(
            modifier = Modifier
                .offset(x = height.times(0.18f), y = -height.times(0.38f))
                .graphicsLayer {
                    rotationZ = 94f
                    rotationX = 180f
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(6f), height.div(30f))
                .clip(ShapePath(oblique1))
                .border(
                    shape = ShapePath(oblique1), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = height.times(0.085f), y = -height.times(0.36f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(10f), height.div(18f))
                .clip(ShapePath(oblique2))
                .border(
                    shape = ShapePath(oblique2), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier
                .offset(x = height.times(0.085f), y = -height.times(0.33f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(14f), height.div(20f))
                .clip(ShapePath(oblique3))
                .border(
                    shape = ShapePath(oblique3), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = height.times(0.085f), y = -height.times(0.30f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(20f), height.div(20f))
                .clip(ShapePath(oblique4))
                .border(
                    shape = ShapePath(oblique4), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = height.times(0.075f), y = -height.times(0.25f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(22f), height.div(20f))
                .clip(ShapePath(oblique4))
                .border(
                    shape = ShapePath(oblique4), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = height.times(0.075f), y = -height.times(0.27f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(20f), height.div(20f))
                .clip(ShapePath(oblique5))
                .border(
                    shape = ShapePath(oblique5), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier
                .offset(x = height.times(0.07f), y = -height.times(0.21f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(24f), height.div(20f))
                .clip(ShapePath(oblique6))
                .border(
                    shape = ShapePath(oblique6), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = height.times(0.07f), y = -height.times(0.18f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(20f), height.div(12f))
                .clip(ShapePath(oblique7))
                .border(
                    shape = ShapePath(oblique7), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier
                .offset(x = height.times(0.072f), y = -height.times(0.09f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(14f), height.div(5f))
                .clip(ShapePath(oblique8))
                .border(
                    shape = ShapePath(oblique8), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }
    }
}

@Composable
fun Abs(
    height: Dp, color: Color, onClick: () -> Unit
) {

    Box {

        Box(
            modifier = Modifier
                .offset(x = -height.times(0.055f), y = -height.times(0.38f))
        ) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs1))
                .border(
                    shape = ShapePath(abs1), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier
                .offset(x = -height.times(0.06f), y = -height.times(0.32f))
        ) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs2))
                .border(
                    shape = ShapePath(abs2), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier
                .offset(x = -height.times(0.055f), y = -height.times(0.23f))
        ) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs3))
                .border(
                    shape = ShapePath(abs3), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(
            modifier = Modifier
                .offset(x = height.times(0.055f), y = -height.times(0.38f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs1))
                .border(
                    shape = ShapePath(abs1), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier
                .offset(x = height.times(0.06f), y = -height.times(0.32f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs2))
                .border(
                    shape = ShapePath(abs2), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier
                .offset(x = height.times(0.055f), y = -height.times(0.23f))
                .graphicsLayer {
                    rotationY = 180f
                }
        ) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs3))
                .border(
                    shape = ShapePath(abs3), color = Color.LightGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }
    }

    Box(
        modifier = Modifier
            .offset(x = height.times(0f), y = -height.times(0.1f))
    ) {

        Box(modifier = Modifier
            .size(height.div(4.6f), height.div(5f))
            .clip(ShapePath(absBottom))
            .border(
                shape = ShapePath(absBottom), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun FrontForeArm(
    height: Dp, color: Color, onClick: () -> Unit
) {


    Box(
        modifier = Modifier
            .offset(x = -height.times(0.29f), y = -height.times(0.24f))
            .graphicsLayer {
                rotationZ = 5f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(wristFlexor))
            .border(
                shape = ShapePath(wristFlexor), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(
        modifier = Modifier
            .offset(x = height.times(0.29f), y = -height.times(0.24f))
            .graphicsLayer {
                rotationZ = 5f
                rotationY = 180f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(wristFlexor))
            .border(
                shape = ShapePath(wristFlexor), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier
            .offset(x = height.times(0.35f), y = -height.times(0.24f))
            .graphicsLayer {
                rotationZ = 5f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(forearm))
            .border(
                shape = ShapePath(forearm), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier
            .offset(x = -height.times(0.35f), y = -height.times(0.24f))
            .graphicsLayer {
                rotationZ = 5f
                rotationY = 180f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(forearm))
            .border(
                shape = ShapePath(forearm), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun FrontBicep(
    height: Dp, color: Color, onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .offset(x = -height.times(0.26f), y = -height.times(0.44f))
            .graphicsLayer {
                rotationZ = 5f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(4f))
            .clip(ShapePath(bicepMinor))
            .border(
                shape = ShapePath(bicepMinor), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier
            .offset(x = height.times(0.26f), y = -height.times(0.44f))
            .graphicsLayer {
                rotationZ = 5f
                rotationY = 180f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(4f))
            .clip(ShapePath(bicepMinor))
            .border(
                shape = ShapePath(bicepMinor), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier
            .offset(x = -height.times(0.24f), y = -height.times(0.41f))
            .graphicsLayer {
                rotationZ = -6f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3.5f))
            .clip(ShapePath(bicepMajor))
            .border(
                shape = ShapePath(bicepMajor), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier
            .offset(x = height.times(0.24f), y = -height.times(0.41f))
            .graphicsLayer {
                rotationZ = -6f
                rotationY = 180f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3.5f))
            .clip(ShapePath(bicepMajor))
            .border(
                shape = ShapePath(bicepMajor), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


}

@Composable
fun FrontTricep(
    height: Dp, color: Color, onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .offset(x = height.times(0.28f), y = -height.times(0.42f))
            .graphicsLayer {
                rotationZ = 22f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(6f), height.div(6f))
            .clip(ShapePath(frontTricepBig))
            .border(
                shape = ShapePath(frontTricepBig), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier
            .offset(x = -height.times(0.28f), y = -height.times(0.42f))
            .graphicsLayer {
                rotationZ = 22f
                rotationY = 180f
            }
    ) {

        Box(modifier = Modifier
            .size(height.div(6f), height.div(6f))
            .clip(ShapePath(frontTricepBig))
            .border(
                shape = ShapePath(frontTricepBig), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(
        modifier = Modifier
            .offset(x = height.times(0.23f), y = -height.times(0.38f))
            .graphicsLayer {
                rotationZ = 22f
            }

    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(8f))
            .clip(ShapePath(frontTricepSmall))
            .border(
                shape = ShapePath(frontTricepSmall), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(
        modifier = Modifier
            .offset(x = -height.times(0.23f), y = -height.times(0.38f))
            .graphicsLayer {
                rotationZ = 22f
                rotationY = 180f
            }

    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(8f))
            .clip(ShapePath(frontTricepSmall))
            .border(
                shape = ShapePath(frontTricepSmall), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


}


@Composable
fun BoxScope.ChestPec(
    height: Dp, color: Color, onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .offset(x = -height.times(0.11f), y = height.times(0.62f))
    ) {
        Box(modifier = Modifier
            .size(height.div(4.5f), height.div(4.5f))
            .clip(ShapePath(pathChest))
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .align(Alignment.TopCenter)
        .offset(x = height.times(0.11f), y = height.times(0.62f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(4.5f), height.div(4.5f))
            .clip(ShapePath(pathChest))
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun FrontDelt(
    height: Dp, color: Color, onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .offset(x = -height.times(0.19f), y = -height.times(0.56f))
    ) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(5f))
            .clip(ShapePath(frontAndSideDelt))
            .border(
                shape = ShapePath(frontAndSideDelt), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.19f), y = -height.times(0.56f))
        .graphicsLayer {
            rotationY = 180f
        }
    ) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(5f))
            .clip(ShapePath(frontAndSideDelt))
            .border(
                shape = ShapePath(frontAndSideDelt), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

@Composable
fun SideDelt(
    height: Dp, color: Color, onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .offset(x = -height.times(0.21f), y = -height.times(0.58f))
    ) {

        Box(modifier = Modifier
            .size(height.div(5.5f), height.div(5f))
            .clip(ShapePath(frontAndSideDelt))
            .border(
                shape = ShapePath(frontAndSideDelt), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.21f), y = -height.times(0.58f))
        .graphicsLayer {
            rotationY = 180f
        }
    ) {

        Box(modifier = Modifier
            .size(height.div(5.5f), height.div(5f))
            .clip(ShapePath(frontAndSideDelt))
            .border(
                shape = ShapePath(frontAndSideDelt), color = Color.LightGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

class ShapePath(private val pathData: String) : Shape {

    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {
        return Outline.Generic(path = drawPath(size))
    }

    private fun drawPath(size: Size): Path {
        return Path().apply {
            reset()
            pathData.toPath(size, this)
            close()
        }
    }
}

val pathHuman =
    "M258.729 15.1991C250.935 22.9159 246.63 31.8429 245.088 36.6526C243.865 40.4674 243.476 47.1006 243.503 53.9075C243.529 60.6912 243.965 67.57 244.36 71.8289C244.383 72.0743 244.223 72.2996 243.984 72.3599C243.745 72.4201 243.498 72.2972 243.402 72.0703C243.128 71.4253 242.585 70.4773 241.863 69.7488C241.135 69.014 240.314 68.5851 239.451 68.7419C238.467 68.9209 237.521 69.3192 236.74 70.0359C235.962 70.7504 235.318 71.8082 234.979 73.357C234.774 74.2913 234.68 75.4052 234.736 76.7287C234.86 79.6378 235.294 83.1423 235.961 86.3675C236.631 89.6067 237.524 92.5084 238.543 94.2471C239.089 95.1773 239.637 96.3398 240.192 97.5311C240.239 97.6314 240.286 97.7319 240.332 97.8325C240.846 98.9371 241.368 100.059 241.912 101.077C242.508 102.191 243.111 103.142 243.729 103.787C244.351 104.437 244.907 104.694 245.425 104.629C246.844 104.452 247.609 104.018 248.06 103.579C248.483 103.169 248.672 102.721 248.852 102.297C248.869 102.257 248.885 102.217 248.902 102.178C248.931 102.11 248.969 102.033 249.018 101.963C249.049 101.919 249.168 101.752 249.395 101.713C249.699 101.659 249.882 101.865 249.94 101.95C250.001 102.039 250.031 102.134 250.048 102.191C250.113 102.421 250.151 102.824 250.188 103.271C250.2 103.406 250.211 103.551 250.223 103.704C250.297 104.624 250.395 105.852 250.606 107.171C250.701 107.768 250.797 108.537 250.906 109.404C250.941 109.687 250.978 109.98 251.016 110.281C251.173 111.513 251.36 112.893 251.609 114.287C252.11 117.099 252.847 119.877 254.024 121.592C254.08 121.674 254.111 121.771 254.112 121.87C254.168 127.965 254.238 138.401 254.219 145.376C254.21 148.831 254.179 151.469 254.11 152.292C254.035 153.197 253.509 154.116 252.745 155.015C251.972 155.924 250.906 156.871 249.651 157.834C247.139 159.762 243.802 161.806 240.363 163.807C233.452 167.829 221.544 173.216 207.4 178.098C200.818 180.369 196.17 181.078 191.663 181.766C191.301 181.821 190.94 181.876 190.579 181.932C185.739 182.679 180.874 183.546 173.675 186.463C172.872 186.789 172.093 187.102 171.337 187.406L170.812 187.618C164.523 190.15 159.829 192.079 155.454 195.191C151.087 198.296 147.013 202.599 142.025 209.907C131.369 225.519 130.362 246.628 130.362 257.625C130.362 266.438 129.945 274.816 129.736 277.909C129.73 277.989 129.706 278.067 129.664 278.136C127.834 281.129 124.964 286.033 122.303 291.011C119.635 296.001 117.199 301.023 116.215 304.27C115.361 307.091 113.995 310.963 112.487 315.241C111.783 317.237 111.048 319.322 110.319 321.429C108.021 328.072 105.782 334.949 104.727 340.1C103.069 348.195 101.314 351.28 98.0725 356.981C97.122 358.652 96.0437 360.548 94.8025 362.861C94.3492 363.706 93.8892 364.555 93.4227 365.416C88.2335 374.992 82.2419 386.048 75.7186 409.509C72.162 422.3 69.6652 434.845 67.5404 445.876C67.3555 446.836 67.1734 447.784 66.9936 448.721C65.11 458.531 63.4818 467.012 61.5897 473.147C59.5231 479.848 55.9881 489.889 52.8309 498.439C51.252 502.715 54.0162 504.871 52.8541 507.803C52.2732 509.269 51.7721 510.495 51.38 511.403C51.1841 511.856 51.0139 512.234 50.8735 512.525C50.7398 512.801 50.6156 513.037 50.5119 513.175C50.3254 513.424 50.0519 513.656 49.7464 513.871C49.4356 514.09 49.0647 514.308 48.66 514.522C47.8505 514.95 46.875 515.375 45.9063 515.761C43.9681 516.534 42.0163 517.163 41.3806 517.354C40.4121 517.644 37.6079 518.79 36.3053 519.336L36.3042 519.337C31.84 521.197 27.4978 523.803 25.7775 525.032C25.0262 525.568 24.345 526.252 23.6242 526.975C23.5458 527.054 23.4669 527.133 23.3873 527.213C22.5921 528.008 21.7406 528.831 20.7441 529.429C20.0757 529.83 19.1696 530.311 18.2131 530.818C17.8097 531.033 17.3972 531.252 16.9899 531.471C15.5943 532.224 14.2568 532.986 13.4208 533.643C11.6884 535.004 9.47548 537.099 8.38689 538.55C7.89897 539.201 7.72359 539.685 7.54806 540.17L7.51929 540.249C7.32212 540.791 7.10394 541.34 6.46544 541.979C5.92065 542.523 5.15932 543.118 4.37853 543.727L4.18548 543.878C3.32369 544.552 2.44079 545.253 1.71544 545.979C1.09508 546.599 0.622155 547.936 0.780514 549.318C0.934113 550.659 1.67556 551.984 3.41739 552.659C5.24454 553.366 7.458 553.434 9.59935 553.04C11.7398 552.645 13.7586 551.796 15.1869 550.725C16.6424 549.633 17.5199 548.636 18.2418 547.76C18.3304 547.652 18.4176 547.546 18.504 547.44C19.1106 546.696 19.6783 546 20.4523 545.464C21.3061 544.873 22.6866 544.169 24.0659 543.614C25.4283 543.066 26.8819 542.625 27.8619 542.625C28.0318 542.625 28.1901 542.711 28.2821 542.854C28.3742 542.997 28.3875 543.177 28.3173 543.331C27.1092 545.998 24.35 552.29 22.9575 556.169C21.4175 560.459 18.9933 570.233 17.6739 575.553C17.5034 576.241 17.3513 576.854 17.222 577.371C16.0973 581.87 14.1002 590.233 13.1031 595.469C12.0997 600.736 10.8591 607.693 10.7363 610.15C10.7199 610.477 10.6817 610.885 10.638 611.351C10.504 612.779 10.3191 614.75 10.5608 616.591C10.7194 617.8 11.0568 618.878 11.6681 619.661C12.2622 620.422 13.1459 620.944 14.5077 621C17.2278 621.114 19.0249 619.316 20.2079 617.223C21.2986 615.293 21.7965 613.229 21.9666 612.523C21.9805 612.466 21.9922 612.417 22.0018 612.379C22.0783 612.073 22.2632 611.047 22.5006 609.714L22.5656 609.349C22.7862 608.11 23.0407 606.68 23.2823 605.407C23.5588 603.949 23.8269 602.649 24.0126 602.092C24.1582 601.655 24.5135 600.462 25.0006 598.828C25.7326 596.37 26.7624 592.914 27.8254 589.522C28.4828 587.425 29.1509 585.359 29.7644 583.586C30.8893 580.337 31.6722 578.113 32.2369 576.592C32.7972 575.082 33.1541 574.231 33.4278 573.752C33.6081 573.436 33.8838 573.206 34.2444 573.177C34.5768 573.151 34.8534 573.309 35.0349 573.467C35.2214 573.631 35.3707 573.847 35.4546 574.069C35.5282 574.264 35.6014 574.605 35.4029 574.902C35.3028 575.053 34.9106 575.77 34.4603 576.795C34.0141 577.81 33.5264 579.093 33.2233 580.366C33.006 581.279 32.4151 583.461 31.7007 586.1C31.3806 587.282 31.0357 588.556 30.6885 589.848C29.5619 594.042 28.4108 598.43 27.9772 600.598C27.544 602.764 27.3584 604.341 27.187 605.898L27.1506 606.23C26.9919 607.681 26.8306 609.155 26.4788 611.089C26.2717 612.229 25.9745 613.569 25.6565 615.004C25.4002 616.16 25.1304 617.377 24.8832 618.599C24.3221 621.374 23.8619 624.24 23.8619 626.75C23.8619 629.294 24.1442 631.325 24.8119 632.704C25.1417 633.385 25.5572 633.89 26.0641 634.226C26.5685 634.561 27.1957 634.75 27.9869 634.75C31.2164 634.75 32.7098 632.571 33.1778 631.752C33.653 630.92 35.1351 627.467 35.7493 624.764C36.0509 623.437 36.4716 621.162 36.8841 618.932L36.9171 618.753C37.3372 616.481 37.747 614.273 38.0018 613.254C38.1103 612.82 38.3896 611.62 38.775 609.965C39.2757 607.813 39.9556 604.893 40.6724 601.884C41.935 596.584 43.3166 590.992 44.0141 588.962C44.3567 587.966 44.6839 586.976 44.9925 586.041L45.0564 585.847C45.3409 584.984 45.6094 584.17 45.8578 583.447C46.1241 582.671 46.3706 581.991 46.592 581.461C46.7027 581.197 46.8103 580.962 46.9141 580.769C47.0134 580.584 47.1271 580.403 47.2584 580.271C47.7632 579.767 48.4805 579.797 48.9605 580.037C49.2075 580.161 49.4415 580.361 49.5742 580.637C49.7148 580.929 49.7227 581.271 49.5591 581.599L49.5584 581.6C49.5573 581.602 49.555 581.607 49.5515 581.616C49.5464 581.628 49.54 581.643 49.5321 581.663C49.5163 581.704 49.4966 581.757 49.4729 581.823C49.4257 581.955 49.3657 582.132 49.2942 582.349C49.1514 582.784 48.9655 583.375 48.7482 584.084C48.3138 585.502 47.7561 587.389 47.171 589.449C45.9993 593.573 44.723 598.369 44.1022 601.473C43.5369 604.299 43.203 606.585 42.9002 608.658C42.8698 608.866 42.8397 609.072 42.8097 609.276C42.4814 611.511 42.1658 613.534 41.5962 615.75C41.5222 616.037 41.4365 616.36 41.3428 616.714C40.7159 619.078 39.7337 622.782 39.5329 626.155C39.4174 628.095 39.5665 629.86 40.1456 631.173C40.7081 632.449 41.6756 633.308 43.2968 633.504C44.8908 633.696 46.256 632.969 47.4554 631.607C48.6622 630.236 49.6522 628.271 50.4565 626.136C51.7287 622.76 52.4934 619.091 52.9491 616.904C53.0702 616.323 53.1695 615.847 53.2507 615.508C53.6146 613.992 54.412 611.294 55.1923 608.653L55.2887 608.327C56.1049 605.564 56.8779 602.934 57.1226 601.772C57.6221 599.4 59.3778 591.87 60.3897 588.961C60.56 588.471 60.7461 587.896 60.9436 587.286C61.3089 586.157 61.7135 584.907 62.1311 583.848C62.4543 583.028 62.8036 582.272 63.1764 581.737C63.3621 581.471 63.5751 581.228 63.821 581.065C64.0776 580.896 64.397 580.798 64.7435 580.893C65.1835 581.013 65.3626 581.401 65.43 581.714C65.499 582.033 65.4908 582.422 65.4469 582.826C65.358 583.642 65.1015 584.688 64.7954 585.748C64.4874 586.814 64.1212 587.921 63.8025 588.865C63.7366 589.06 63.6731 589.247 63.6127 589.426C63.3772 590.12 63.1887 590.676 63.0927 591.012C62.8511 591.858 62.5436 593.515 62.2287 595.231L62.1932 595.424C61.8943 597.054 61.5918 598.704 61.345 599.629C61.2651 599.928 61.1432 600.366 60.9927 600.906C60.6206 602.24 60.0742 604.199 59.5583 606.247C58.9519 608.654 58.4011 611.133 58.2344 612.8C58.0463 614.681 58.0008 616.353 58.4707 617.606C58.6999 618.217 59.0487 618.718 59.5631 619.098C60.0806 619.48 60.7969 619.761 61.7953 619.878C62.6218 619.976 63.4344 619.544 64.2417 618.645C65.0447 617.75 65.7657 616.472 66.3908 615.091C67.0129 613.716 67.526 612.27 67.9241 611.06C68.0932 610.547 68.2447 610.065 68.3752 609.649C68.5449 609.109 68.6791 608.683 68.7702 608.445C69.0763 607.65 70.321 603.886 71.5421 600.082C72.7663 596.268 73.9423 592.489 74.123 591.645C74.3198 590.727 73.1475 588.314 74.0812 585.824C75.0189 583.324 75.3393 580.677 76.0397 579.276C76.6986 577.958 77.6817 574.933 78.6479 571.369C79.6182 567.791 80.5574 563.723 81.119 560.416C81.4022 558.749 81.7636 557.028 82.1306 555.299L82.1947 554.997C82.5414 553.363 82.8895 551.724 83.1823 550.099C83.8051 546.643 82.9245 543.342 82.4987 540.362C82.4566 540.067 82.4153 539.78 82.375 539.5C81.5808 533.983 82.4334 531.228 84.2754 526.808C84.9727 525.134 85.712 523.232 86.4564 521.318C87.6918 518.14 88.9413 514.927 90.0366 512.658C90.115 512.495 90.2023 512.313 90.2993 512.11C92.3799 507.763 97.4102 495.867 115.589 471.947C134.551 446.997 146.513 418.941 149.746 402.28C152.345 388.884 154.495 376.704 155.244 372.291C155.254 372.231 155.275 372.174 155.306 372.121C166.615 352.936 175.232 336.871 180.276 318.989C180.336 318.777 180.527 318.629 180.747 318.625C180.968 318.621 181.165 318.761 181.233 318.971C182.549 323.024 184.708 328.638 186.919 334.388C187.325 335.442 187.732 336.501 188.136 337.555C190.736 344.342 193.213 350.968 194.222 355.004C195.221 359.001 195.84 361.102 196.455 362.73C196.916 363.952 197.374 364.908 197.999 366.213C198.208 366.649 198.436 367.124 198.689 367.662L198.877 368.06C200.809 372.164 202.741 376.264 203.235 381.326C203.338 382.383 203.516 383.724 203.737 385.306C203.794 385.715 203.853 386.139 203.915 386.578C204.758 392.581 205.987 401.334 205.987 410.5C205.987 416.675 204.845 422.413 203.734 428.003L203.696 428.191C202.57 433.855 201.488 439.365 201.612 445.114C201.692 448.854 201.799 451.908 201.906 454.479C201.979 456.246 202.054 457.789 202.12 459.172C202.257 462.017 202.362 464.188 202.362 466.25C202.362 467.453 202.03 468.889 201.525 470.591C201.227 471.597 200.859 472.724 200.452 473.972C200.17 474.837 199.868 475.761 199.557 476.744C198.031 481.571 196.228 487.979 195.108 496.565C194.247 503.164 193.404 508.029 192.65 512.113C192.472 513.074 192.3 513.992 192.133 514.877C191.592 517.756 191.114 520.299 190.727 522.947C190.414 525.085 190.162 527.289 189.986 529.785C189.985 529.8 189.983 529.815 189.98 529.83C187.109 547.584 181.324 588.473 181.125 610.13C180.947 629.404 182.031 651.365 182.764 666.2C183.062 672.239 183.302 677.098 183.375 680.113C183.624 690.462 184.996 704.795 188.483 717.745C188.814 718.975 189.171 720.223 189.547 721.482C190.442 724.482 191.44 727.545 192.44 730.592L192.678 731.318C193.597 734.119 194.511 736.902 195.335 739.598C197.123 745.444 198.51 750.932 198.625 755.362C198.743 759.931 198.054 762.847 197.274 766.144C197.22 766.372 197.166 766.601 197.112 766.833C196.273 770.405 195.374 774.617 195.25 782.008C195 796.863 195.874 812.097 196.124 816.094C196.142 816.39 196.113 816.843 196.054 817.406C195.994 817.979 195.899 818.699 195.776 819.546C195.528 821.241 195.164 823.464 194.725 826.079C194.562 827.051 194.389 828.077 194.208 829.152C193.413 833.862 192.463 839.493 191.541 845.482C189.274 860.21 187.191 877.029 187.998 887.587C189.394 905.84 194.486 937.866 197.714 958.173C198.24 961.479 198.716 964.473 199.119 967.048C199.542 969.75 200.033 972.766 200.558 975.996C201.818 983.735 203.277 992.703 204.48 1001.53C206.182 1014.02 207.378 1026.29 206.748 1034.41C206.123 1042.48 205.685 1046.77 205.403 1049.49L205.382 1049.7C205.116 1052.27 205 1053.39 205 1055.12C205 1056.77 205.372 1058.82 205.782 1061.08L205.867 1061.55C206.303 1063.97 206.75 1066.57 206.75 1069C206.75 1070.79 206.839 1072.4 206.917 1073.79C206.941 1074.23 206.965 1074.66 206.984 1075.05C207.023 1075.88 207.043 1076.61 207.013 1077.23C206.983 1077.85 206.901 1078.4 206.705 1078.83C206.437 1079.42 206.018 1080.01 205.572 1080.65C205.457 1080.81 205.34 1080.97 205.224 1081.14C204.64 1081.99 204.006 1082.99 203.463 1084.31C203.031 1085.37 202.852 1086.34 202.674 1087.3C202.629 1087.54 202.585 1087.78 202.537 1088.02C202.297 1089.22 201.964 1090.41 201.011 1091.57C200.513 1092.17 199.649 1092.85 198.635 1093.61C198.4 1093.78 198.154 1093.97 197.899 1094.15C194.947 1096.34 190.814 1099.41 188.054 1104.01C186.031 1107.38 185.082 1110.75 184.73 1113.56C184.377 1116.39 184.629 1118.62 184.977 1119.72C185.155 1120.29 185.325 1120.64 185.47 1120.86C185.611 1121.08 185.736 1121.19 185.851 1121.29C185.948 1121.37 186.158 1121.52 186.295 1121.74C186.448 1121.98 186.5 1122.27 186.5 1122.62C186.5 1123.14 186.743 1124.01 187.231 1124.76C187.718 1125.5 188.366 1126 189.125 1126C190.1 1126 190.83 1125.91 191.316 1125.77C191.559 1125.7 191.719 1125.62 191.813 1125.56C191.866 1125.52 191.887 1125.5 191.893 1125.49C191.957 1125.26 192.175 1125.11 192.413 1125.13C192.656 1125.15 192.851 1125.34 192.873 1125.58C192.953 1126.46 193.128 1127.24 193.422 1127.81C193.71 1128.38 194.083 1128.69 194.571 1128.76C195.841 1128.94 196.982 1128.9 197.843 1128.65C198.7 1128.39 199.213 1127.95 199.397 1127.35C199.465 1127.13 199.677 1126.99 199.908 1127C200.14 1127.02 200.33 1127.19 200.368 1127.42C200.511 1128.28 200.829 1129.04 201.382 1129.59C201.925 1130.12 202.745 1130.5 204 1130.5C205.218 1130.5 206.045 1130.11 206.616 1129.55C207.198 1128.98 207.551 1128.2 207.739 1127.39C207.798 1127.13 208.046 1126.96 208.305 1127.01C208.565 1127.05 208.748 1127.28 208.724 1127.55C208.631 1128.57 208.804 1130.11 209.599 1131.37C210.372 1132.6 211.765 1133.62 214.25 1133.62C215.827 1133.62 216.903 1133.26 217.649 1132.75C218.396 1132.24 218.854 1131.56 219.147 1130.84C219.442 1130.12 219.564 1129.39 219.643 1128.79C219.654 1128.7 219.665 1128.61 219.675 1128.53C219.7 1128.33 219.722 1128.15 219.747 1128.01C219.765 1127.91 219.79 1127.79 219.828 1127.69C219.856 1127.61 219.937 1127.41 220.151 1127.3C220.503 1127.13 220.999 1127.06 221.406 1127.06C221.618 1127.06 221.834 1127.08 222.025 1127.12C222.191 1127.16 222.432 1127.23 222.603 1127.4C222.725 1127.52 222.761 1127.67 222.774 1127.72C222.791 1127.8 222.799 1127.88 222.803 1127.95C222.81 1128.1 222.807 1128.27 222.802 1128.46C222.792 1128.84 222.774 1129.32 222.796 1129.84C222.842 1130.9 223.058 1131.98 223.728 1132.65C224.5 1133.42 225.439 1134.12 226.794 1134.61C228.154 1135.1 229.96 1135.37 232.475 1135.25C238.69 1134.95 242.404 1131.31 244.632 1127.54C246.54 1124.31 247.326 1121.05 247.644 1119.73C247.699 1119.5 247.74 1119.33 247.771 1119.23C247.823 1119.06 247.91 1118.83 248.016 1118.56C248.729 1116.71 250.282 1112.68 247.565 1107.87C247.132 1107.11 246.736 1106.43 246.378 1105.81C245.778 1104.78 245.288 1103.94 244.927 1103.21C244.634 1102.62 244.412 1102.08 244.273 1101.54C243.914 1100.14 244.152 1098.86 244.91 1096.94C245.323 1095.89 245.814 1094.85 246.312 1093.8L246.441 1093.53C246.898 1092.57 247.357 1091.61 247.773 1090.62C248.683 1088.46 249.375 1086.22 249.375 1083.75C249.375 1081.24 249.04 1079.82 248.721 1078.65C248.689 1078.53 248.656 1078.41 248.624 1078.3C248.334 1077.25 248.066 1076.29 248.125 1074.85C248.154 1074.15 248.229 1073.01 248.323 1071.57C248.638 1066.76 249.163 1058.7 248.876 1053.53C248.785 1051.9 248.336 1049.85 247.702 1047.45C247.386 1046.25 247.026 1044.97 246.646 1043.62C246.266 1042.26 245.865 1040.84 245.47 1039.35C243.888 1033.4 242.374 1026.44 242.5 1019.12C242.751 1004.58 246.881 970.902 254.274 948.096C261.62 925.437 261.249 906.262 259.876 889.29C259.375 883.094 257.991 876.29 256.578 869.34C256.06 866.796 255.539 864.232 255.055 861.671C253.259 852.156 251.989 842.697 253.509 834.657C255.016 826.681 257.214 820.183 259.183 814.477L259.41 813.823C261.289 808.383 262.911 803.69 263.504 799.061C264.125 794.22 264.281 786.347 264.422 779.178L264.423 779.133C264.493 775.574 264.559 772.193 264.678 769.466C264.738 768.098 264.811 766.888 264.905 765.899C264.998 764.921 265.115 764.126 265.271 763.606C265.575 762.593 266.069 760.135 266.638 756.913C268.103 748.62 270.017 735.524 270.376 729.968C270.548 727.306 271.525 722.08 272.827 715.896C274.132 709.699 275.773 702.497 277.287 695.855L277.288 695.848L277.592 694.514C278.926 688.658 280.133 683.336 280.885 679.65C281.108 678.559 281.357 677.364 281.626 676.072C284.383 662.836 289.238 639.531 289.125 616.127V603.034C289.125 602.891 289.186 602.754 289.294 602.659C289.401 602.565 289.544 602.52 289.687 602.538C290.8 602.677 291.986 602.75 293.25 602.75C294.513 602.75 295.699 602.677 296.813 602.538C296.955 602.52 297.098 602.565 297.206 602.659C297.313 602.754 297.375 602.891 297.375 603.034V616.127C297.261 639.531 302.117 662.836 304.874 676.072C305.143 677.364 305.392 678.559 305.615 679.65C306.367 683.336 307.573 688.658 308.907 694.514L309.211 695.848L309.213 695.855C310.726 702.497 312.367 709.699 313.672 715.896C314.975 722.08 315.952 727.306 316.124 729.968C316.482 735.524 318.397 748.62 319.861 756.913C320.43 760.135 320.925 762.593 321.229 763.606C321.385 764.126 321.501 764.921 321.595 765.899C321.689 766.888 321.762 768.098 321.822 769.466C321.941 772.193 322.007 775.574 322.077 779.133L322.078 779.178C322.219 786.347 322.375 794.22 322.996 799.061C323.589 803.69 325.211 808.383 327.09 813.823L327.316 814.477C329.286 820.183 331.484 826.681 332.991 834.657C334.51 842.697 333.241 852.156 331.444 861.671C330.961 864.232 330.439 866.796 329.922 869.34C328.508 876.29 327.124 883.094 326.623 889.29C325.25 906.262 324.88 925.437 332.225 948.096C339.619 970.902 343.749 1004.58 344 1019.12C344.126 1026.44 342.612 1033.4 341.03 1039.35C340.634 1040.84 340.234 1042.26 339.854 1043.62L339.829 1043.7C339.458 1045.03 339.107 1046.28 338.798 1047.45C338.163 1049.85 337.715 1051.9 337.624 1053.53C337.336 1058.7 337.862 1066.76 338.177 1071.57C338.27 1073.01 338.345 1074.15 338.374 1074.85C338.434 1076.29 338.166 1077.25 337.875 1078.3C337.843 1078.41 337.811 1078.53 337.779 1078.65C337.459 1079.82 337.125 1081.24 337.125 1083.75C337.125 1086.22 337.816 1088.46 338.726 1090.62C339.143 1091.61 339.601 1092.57 340.059 1093.53L340.188 1093.8C340.685 1094.85 341.177 1095.89 341.59 1096.94C342.348 1098.86 342.586 1100.14 342.226 1101.54C342.088 1102.08 341.866 1102.62 341.573 1103.21C341.211 1103.94 340.722 1104.78 340.121 1105.81C339.764 1106.43 339.367 1107.11 338.935 1107.87C336.217 1112.68 337.771 1116.71 338.484 1118.56C338.589 1118.83 338.677 1119.06 338.729 1119.23C338.759 1119.33 338.801 1119.5 338.856 1119.73C339.174 1121.05 339.96 1124.31 341.868 1127.54C344.096 1131.31 347.809 1134.95 354.024 1135.25C356.539 1135.37 358.346 1135.1 359.706 1134.61C361.06 1134.12 362 1133.42 362.771 1132.65C363.442 1131.98 363.657 1130.9 363.703 1129.84C363.723 1129.39 363.711 1128.97 363.701 1128.61L363.697 1128.46C363.692 1128.27 363.689 1128.1 363.697 1127.95C363.701 1127.88 363.709 1127.8 363.726 1127.72C363.739 1127.67 363.775 1127.52 363.896 1127.4C364.068 1127.23 364.309 1127.16 364.475 1127.12C364.666 1127.08 364.882 1127.06 365.094 1127.06C365.5 1127.06 365.997 1127.13 366.348 1127.3C366.563 1127.41 366.644 1127.61 366.672 1127.69C366.71 1127.79 366.734 1127.91 366.752 1128.01C366.788 1128.21 366.819 1128.5 366.857 1128.79C366.936 1129.39 367.058 1130.12 367.353 1130.84C367.646 1131.56 368.104 1132.24 368.85 1132.75C369.596 1133.26 370.672 1133.62 372.25 1133.62C374.735 1133.62 376.128 1132.6 376.901 1131.37C377.696 1130.11 377.869 1128.57 377.775 1127.55C377.752 1127.28 377.935 1127.05 378.194 1127.01C378.454 1126.96 378.701 1127.13 378.76 1127.39C378.949 1128.2 379.302 1128.98 379.884 1129.55C380.455 1130.11 381.282 1130.5 382.5 1130.5C383.733 1130.5 384.551 1130.12 385.099 1129.58C385.657 1129.03 385.982 1128.26 386.132 1127.41C386.173 1127.19 386.363 1127.02 386.594 1127C386.824 1126.99 387.035 1127.13 387.103 1127.35C387.286 1127.95 387.8 1128.39 388.657 1128.65C389.518 1128.9 390.659 1128.94 391.929 1128.76C392.393 1128.69 392.766 1128.38 393.061 1127.8C393.359 1127.22 393.541 1126.43 393.627 1125.57C393.652 1125.33 393.847 1125.14 394.089 1125.13C394.327 1125.11 394.543 1125.26 394.606 1125.49C394.613 1125.5 394.633 1125.52 394.686 1125.56C394.781 1125.62 394.941 1125.7 395.184 1125.77C395.669 1125.91 396.4 1126 397.375 1126C398.134 1126 398.782 1125.5 399.269 1124.76C399.756 1124.01 400 1123.14 400 1122.62C400 1122.27 400.052 1121.98 400.205 1121.74C400.322 1121.55 400.492 1121.41 400.6 1121.33C400.618 1121.31 400.634 1121.3 400.648 1121.29C400.764 1121.19 400.889 1121.08 401.029 1120.86C401.174 1120.64 401.345 1120.29 401.523 1119.72C401.87 1118.62 402.123 1116.39 401.769 1113.56C401.417 1110.75 400.468 1107.38 398.446 1104.01C395.686 1099.41 391.552 1096.34 388.6 1094.15C388.346 1093.97 388.1 1093.78 387.865 1093.61C386.851 1092.85 385.987 1092.17 385.489 1091.57C384.535 1090.41 384.203 1089.22 383.963 1088.02C383.915 1087.78 383.87 1087.54 383.826 1087.3C383.647 1086.34 383.469 1085.37 383.037 1084.31C382.494 1082.99 381.86 1081.99 381.276 1081.14C381.159 1080.97 381.043 1080.81 380.928 1080.65C380.481 1080.01 380.062 1079.42 379.795 1078.83C379.598 1078.4 379.517 1077.85 379.487 1077.23C379.456 1076.61 379.477 1075.88 379.516 1075.05C379.535 1074.66 379.558 1074.23 379.583 1073.79C379.661 1072.4 379.75 1070.79 379.75 1069C379.75 1066.57 380.196 1063.97 380.633 1061.55L380.718 1061.08C381.128 1058.82 381.5 1056.77 381.5 1055.12C381.5 1053.39 381.384 1052.27 381.118 1049.7L381.096 1049.49C380.815 1046.77 380.377 1042.48 379.751 1034.41C379.122 1026.29 380.317 1014.02 382.02 1001.53C383.222 992.703 384.682 983.736 385.941 975.997C386.467 972.767 386.958 969.75 387.381 967.048C387.784 964.473 388.26 961.478 388.785 958.172C392.014 937.865 397.105 905.84 398.501 887.587C399.309 877.029 397.226 860.21 394.959 845.482C394.037 839.495 393.087 833.866 392.293 829.156C392.111 828.08 391.938 827.052 391.774 826.079C391.336 823.464 390.971 821.241 390.724 819.546C390.6 818.699 390.506 817.979 390.445 817.406C390.386 816.843 390.357 816.39 390.376 816.094C390.626 812.097 391.5 796.863 391.25 782.008C391.126 774.617 390.227 770.405 389.388 766.833C389.334 766.601 389.279 766.372 389.226 766.144C388.446 762.847 387.756 759.931 387.875 755.362C387.99 750.932 389.377 745.444 391.165 739.598C391.989 736.903 392.902 734.12 393.821 731.319L394.06 730.592C395.06 727.545 396.058 724.482 396.953 721.482C397.329 720.223 397.686 718.975 398.017 717.745C401.504 704.795 402.876 690.462 403.125 680.113C403.198 677.098 403.438 672.24 403.736 666.2C404.468 651.365 405.553 629.404 405.375 610.13C405.175 588.473 399.391 547.584 396.519 529.83C396.517 529.815 396.515 529.8 396.514 529.785C396.338 527.289 396.085 525.085 395.773 522.947C395.386 520.299 394.908 517.756 394.366 514.877C394.2 513.992 394.027 513.074 393.85 512.113C393.095 508.029 392.253 503.164 391.392 496.565C390.272 487.979 388.468 481.571 386.942 476.744C386.631 475.761 386.33 474.837 386.048 473.972C385.64 472.723 385.273 471.597 384.975 470.591C384.47 468.889 384.138 467.453 384.138 466.25C384.138 464.189 384.242 462.017 384.379 459.173C384.446 457.789 384.52 456.246 384.594 454.479C384.701 451.908 384.807 448.854 384.888 445.114C385.012 439.365 383.93 433.855 382.804 428.191L382.766 428.003C381.654 422.413 380.513 416.675 380.513 410.5C380.513 401.334 381.742 392.581 382.585 386.579C382.646 386.14 382.706 385.715 382.763 385.306C382.983 383.724 383.162 382.383 383.265 381.326C383.759 376.264 385.69 372.164 387.623 368.06L387.636 368.032L387.81 367.662C388.063 367.124 388.291 366.649 388.501 366.212C389.126 364.908 389.584 363.952 390.045 362.73C390.659 361.102 391.279 359.001 392.278 355.004C393.287 350.968 395.764 344.342 398.364 337.555C398.768 336.501 399.175 335.442 399.581 334.388C401.792 328.638 403.951 323.024 405.267 318.971C405.335 318.761 405.532 318.621 405.752 318.625C405.973 318.629 406.164 318.777 406.224 318.989C411.267 336.871 419.885 352.936 431.194 372.121C431.212 372.152 431.226 372.184 431.237 372.218C431.245 372.242 431.251 372.266 431.256 372.291C432.005 376.704 434.154 388.884 436.754 402.28C439.987 418.941 451.949 446.997 470.911 471.947C489.089 495.867 495.62 509.513 497.7 513.86C497.797 514.063 497.885 514.245 497.963 514.408C499.058 516.677 500.308 519.891 501.543 523.068C502.288 524.983 503.027 526.884 503.724 528.558C505.566 532.978 505.176 535.692 504.382 541.209C504.341 541.489 504.3 541.776 504.258 542.071C503.832 545.051 504.195 548.393 504.817 551.849C505.11 553.473 505.458 555.113 505.805 556.745L505.869 557.049C506.236 558.778 506.598 560.499 506.881 562.166C507.442 565.473 507.53 566.921 508.5 570.5C509.466 574.064 509.61 577.235 510.269 578.553C510.97 579.954 510.711 582.72 511.649 585.221C512.583 587.71 512.43 590.477 512.627 591.395C512.807 592.239 513.983 596.018 515.208 599.832C516.429 603.636 517.673 607.4 517.979 608.195C518.071 608.432 518.205 608.859 518.374 609.398C518.505 609.814 518.656 610.296 518.826 610.81C519.224 612.02 519.737 613.466 520.359 614.841C520.984 616.222 521.705 617.5 522.508 618.395C523.315 619.294 524.128 619.726 524.954 619.628C525.953 619.511 526.669 619.23 527.187 618.848C527.701 618.468 528.05 617.967 528.279 617.356C528.749 616.103 528.703 614.431 528.515 612.55C528.349 610.883 527.798 608.404 527.191 605.997C526.676 603.949 526.129 601.99 525.757 600.655C525.607 600.116 525.485 599.678 525.405 599.379C525.158 598.453 524.855 596.8 524.556 595.169L524.521 594.981C524.206 593.265 523.899 591.608 523.657 590.762C523.561 590.427 523.373 589.871 523.138 589.178C523.077 588.999 523.013 588.811 522.947 588.615C522.629 587.671 522.262 586.564 521.954 585.498C521.648 584.438 521.392 583.392 521.303 582.576C521.259 582.172 521.251 581.783 521.32 581.464C521.387 581.151 521.566 580.763 522.006 580.643C522.353 580.548 522.672 580.646 522.929 580.815C523.175 580.978 523.388 581.221 523.573 581.487C523.946 582.022 524.295 582.778 524.619 583.598C525.036 584.657 525.441 585.908 525.806 587.036C526.004 587.647 526.19 588.221 526.36 588.711C527.372 591.62 529.128 599.15 529.627 601.522C529.872 602.684 530.645 605.314 531.461 608.077L531.557 608.403C532.338 611.044 533.135 613.742 533.499 615.258C533.58 615.597 533.68 616.073 533.801 616.654C534.256 618.841 535.021 622.51 536.293 625.886C537.098 628.021 538.087 629.986 539.294 631.357C540.494 632.719 541.859 633.446 543.453 633.254C545.074 633.058 546.042 632.199 546.604 630.923C547.183 629.61 547.332 627.845 547.217 625.905C547.016 622.532 546.034 618.827 545.407 616.463C545.313 616.11 545.228 615.787 545.154 615.5C544.584 613.284 544.268 611.261 543.94 609.026C543.91 608.822 543.88 608.615 543.85 608.407C543.547 606.335 543.213 604.049 542.648 601.223C542.027 598.119 540.75 593.323 539.579 589.199C538.994 587.139 538.436 585.252 538.002 583.834C537.784 583.125 537.598 582.534 537.456 582.099C537.384 581.882 537.324 581.705 537.277 581.573C537.253 581.507 537.233 581.454 537.218 581.413C537.21 581.393 537.203 581.378 537.198 581.366C537.193 581.353 537.191 581.349 537.191 581.349C537.027 581.021 537.035 580.679 537.176 580.387C537.308 580.111 537.542 579.911 537.789 579.787C538.269 579.547 538.987 579.517 539.491 580.021C539.623 580.153 539.736 580.334 539.836 580.519C539.889 580.618 539.943 580.728 539.998 580.847C540.051 580.961 540.104 581.082 540.158 581.211C540.379 581.741 540.626 582.421 540.892 583.197C541.056 583.673 541.228 584.189 541.408 584.732C541.521 585.075 541.638 585.429 541.757 585.791C542.066 586.726 542.393 587.716 542.736 588.712C543.433 590.742 544.815 596.334 546.077 601.634C546.795 604.645 547.475 607.568 547.976 609.719C548.361 611.372 548.639 612.57 548.748 613.004C549.003 614.023 549.413 616.231 549.833 618.503C550.256 620.79 550.691 623.152 551 624.514C551.615 627.217 553.097 630.67 553.572 631.502C554.04 632.321 555.533 634.5 558.763 634.5C559.554 634.5 560.181 634.311 560.686 633.976C561.193 633.64 561.608 633.135 561.938 632.454C562.606 631.075 562.888 629.044 562.888 626.5C562.888 623.99 562.428 621.124 561.867 618.349C561.619 617.127 561.35 615.91 561.093 614.754C560.775 613.32 560.478 611.979 560.271 610.839C559.919 608.904 559.758 607.431 559.599 605.98L559.563 605.648C559.391 604.091 559.206 602.514 558.773 600.348C558.339 598.18 557.188 593.792 556.061 589.598C555.714 588.308 555.37 587.035 555.05 585.854C554.335 583.214 553.744 581.029 553.526 580.116C553.223 578.843 552.736 577.56 552.289 576.545C551.839 575.52 551.447 574.803 551.347 574.652C551.148 574.355 551.222 574.014 551.295 573.819C551.379 573.597 551.528 573.381 551.715 573.217C551.896 573.059 552.173 572.901 552.505 572.927C552.866 572.956 553.142 573.186 553.322 573.502C553.596 573.981 553.953 574.832 554.513 576.342C555.078 577.863 555.86 580.087 556.985 583.336C557.599 585.109 558.267 587.175 558.924 589.272C559.987 592.663 561.017 596.118 561.749 598.575C562.236 600.211 562.591 601.405 562.737 601.842C562.923 602.399 563.191 603.699 563.467 605.157C563.733 606.557 564.014 608.147 564.249 609.464C564.487 610.797 564.671 611.823 564.748 612.129C564.758 612.167 564.769 612.216 564.783 612.274C564.953 612.979 565.451 615.043 566.542 616.973C567.725 619.066 569.522 620.864 572.242 620.75C573.604 620.694 574.487 620.172 575.082 619.411C575.693 618.628 576.03 617.55 576.189 616.341C576.431 614.5 576.246 612.529 576.112 611.101C576.068 610.635 576.03 610.227 576.013 609.9C575.891 607.443 574.65 600.486 573.647 595.219C572.649 589.983 570.652 581.62 569.528 577.121C569.398 576.604 569.246 575.991 569.076 575.304C567.757 569.984 565.332 560.209 563.792 555.919C562.4 552.04 559.641 545.748 558.432 543.081C558.362 542.927 558.376 542.747 558.468 542.604C558.56 542.461 558.718 542.375 558.888 542.375C559.868 542.375 561.321 542.816 562.684 543.364C564.063 543.919 565.444 544.623 566.297 545.214C567.072 545.75 567.639 546.446 568.246 547.19C568.332 547.296 568.419 547.403 568.508 547.51C569.23 548.386 570.107 549.383 571.563 550.475C572.991 551.546 575.01 552.395 577.15 552.79C579.292 553.184 581.505 553.116 583.332 552.409C585.074 551.734 585.816 550.409 585.969 549.068C586.128 547.686 585.655 546.349 585.034 545.729C584.309 545.003 583.426 544.302 582.564 543.628L582.371 543.477C581.59 542.868 580.829 542.273 580.284 541.729C579.646 541.09 579.428 540.541 579.23 539.999L579.202 539.92C579.026 539.435 578.851 538.951 578.363 538.3C577.274 536.849 575.061 534.754 573.329 533.393C572.493 532.736 571.155 531.974 569.76 531.221C569.352 531.002 568.94 530.783 568.536 530.568C567.58 530.061 566.674 529.58 566.006 529.179C565.009 528.581 564.158 527.758 563.362 526.963C563.283 526.884 563.204 526.804 563.126 526.726C562.405 526.002 561.724 525.319 560.972 524.782C559.252 523.553 554.91 520.947 550.445 519.087L550.444 519.086C549.142 518.54 546.338 517.394 545.369 517.104C544.733 516.913 542.782 516.284 540.843 515.511C539.875 515.125 538.899 514.7 538.09 514.272C537.685 514.058 537.314 513.84 537.003 513.621C536.698 513.406 536.424 513.174 536.238 512.925C536.173 512.838 536.1 512.714 536.021 512.565C535.975 512.476 535.926 512.378 535.876 512.275C535.736 511.984 535.566 511.606 535.37 511.153C534.978 510.245 534.477 509.019 533.896 507.553C532.734 504.621 535.248 502.715 533.669 498.439C530.512 489.889 526.977 479.848 524.91 473.147C523.018 467.012 521.39 458.532 519.506 448.721C519.326 447.785 519.144 446.836 518.959 445.876C516.834 434.845 514.338 422.3 510.781 409.509C504.258 386.048 498.266 374.992 493.077 365.416C492.611 364.555 492.151 363.706 491.697 362.861C490.456 360.548 489.378 358.652 488.427 356.981C485.185 351.28 483.431 348.195 481.773 340.1C480.718 334.949 478.479 328.072 476.181 321.429C475.452 319.322 474.717 317.237 474.013 315.241C472.505 310.963 471.139 307.091 470.284 304.27C469.3 301.023 466.865 296.001 464.197 291.011C461.535 286.033 458.665 281.129 456.836 278.136C456.794 278.067 456.769 277.989 456.764 277.909C456.555 274.816 456.138 266.438 456.138 257.625C456.138 246.628 455.131 225.519 444.475 209.907C439.487 202.599 435.413 198.296 431.046 195.191C426.671 192.079 421.977 190.15 415.688 187.618C414.769 187.248 413.815 186.865 412.825 186.463C405.625 183.546 400.761 182.679 395.921 181.932C395.56 181.876 395.199 181.821 394.837 181.766C390.33 181.078 385.681 180.369 379.1 178.098C364.955 173.216 353.048 167.829 346.136 163.807C342.698 161.806 339.361 159.762 336.849 157.834C335.594 156.871 334.527 155.924 333.755 155.015C332.991 154.116 332.465 153.197 332.39 152.292C332.321 151.469 332.29 148.831 332.28 145.376C332.262 138.401 332.332 127.965 332.388 121.87C332.389 121.76 332.426 121.653 332.495 121.566C333.03 120.886 333.491 119.861 333.884 118.613C334.275 117.374 334.587 115.956 334.844 114.521C335.1 113.088 335.3 111.648 335.466 110.367C335.51 110.034 335.551 109.712 335.59 109.402C335.702 108.526 335.801 107.755 335.894 107.171C336.105 105.852 336.203 104.624 336.276 103.704C336.289 103.551 336.3 103.406 336.311 103.271C336.349 102.824 336.386 102.421 336.452 102.191C336.468 102.134 336.499 102.039 336.559 101.95C336.618 101.865 336.801 101.659 337.105 101.713C337.332 101.752 337.451 101.919 337.482 101.963C337.531 102.033 337.568 102.11 337.597 102.178C337.614 102.217 337.631 102.257 337.648 102.297C337.828 102.721 338.017 103.169 338.439 103.579C338.891 104.018 339.656 104.452 341.075 104.629C341.593 104.694 342.149 104.437 342.771 103.787C343.388 103.142 343.991 102.191 344.587 101.077C345.131 100.06 345.653 98.9374 346.167 97.8331L346.308 97.5311C346.862 96.3398 347.411 95.1773 347.956 94.2471C348.976 92.5084 349.869 89.6067 350.539 86.3675C351.206 83.1423 351.639 79.6378 351.763 76.7287C351.82 75.4052 351.725 74.2913 351.521 73.357C351.181 71.8082 350.538 70.7504 349.759 70.0359C348.978 69.3192 348.033 68.9209 347.048 68.7419C346.186 68.5851 345.365 69.014 344.637 69.7488C343.915 70.4773 343.372 71.4253 343.098 72.0703C343.002 72.2972 342.755 72.4201 342.516 72.3599C342.277 72.2996 342.117 72.0743 342.14 71.8289C342.535 67.57 342.971 60.6912 342.997 53.9075C343.023 47.1006 342.634 40.4674 341.412 36.6526C339.87 31.8432 335.558 22.9162 327.758 15.1992C319.967 7.49234 308.706 1 293.237 1C277.767 1 266.513 7.4923 258.729 15.1991ZM365.735 1128.04C365.735 1128.04 365.735 1128.04 365.736 1128.04C365.729 1128.03 365.73 1128.02 365.735 1128.04Z"


val pathChest =
    "M103.625 5.13394C98.3745 1.63394 88.8745 0.883939 75.1245 0.00893884C71.8745 -0.202311 68.1245 3.38394 63.9995 5.13394C59.8745 6.88394 56.7495 5.13394 42.9995 9.75894C29.2495 14.3839 21.8745 26.1339 16.6245 30.7589C11.3745 35.3839 5.74951 38.3839 0.249512 56.6339C6.14951 53.8339 13.6245 47.9673 16.6245 45.3839V48.009L15.3418 63.25C18.2168 70 24.3168 87.325 25.7168 102.625C30.1168 106.825 41.4053 111.381 46.4995 113.134C55.7775 115.707 67.1489 116.695 80.7495 114.884C126.75 108.759 120.625 70.6339 120.25 47.2589C119.875 23.8839 108.875 8.63394 103.625 5.13394Z"

val frontAndSideDelt =
    "M0.499985 116.189L1.74012 90.3944C1.94957 87.36 2.37511 87.7354 2.37511 78.9785C2.37511 75.1426 10.0001 51.3541 20.7501 35.6041C27.638 27.5477 39.2501 17.0583 47.6251 10.9397C49.6251 9.47852 49.1251 9.85352 49.6251 9.47852C54.1251 6.29714 63.7795 -0.778499 70.125 1.41414C74.8195 3.03632 78.5952 4.82021 81.625 5.56436C88.75 7.31436 99.25 8.93936 116.625 10.9394C113.375 10.7281 108.75 14.3146 104.625 16.0646C100.5 17.8146 97.375 16.0646 83.625 20.6896C69.875 25.3146 62.5 37.0646 57.25 41.6896C52 46.3146 46.375 49.3146 40.875 67.5646C27.0026 79.0057 19.0707 88.108 12.3821 97.5644C7.37845 104.639 5.10036 108.326 0.499985 116.189Z"


val bicepMajor =
    "M43.375 8L53.5 0.625L52.375 14.625L50.375 42.75L49.75 65.75C46.625 74.375 27.25 106.125 20.5 115.125C13.75 124.125 13.25 127.5 13.25 129.75C13.25 131.55 16 134.083 17.375 135.125L22.375 141.5C22.075 144.5 19.25 157.75 17.875 164C17.625 162.958 16.425 159.825 13.625 155.625C10.125 150.375 2.5 143 1.5 139.5C0.5 136 1.25 130.5 0.75 126.125C2.125 102.5 6.25 82.625 14.125 58C20.425 38.3 36.25 16.4583 43.375 8Z"

val bicepMinor =
    "M30.0312 68.75C35.5312 50.375 36.9062 34.625 64.9062 0.875L58.5312 4.25C52.5729 8.83333 38.3813 21.425 29.2812 35.125C17.9062 52.25 11.6562 62.125 5.90625 82.25C1.30625 98.35 0.572914 119.208 0.78125 127.625C1.58125 130.425 2.69792 136.208 3.15625 138.75C12.7562 136.35 19.6562 132.083 21.9062 130.25C22.0729 128.875 22.3063 124.65 21.9062 118.75C21.4062 111.375 24.5312 87.125 30.0312 68.75Z"


val frontTricepSmall =
    "M0.75 10.125V0.875C3.85 7.275 12.125 21.2917 15.875 27.5L18.25 37.5L24.75 60.625C11.45 40.625 3.20833 18.625 0.75 10.125Z"

val frontTricepBig =
    "M1.5 25.25L0.375 0.125L6 9.125C8.04167 12.6667 12.325 20.525 13.125 23.625C14.125 27.5 20.375 43.875 21.5 47.625C22.625 51.375 23.25 62.5 23.125 64C23.025 65.2 23.5 68.0833 23.75 69.375C23.75 70.0833 23.85 72.55 24.25 76.75C24.65 80.95 20.8333 93 18.875 98.5C17.625 60.25 11.375 47.375 8.75 40C6.65 34.1 3.04167 27.7083 1.5 25.25Z"

val forearm =
    "M8.24988 15.875C7.99988 13.75 8.12488 7.375 7.37488 0.75L9.49988 6.75L11.4999 15.875L14.2499 22.625L21.4999 35.375C23.4165 41.125 27.9499 55.175 30.7499 65.375C34.2499 78.125 34.1249 89.875 39.7499 113C44.2499 131.5 48.6249 140.625 50.2499 142.875L51.9999 157.625C49.9582 155.167 42.1499 145.25 27.2499 125.25C8.62488 100.25 0.999876 73.625 0.624876 67.625C0.249876 61.625 3.37488 49.375 3.99988 46.75C4.62488 44.125 8.62488 34.375 9.12488 28.5C9.62488 22.625 8.49988 18 8.24988 15.875Z"

val wristFlexor =
    "M78.375 26C79.7917 25.0833 84.7 18.675 93 0.375C92.3 4.175 86.2083 21.875 83.25 30.25L77.125 48.5C72.0833 57.75 61.225 77.475 58.125 82.375C54.25 88.5 40 107.625 35.875 110.75C31.75 113.875 24.5 123.5 18.75 131C14.15 137 6.75 150.917 3.625 157.125L0 156.125C1.875 151.083 7.9 137.35 17 122.75C26.1 108.15 32.2917 88.8333 34.25 81L38.75 71L43.625 58.625L49.875 53L67.625 37.875L78.375 26Z"

val abs1 =
    "M53.6907 7.80102C53.3157 -2.19898 44.3157 0.176018 35.1907 1.42602C29.5177 5.31775 21.8425 8.21584 11.8157 9.55093C10.7323 10.1759 6.91568 14.1758 2.81568 25.1758C-2.30932 38.9258 0.477657 60.6599 3.94068 60.926C7.19068 61.1758 19.5657 51.3008 26.4407 47.426C33.3157 43.5513 45.6907 41.5508 51.6907 35.3008C54.4459 32.4308 54.0657 17.801 53.6907 7.80102Z"

val abs2 =
    "M44.3811 0.550568C40.3811 0.550568 34.8811 3.30057 23.3811 8.55057C17.3811 11.2897 11.9811 15.2256 6.38109 17.9256C-0.618914 21.3006 1.50609 31.9256 1.50609 34.9256C1.50609 37.7499 -0.377395 43.6765 1.80111 45.9253C1.93659 46.0652 2.08777 46.1908 2.25609 46.3006C5.13109 48.1756 36.8811 40.4256 44.3811 40.4256C51.8811 40.4256 51.5061 35.1756 51.8811 16.8006C52.2561 -1.57443 48.3811 0.550568 44.3811 0.550568Z"

val abs3 =
    "M51.1794 5.55141C48.1794 -2.07359 36.4294 1.17641 23.4294 3.30141C18.1794 4.15958 12.9928 5.89793 5.80441 6.55141C3.54641 6.75668 2.23423 8.285 1.47622 10.4262C-0.0736917 14.8043 0.693379 21.7448 0.42941 25.1764C-0.0890191 31.916 0.42463 39.6876 3.18315 43.4262C3.7502 44.1947 4.41211 44.7928 5.17941 45.1764C9.67941 47.4264 38.0544 41.3014 45.4294 39.5514C52.8044 37.8014 54.1794 13.1764 51.1794 5.55141Z"

val absBottom =
    "M49.61 148.185L49.4255 148.176L48.7405 148.185C41.4873 148.555 37.7801 148.743 35.425 141.926C33.05 135.051 23.175 111.551 19.8 103.176C16.425 94.801 14.55 77.176 13.925 68.551C13.3 59.926 10.425 50.301 7.425 45.926C4.425 41.551 0.925 29.801 0.8 24.301C0.8 19.051 -0.45 12.926 0.175 12.051L0.179974 12.044L0.180025 12.044C0.243921 11.9545 0.333657 11.8288 0.479413 11.6758C1.78532 10.3047 7.58853 6.73626 39.6012 7.29947C40.2222 2.10026 46.3298 0.300781 49.1755 0.300781C52.0211 0.300781 58.1283 2.10026 58.7492 7.29947C90.7619 6.73626 96.5652 10.3047 97.8711 11.6758C98.0168 11.8287 98.1065 11.9544 98.1704 12.0439L98.1705 12.044L98.1755 12.051C98.8005 12.926 97.5505 19.051 97.5505 24.301C97.4255 29.801 93.9255 41.551 90.9255 45.926C87.9255 50.301 85.0505 59.926 84.4255 68.551C83.8005 77.176 81.9255 94.801 78.5505 103.176C75.1755 111.551 65.3005 135.051 62.9255 141.926C60.5704 148.743 56.8632 148.555 49.61 148.185Z"

val oblique1 =
    "M13.8672 45C12.6172 28.5 9.24218 17.5 3.24218 0.375C2.24254 13.4537 0.368523 43.4785 0.866648 58.9834C4.06641 68.6996 11.7538 86.7232 13.7292 94.625C15.7292 102.625 16.2292 103.125 18.2292 107.375L20.2422 111.875C15.2422 93 15.1172 61.5 13.8672 45Z"

val oblique2 =
    "M21.375 30.3618C29.625 26.9868 36.625 15.4085 55.75 12.8619C28.9259 16.4336 10.7729 8.82636 0.25 0.5L1.875 26.3618C3.75 33.1118 13.125 33.7368 21.375 30.3618Z"

val oblique3 =
    "M31.5625 24.874C43.0625 21.249 42.3125 1.74902 32.3125 0.874023C27.7867 4.19037 23.6733 7.56708 19.5625 9.24878C11.3125 12.6238 1.93753 11.9988 0.0625305 5.24878C-0.437469 14.2498 9.1875 17.874 10.8125 19.499C12.1125 20.799 14.5208 24.2074 15.5625 25.749C19.6625 25.149 27.9375 24.9157 31.5625 24.874Z"

val oblique4 =
    "M10.5 11.5C11.8 12.8 14.2083 16.2083 15.25 17.75C11.5 19.5 8.375 27.625 5.5 28.625C4.25 27.6253 3.125 25.1253 1.375 21.125L0 0.25C1.25 3.375 8.875 9.875 10.5 11.5Z"

val oblique5 =
    "M10.4483 0.875C14.5483 0.275002 22.8233 0.0416679 26.4483 0C34.1983 5.375 33.6983 16 33.3233 23.125C32.9483 30.25 28.5733 29.375 26.4483 28.25C23.5011 26.2852 14.1532 22.9778 8.44826 21.1058C7.76531 20.8817 7.13457 20.6782 6.57326 20.5C4.61493 18.7083 1.07346 14.375 0.0733643 11.875C2.94836 10.875 6.69826 2.625 10.4483 0.875Z"

val oblique6 =
    "M25.125 7.1442C22.1778 5.17941 12.8299 1.87197 7.125 0C3.25 1.01918 1.125 7.3942 0.25 8.7692L1.36209 13.894C1.46631 14.9622 1.64658 16.3129 1.86664 17.894C5.61664 28.394 7.375 29.1442 11.75 33.3942C14.2389 35.8119 18.9655 38.17 22.75 40.6442C30.375 44.2692 32 32.7692 32 24.6442C32 16.5192 32 12.5192 25.125 7.1442Z"

val oblique7 =
    "M10.7543 16.2502C6.37928 12.0002 4.62092 11.25 0.87092 0.75C1.70661 6.75391 3.12927 17.251 2.43343 34.6255C1.55843 45.7505 10.7543 50.7502 17.7543 54.5002C18.4722 54.8183 19.163 55.1125 19.8283 55.3916C25.3409 57.1255 27.7607 52.3627 28.8793 48.0002C30.1293 43.1252 29.3793 34.6252 27.6293 29.5002C25.8793 24.3752 15.1293 20.5002 10.7543 16.2502Z"

val oblique8 =
    "M4.60067 0.25C3.72567 11.375 14.9423 17.6406 21.9423 21.3906C30.4293 24.9505 34.775 26.0396 38.3683 42.4993C42.2433 60.2493 44.2433 57.7493 46.1183 68.6243C47.9933 79.4993 47.2434 90.9324 45.9934 98.8074C43.7434 90.6245 42.2308 87.3842 40.2775 84.124C39.2274 82.3714 38.1171 80.8306 36.7434 78.6243C34.8985 75.6613 31.0284 73.4734 26.7434 71.5686C21.4472 69.2144 15.5172 67.2926 11.9934 64.8743C9.17706 62.9415 7.14138 60.838 5.60618 58.499C3.66628 55.5435 2.51254 51.4618 1.60542 47.624C1.60542 44.5733 1.37562 41.2928 1.1497 35.874C1.04237 33.2998 0.93594 30.243 0.855419 26.499C0.663498 17.5747 3.34458 9.23978 4.60067 0.25Z"

fun String?.toPath(size: Size?, pathDestination: Path? = null): Path? {
    this?.let {
        size?.let {
            if (!TextUtils.isEmpty(this)) {
                val pathDestinationResult = pathDestination ?: kotlin.run {
                    Path()
                }
                val scaleMatrix = Matrix()
                val rectF = RectF()
                val path = PathParser().parsePathString(this).toPath(pathDestinationResult)
                val bounds = path.getBounds()
                val rectPath = Rect(
                    bounds.left.toInt(),
                    bounds.top.toInt(),
                    bounds.right.toInt(),
                    bounds.bottom.toInt()
                )
                val scaleXFactor = size.width / rectPath.width().toFloat()
                val scaleYFactor = size.height / rectPath.height().toFloat()
                val androidPath = path.asAndroidPath()
                scaleMatrix.setScale(scaleXFactor, scaleYFactor, rectF.centerX(), rectF.centerY())
                androidPath.computeBounds(rectF, true)
                androidPath.transform(scaleMatrix)
                return androidPath.asComposePath()
            }
        }
    }
    return null
}