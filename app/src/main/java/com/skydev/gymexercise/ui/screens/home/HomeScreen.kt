package com.skydev.gymexercise.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydev.gymexercise.ui.components.FrontMuscleSelector
import com.skydev.gymexercise.ui.components.RearMuscleSelector
import com.skydev.gymexercise.ui.components.rememberMuscleSelectorState
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController,
) {

    val selectorState = rememberMuscleSelectorState(height = 200.dp)
    val muscles by remember(selectorState.selected.size) {
        derivedStateOf {
            selectorState.selected.map { it.name }
        }
    }

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(selectorState.height + 300.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FrontMuscleSelector(modifier = Modifier.fillMaxHeight(), selectorState)
            RearMuscleSelector(modifier = modifier.fillMaxHeight(), selectorState)
        }
        LazyRow {
            items(muscles) { muscleName ->
                Text(text = muscleName) // Display each selected muscle's name
            }
        }
    }


}
