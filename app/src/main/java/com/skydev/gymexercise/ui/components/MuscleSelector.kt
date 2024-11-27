package com.skydev.gymexercise.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

data class FrontMuscleSelectorState(
    val selected: Set<MuscleGroups>,
    val selectionToggle: (MuscleGroups) -> Unit = {},
)

enum class MuscleGroups {
    NECK, SIDE_DELTOID, FRONT_DELTOID, CHEST, OBLIQUE, ABS, BICEP, TRICEP, FOREARM, WRIST_FLAXOR, ABDUCTOR, ADDUCTOR, HIP_FLAXOR, QUADS, CALF
}

@Composable
fun rememberFrontMuscleSelectorState(
    initialSelected: Set<MuscleGroups> = setOf()
): FrontMuscleSelectorState {
    val selected = remember { mutableStateOf(initialSelected) }
    val selectionToggle: (MuscleGroups) -> Unit = remember {
        { muscle: MuscleGroups ->
            selected.value = if (selected.value.contains(muscle)) {
                selected.value - muscle
            } else {
                selected.value + muscle
            }
        }
    }

    return remember { FrontMuscleSelectorState(selected.value, selectionToggle) }
}

@Composable
fun MuscleSelector(modifier: Modifier = Modifier) {

}