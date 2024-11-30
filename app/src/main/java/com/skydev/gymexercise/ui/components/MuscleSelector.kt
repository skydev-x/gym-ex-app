package com.skydev.gymexercise.ui.components

import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

data class MuscleSelectorState(
    val selected: Set<MuscleGroups>,
    val selectionToggle: (MuscleGroups) -> Unit,
    val getColor: (MuscleGroups) -> Brush,
    val height: Dp = 300.dp,
)

enum class MuscleGroups {
    NECK, SIDE_DELTOID, FRONT_DELTOID, CHEST, OBLIQUE, ABS, BICEP, TRICEP, FOREARM, ABDUCTOR, ADDUCTOR, HIP_FLAXOR, QUADS, CALF, TRAPS, LATS, GLUTES, HAMS, READ_DELT
}


@Composable
fun rememberMuscleSelectorState(
    height: Dp = 300.dp, initialSelected: Set<MuscleGroups> = setOf()
): MuscleSelectorState {
    val selected = remember { mutableStateOf(initialSelected) }
    val selectionToggle: (MuscleGroups) -> Unit = remember {
        { muscle: MuscleGroups ->
            selected.value = if (selected.value.contains(muscle)) {
                selected.value - muscle
            } else {
                selected.value + muscle
            }
            Log.d("muscle", "$selected")
        }
    }


    val getColor: (MuscleGroups) -> Brush = remember {
        { muscle: MuscleGroups ->
            if (selected.value.contains(muscle)) Brush.radialGradient(
                colors = listOf(
                    Color(0xFF7fd1b9),
                    Color(0xFF1c77c3),
                ), radius = if (listOf(
                        MuscleGroups.QUADS,
                        MuscleGroups.HIP_FLAXOR,
                        MuscleGroups.CALF,
                        MuscleGroups.HAMS,
                        MuscleGroups.ADDUCTOR,
                        MuscleGroups.ABDUCTOR,
                        MuscleGroups.LATS
                    ).contains(muscle)
                ) 200f else 75f
            ) else {
                if (listOf(
                        MuscleGroups.QUADS,
                        MuscleGroups.HIP_FLAXOR,
                        MuscleGroups.CALF,
                        MuscleGroups.HAMS,
                        MuscleGroups.ADDUCTOR,
                        MuscleGroups.ABDUCTOR,
                        MuscleGroups.LATS
                    ).contains(muscle)
                ) {
                    Brush.radialGradient(
                        listOf(
                            Color(0xFFfb6376),
                            Color(0xFFf0a6ca),

                            ), radius = 100f
                    )
                } else {
                    Brush.radialGradient(
                        listOf(
                            Color(0xFFfb6376),
                            Color(0xFFf0a6ca),
                        )
                    )
                }
            }
        }
    }

    return remember(height) {
        MuscleSelectorState(
            selected = selected.value, selectionToggle, getColor, height
        )
    }.also {
        selected.value = it.selected
    }
}

@Composable
fun RearMuscleSelector(
    modifier: Modifier = Modifier, state: MuscleSelectorState = rememberMuscleSelectorState()
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(state.height)
                .aspectRatio(0.5f)
                .clip(ShapePath(pathHumanRear))
                .background(Color.DarkGray),
        )



        Abductor(height = state.height, color = state.getColor(MuscleGroups.ABDUCTOR)) {
            state.selectionToggle(MuscleGroups.ABDUCTOR)
        }

        ObliqueBack(height = state.height, color = state.getColor(MuscleGroups.OBLIQUE)) {
            state.selectionToggle(MuscleGroups.OBLIQUE)
        }

        Lats(height = state.height, color = state.getColor(MuscleGroups.LATS)) {
            state.selectionToggle(MuscleGroups.LATS)
        }

        Traps(height = state.height, color = state.getColor(MuscleGroups.TRAPS)) {
            state.selectionToggle(MuscleGroups.TRAPS)
        }

        RearShoulder(height = state.height, color = state.getColor(MuscleGroups.SIDE_DELTOID)) {
            state.selectionToggle(MuscleGroups.SIDE_DELTOID)
        }

        RearDeltoid(height = state.height, color = state.getColor(MuscleGroups.READ_DELT)) {
            state.selectionToggle(MuscleGroups.READ_DELT)
        }




        Glutes(height = state.height, color = state.getColor(MuscleGroups.GLUTES)) {
            state.selectionToggle(MuscleGroups.GLUTES)
        }




        Hamstrings(height = state.height, color = state.getColor(MuscleGroups.HAMS)) {
            state.selectionToggle(MuscleGroups.HAMS)
        }

        Calf(height = state.height, color = state.getColor(MuscleGroups.CALF)) {
            state.selectionToggle(MuscleGroups.CALF)
        }

        BackAdductor(height = state.height, color = state.getColor(MuscleGroups.ADDUCTOR)) {
            state.selectionToggle(MuscleGroups.ADDUCTOR)
        }

        BackQuad(height = state.height, color = state.getColor(MuscleGroups.QUADS)) {
            state.selectionToggle(MuscleGroups.QUADS)
        }

        Tricep(height = state.height, color = state.getColor(MuscleGroups.TRICEP)) {
            state.selectionToggle(MuscleGroups.TRICEP)
        }

        BackForearm(height = state.height, color = state.getColor(MuscleGroups.FOREARM)) {
            state.selectionToggle(MuscleGroups.FOREARM)
        }


    }
}

val abductorBack1 =
    "M23.625 1.12557C17.25 3.50057 6.875 9.75057 0.25 21.7506C5.35 24.2502 8.20833 29.959 9 32.501C13 44.001 12.5833 58.7926 11.875 64.751L16.5 55.501L22.75 41.001L28.25 27.501C28.9583 25.8343 31.275 21.626 34.875 18.126C41.875 11.226 48.0417 11.6676 50.25 12.751C46.1667 9.66751 37.675 3.25057 36.375 2.25057C34.75 1.00057 30 -1.24943 23.625 1.12557Z"
val abductorBack2 =
    "M25.8746 23.5C24.4996 13.125 21.9996 4.875 14.3746 0.75L13.3746 2.375L12.4996 6.875L9.62463 15.25L7.37463 24.5L4.87463 41.5L1.99963 58L0.249634 70.125L1.87463 88.125L4.37463 85.125L8.49963 78.875L12.7496 71L19.3746 57.125L26.2496 43.25C26.5829 40.125 26.9746 31.8 25.8746 23.5Z"

val obliqueBack =
    "M21.125 27.75C12.325 17.95 5.875 5.66667 3.75 0.75C4 3.79167 4.575 11.35 4.875 17.25C5.175 23.15 4.33333 29.0417 3.875 31.25L2.125 40.75C1.58333 43.6667 0.5 49.9 0.5 51.5C0.5 53.1 0.75 60.25 0.875 63.625L1.375 76.625L5.5 69.625C7.25 67.5 11.6 62.25 15 58.25C18.4 54.25 22 51.3333 23.375 50.375C26 50.1667 32.025 49.55 35.125 48.75C38.225 47.95 42.5 49.5 44.25 50.375C40.2083 46.9167 29.925 37.55 21.125 27.75Z"

val glutes =
    "M81.1249 42.375C78.7083 37.5417 72.9999 24.425 69.4999 10.625C68.6666 8.45833 66.3749 3.55 63.8749 1.25C61.3749 -1.05 56.3332 0.541667 54.1249 1.625L48.9999 5.125L43.9999 10.625L40.7499 16.875L35.9999 28.125L30.1249 42.375L25.7499 50.875C22.1249 58 14.2499 73.725 11.7499 79.625C9.24988 85.525 3.37488 93.75 0.749878 97.125L1.74988 110.25L2.87488 130.75C2.87488 137.708 2.99988 153.3 3.49988 160C3.99988 166.7 7.29154 169.792 8.87488 170.5C9.91654 167.958 12.7999 161.95 15.9999 158.25C21.7999 151.85 33.8332 149.583 39.1249 149.25L62.9999 146.5C70.9582 145.542 88.6749 141.35 95.8749 132.25C104.875 120.875 103.75 108.125 103.75 94.75C103.75 81.375 102.375 76.125 100.5 72C98.6249 67.875 93.7499 62.625 90.4999 58.625C87.8999 55.425 83.1666 46.4583 81.1249 42.375Z"
val ham1 =
    "M33.3828 109.625C32.7995 90.2086 32.7578 41.1252 37.2578 0.125234C35.3828 0.0419006 29.7328 0.500234 22.1328 3.00023C12.6328 6.12523 8.38281 13.7502 7.25781 15.6252C6.35781 17.1252 4.63281 21.9169 3.88281 24.1252C3.38281 26.7502 2.18281 33.5002 1.38281 39.5002C0.582813 45.5002 0.466145 58.0836 0.507812 63.6252L1.13281 89.5002L2.25781 113.875C2.50781 120.042 3.25781 134.525 4.25781 143.125C5.50781 153.875 3.63281 160.5 2.38281 165.5C1.38281 169.5 1.21614 178.917 1.25781 183.125L1.63281 201.625L2.00781 213.25C3.04948 209.5 5.80781 200.225 8.50781 193.125C11.8828 184.25 17.5078 178.375 22.3828 172.625C27.2578 166.875 31.3828 151.5 33.3828 140.5C34.9828 131.7 34.0495 116.25 33.3828 109.625Z"
val ham2 =
    "M1.50009 37C2.00009 29.3 3.62509 13.7917 4.37509 7C8.87506 6.5 19.1 5.375 24 4.875C28.9 4.375 38.5417 1.75 42.75 0.5V18.625L42 37.875L39.875 61L37 88L34.125 108.75L30.125 138.25C29.125 143.458 28.275 156.575 32.875 167.375C38.6123 180.845 38.5006 183.862 38.1275 193.933L38.125 194C37.825 202.1 34.3334 213.958 32.625 218.875C31.4167 222.708 28.7 231.75 27.5 237.25C26.3 242.75 27 251.792 27.5 255.625C26.7084 251.583 24.925 241.975 24.125 235.875C23.325 229.775 23.125 211.333 23.125 202.875L23.3751 192.125C23.8334 190 24.4751 185.375 23.3751 183.875C22.2751 182.375 16.5001 182.5 13.7501 182.75C12.7084 180.542 9.65009 172.475 5.75009 157.875C1.85009 143.275 0.45842 117.292 0.250088 106.125V75.25C0.45842 65.7083 1.00009 44.7 1.50009 37Z"
val calf =
    "M63.0663 17.6252C63.1913 25.5002 60.9413 53.2502 68.1913 81.8752C70.0913 88.475 72.733 105.875 73.8163 113.75C74.8077 126.008 75.2793 139.435 72.7007 154.5C72.206 157.391 71.5989 160.342 70.8617 163.356C62.6537 192.427 52.9011 195.498 48.0663 195.25C43.1913 195 35.6792 183.375 36.1671 168.25C36.2579 165.436 36.35 162.9 36.4413 160.625C35.6888 179.375 25.3163 186.875 21.3163 187C17.5218 187.119 11.5899 184.988 8.0026 165.88C5.13557 147.409 1.99245 125.871 0.94133 112.125C-0.68367 90.8752 8.54137 45.25 9.31633 41.3752C11.7274 29.3198 15.7209 23.4184 17.0944 17.6252C17.3587 16.5106 17.526 15.4 17.5663 14.2502C17.7663 8.55022 15.3163 7.00023 14.8163 3.87523C14.8163 2.83354 16.7163 0.725206 24.3163 0.625231C33.8163 0.500231 38.3163 10.1252 39.6913 10.1252C41.0663 10.1252 44.6913 8.50023 47.8163 6.50023C49.2172 5.60368 51.2461 5.00853 53.4413 4.72612C56.143 4.37853 59.0965 4.50456 61.4413 5.12523C65.6913 6.25023 62.9413 9.75023 63.0663 17.6252Z"
val soleus =
    "M0.5 0.5L7.25 41.625C13.0859 51.2737 24.589 74.2039 26.9018 93.1566C27.0346 94.5516 27.1505 95.8773 27.25 97.125C27.1837 95.8263 27.0659 94.5015 26.9018 93.1566C24.4759 67.6737 16.4281 19.0828 0.5 0.5Z"
val quadBack1 =
    "M11.625 58.5C11.9167 49.9583 11.575 26.35 7.87499 0.25C6.29166 11.7083 2.875 36.675 1.875 44.875C0.875 53.075 0.208334 71.4583 0 79.625L0.375 100.5L1.625 129.5L2.625 151.25L4 164.375C5 170.25 7.575 183.6 9.875 190C12.175 196.4 15.75 208.833 17.25 214.25L14.625 170.25C14.0417 156.083 13.025 125.025 13.625 114.125C14.225 103.225 16.625 93.1667 17.75 89.5C17.2083 89.875 15.625 89.35 13.625 84.25C11.625 79.15 11.4583 64.9583 11.625 58.5Z"

val quadBack2 =
    "M24.8779 34.875L31.5029 0.625L29.8779 4.625L21.1279 27.875L12.6279 50.375L6.62793 67.25L2.00293 81.75C1.46126 83.5 0.35293 89 0.25293 97C0.152929 105 2.46126 112.5 3.62793 115.25L8.00293 125.75L9.37793 133.75L10.0029 115.25L11.2529 109.375L14.6279 88.625L15.7529 77.125L19.6279 58.125L24.8779 34.875Z"

val adductor =
    "M0 93.125C6.95833 73.375 21.275 27.175 22.875 0.375C20.075 4.675 16.7083 7.5 15.375 8.375L4.375 13.875C6.375 29.675 2.29167 73.2917 0 93.125Z"
val tricep1 =
    "M42.3906 17.25L49.8906 0.125C45.1823 2.125 34.8156 7.15 31.0156 11.25C27.2156 15.35 21.5156 25.7917 19.1406 30.5L15.2656 38.625C14.724 40.3333 13.2656 44.75 11.7656 48.75C9.89062 53.75 4.26562 70.75 2.39062 79C0.525993 87.2044 0.639305 90.093 0.886494 96.3946L0.890625 96.5C1.14062 102.875 3.76562 113.25 4.76562 116.375C5.76562 119.5 7.89062 124.25 9.14062 124.25C10.3906 124.25 11.2656 114.375 10.6406 106.375C10.0156 98.375 14.5156 85.5 20.7656 73.625C25.7656 64.125 32.849 53.5833 35.7656 49.5L37.7656 46.375C33.8656 42.275 33.3906 38.1667 33.6406 36.625L42.3906 17.25Z"
val tricep2 =
    "M42.0156 2.75L38.2656 0.25C36.8656 1.25 23.4323 7.91667 16.8906 11.125L10.6406 25.75C8.43229 30.6667 3.51562 41.45 1.51562 45.25C-0.984375 50 2.26562 54.25 4.01562 56.625C5.76562 59 10.3906 61.5 12.3906 61.5C14.3906 61.5 20.0156 55.375 28.3906 46.5C35.0906 39.4 41.6823 30.4583 44.1406 26.875L42.0156 2.75Z"
val tricep3 =
    "M53.6406 26.125L51.2656 0C48.8073 2.95833 42.8906 10.175 38.8906 15.375C34.8906 20.575 26.474 28.625 22.7656 32C21.599 33.2083 18.8656 35.325 17.2656 34.125C16.1406 40.75 10.6406 58.25 4.89062 72.375C-0.853168 86.4848 -0.110985 93.6096 0.513599 99.6056L0.515625 99.625C1.14062 105.625 1.89062 105 2.89062 110.125C3.89062 115.25 4.14062 118.375 2.51562 122.125C1.21563 125.125 1.55729 126.792 1.89062 127.25C2.72396 127.583 4.74062 127.775 6.14062 125.875C7.89062 123.5 12.1406 118.875 15.7656 115.875C19.3906 112.875 22.3906 107.375 23.6406 102.875C24.6406 99.275 29.5573 91.2917 31.8906 87.75L42.7656 67.5L47.7656 56.5L52.5156 43.125L53.6406 36.5V26.125Z"
val wristExtensor1 =
    "M41.6621 10.375L38.2871 0.75L31.9121 15.25L28.4121 25.125L24.6621 37.625L21.6621 48.5L18.0371 66.125L15.4121 79.625L11.7871 96.5L7.53711 109.625C6.07878 114.208 2.76211 124.25 1.16211 127.75C-0.837891 132.125 1.91211 136.75 6.53711 139.625C10.2371 141.925 15.6621 144.25 17.9121 145.125L19.9121 138L22.5371 126.375L26.0371 103L28.4121 83.125L30.4121 68.375L33.6621 50.5L36.0371 41.75C37.5371 27.15 40.4121 14.75 41.6621 10.375Z"
val wristExtensor2 =
    "M16 55.875C17.625 41.875 16.25 31.625 19 0C16.4 7 13.5 24.4167 12.375 32.25C11 42.7917 8.025 65.925 7.125 74.125C6.225 82.325 2.33333 96.9583 0.5 103.25L5.375 104.875C8.375 97.75 14.375 69.875 16 55.875Z"
val wristExtensor3 =
    "M33.1242 68.25C36.9992 46.625 27.8742 22.625 19.6242 0.25C17.7242 5.85 15.4158 21.0833 14.4991 28C13.5408 42.0833 11.6241 72.525 11.6241 81.625C11.6241 90.725 7.62415 109.333 5.62415 117.5L0.499146 137C2.37415 137.458 7.22415 138.1 11.6241 137C13.5241 126.2 22.4158 99.3333 26.6241 87.25C27.9158 84.2083 31.0242 76.15 33.1242 68.25Z"
val wristFlexorBack =
    "M68.125 23.5L72.125 0C70.125 2.25 66.5 11.25 65.125 15C64.025 18 59.3333 22.5 57.125 24.375C55.7083 25.375 52.675 27.8 51.875 29.5C51.075 31.2 49.125 32.9583 48.25 33.625C47.95 40.025 41.7083 44.5417 38.625 46L36.125 41.625L32.375 51.5L27.875 63.875L22.5 77.875L14.625 100.375L6.125 126.25L0.75 147.125L3.5 146.125L9.375 134.5L16.125 123L25.5 108.875C29.25 103.833 37.5 92.65 40.5 88.25C44.25 82.75 48.375 75.125 55.375 62.125C60.975 51.725 66.2083 32.0417 68.125 23.5Z"


@Composable
fun BackForearm(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.365f), y = -height.times(0.2f))
    ) {
        Box(modifier = Modifier
            .size(height.div(12f), height.div(6f))
            .clip(ShapePath(wristExtensor2))
            .border(
                shape = ShapePath(wristExtensor2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.37f), y = -height.times(0.22f))
    ) {
        Box(modifier = Modifier
            .size(height.div(12f), height.div(3.5f))
            .clip(ShapePath(wristExtensor1))
            .border(
                shape = ShapePath(wristExtensor1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }



    Box(
        modifier = Modifier.offset(x = -height.times(0.34f), y = -height.times(0.2f))
    ) {
        Box(modifier = Modifier
            .size(height.div(18f), height.div(4.5f))
            .clip(ShapePath(wristExtensor3))
            .border(
                shape = ShapePath(wristExtensor3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.295f), y = -height.times(0.22f))
    ) {
        Box(modifier = Modifier
            .size(height.div(10f), height.div(4f))
            .clip(ShapePath(wristFlexorBack))
            .border(
                shape = ShapePath(wristFlexorBack), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.365f), y = -height.times(0.2f))
        .graphicsLayer { rotationY = 180f }) {
        Box(modifier = Modifier
            .size(height.div(12f), height.div(6f))
            .clip(ShapePath(wristExtensor2))
            .border(
                shape = ShapePath(wristExtensor2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.37f), y = -height.times(0.22f))
        .graphicsLayer { rotationY = 180f }) {
        Box(modifier = Modifier
            .size(height.div(12f), height.div(3.5f))
            .clip(ShapePath(wristExtensor1))
            .border(
                shape = ShapePath(wristExtensor1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }



    Box(modifier = Modifier
        .offset(x = height.times(0.34f), y = -height.times(0.2f))
        .graphicsLayer { rotationY = 180f }) {
        Box(modifier = Modifier
            .size(height.div(18f), height.div(4.5f))
            .clip(ShapePath(wristExtensor3))
            .border(
                shape = ShapePath(wristExtensor3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.295f), y = -height.times(0.22f))
        .graphicsLayer { rotationY = 180f }) {
        Box(modifier = Modifier
            .size(height.div(10f), height.div(4f))
            .clip(ShapePath(wristFlexorBack))
            .border(
                shape = ShapePath(wristFlexorBack), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

@Composable
fun BackAdductor(
    height: Dp, color: Brush, onClick: () -> Unit
) {
    Box(
        modifier = Modifier.offset(x = -height.times(0.035f), y = height.times(0.16f))
    ) {

        Box(modifier = Modifier
            .size(height.div(16.5f), height.div(4f))
            .clip(ShapePath(adductor))
            .border(
                shape = ShapePath(adductor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.035f), y = height.times(0.16f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(16.5f), height.div(4f))
            .clip(ShapePath(adductor))
            .border(
                shape = ShapePath(adductor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

@Composable
fun BackQuad(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.04f), y = height.times(0.25f))
    ) {

        Box(modifier = Modifier
            .size(height.div(16f), height.div(4f))
            .clip(ShapePath(quadBack2))
            .border(
                shape = ShapePath(quadBack2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.04f), y = height.times(0.25f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(16f), height.div(4f))
            .clip(ShapePath(quadBack2))
            .border(
                shape = ShapePath(quadBack2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.17f), y = height.times(0.16f))
    ) {

        Box(modifier = Modifier
            .size(height.div(24f), height.div(3f))
            .clip(ShapePath(quadBack1))
            .border(
                shape = ShapePath(quadBack1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.17f), y = height.times(0.16f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(24f), height.div(3f))
            .clip(ShapePath(quadBack1))
            .border(
                shape = ShapePath(quadBack1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

@Composable
fun Calf(
    height: Dp, color: Brush, onClick: () -> Unit
) {
    Box(
        modifier = Modifier.offset(x = -height.times(0.11f), y = height.times(0.54f))
    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(calf))
            .border(
                shape = ShapePath(calf), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.11f), y = height.times(0.54f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(calf))
            .border(
                shape = ShapePath(calf), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.125f), y = height.times(0.8f))
    ) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(4f))
            .clip(ShapePath(soleus))
            .border(
                shape = ShapePath(soleus), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.125f), y = height.times(0.8f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(4f))
            .clip(ShapePath(soleus))
            .border(
                shape = ShapePath(soleus), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}


@Composable
fun Hamstrings(
    height: Dp, color: Brush, onClick: () -> Unit
) {


    Box(
        modifier = Modifier.offset(x = -height.times(0.08f), y = height.times(0.29f))
    ) {

        Box(modifier = Modifier
            .size(height.div(14f), height.div(2.3f))
            .clip(ShapePath(ham2))
            .border(
                shape = ShapePath(ham2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.13f), y = height.times(0.28f))
    ) {

        Box(modifier = Modifier
            .size(height.div(14f), height.div(2.5f))
            .clip(ShapePath(ham1))
            .border(
                shape = ShapePath(ham1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.08f), y = height.times(0.29f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(14f), height.div(2.3f))
            .clip(ShapePath(ham2))
            .border(
                shape = ShapePath(ham2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.13f), y = height.times(0.28f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(14f), height.div(2.5f))
            .clip(ShapePath(ham1))
            .border(
                shape = ShapePath(ham1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun Glutes(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.09f), y = -height.times(0.01f))
    ) {

        Box(modifier = Modifier
            .size(height.div(5.5f), height.div(2.5f))
            .clip(ShapePath(glutes))
            .border(
                shape = ShapePath(glutes), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.09f), y = -height.times(0.01f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(5.5f), height.div(2.5f))
            .clip(ShapePath(glutes))
            .border(
                shape = ShapePath(glutes), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

@Composable
fun Abductor(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.1f), y = -height.times(0.15f))
    ) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(6f))
            .clip(ShapePath(abductorBack1))
            .border(
                shape = ShapePath(abductorBack1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.148f), y = -height.times(0.08f))
    ) {

        Box(modifier = Modifier
            .size(height.div(13f), height.div(5f))
            .clip(ShapePath(abductorBack2))
            .border(
                shape = ShapePath(abductorBack2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.1f), y = -height.times(0.15f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(6f))
            .clip(ShapePath(abductorBack1))
            .border(
                shape = ShapePath(abductorBack1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.148f), y = -height.times(0.08f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(13f), height.div(5f))
            .clip(ShapePath(abductorBack2))
            .border(
                shape = ShapePath(abductorBack2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun ObliqueBack(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.115f), y = -height.times(0.23f))
    ) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(7f))
            .clip(ShapePath(obliqueBack))
            .border(
                shape = ShapePath(obliqueBack), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.115f), y = -height.times(0.23f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(7f))
            .clip(ShapePath(obliqueBack))
            .border(
                shape = ShapePath(obliqueBack), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

@Composable
fun Lats(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.1f), y = -height.times(0.35f))
    ) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(3f))
            .clip(ShapePath(lats))
            .border(
                shape = ShapePath(lats), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.1f), y = -height.times(0.35f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(3f))
            .clip(ShapePath(lats))
            .border(
                shape = ShapePath(lats), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}


@Composable
fun Tricep(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.283f), y = -height.times(0.415f))
    ) {

        Box(modifier = Modifier
            .size(height.div(10f), height.div(5.5f))
            .clip(ShapePath(tricep1))
            .border(
                shape = ShapePath(tricep1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.23f), y = -height.times(0.4f))
    ) {

        Box(modifier = Modifier
            .size(height.div(10f), height.div(5.5f))
            .clip(ShapePath(tricep3))
            .border(
                shape = ShapePath(tricep3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.23f), y = -height.times(0.47f))
    ) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(12f))
            .clip(ShapePath(tricep2))
            .border(
                shape = ShapePath(tricep2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.283f), y = -height.times(0.415f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(10f), height.div(5.5f))
            .clip(ShapePath(tricep1))
            .border(
                shape = ShapePath(tricep1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.23f), y = -height.times(0.4f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(10f), height.div(5.5f))
            .clip(ShapePath(tricep3))
            .border(
                shape = ShapePath(tricep3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.23f), y = -height.times(0.47f))
        .graphicsLayer { rotationY = 180f }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(12f))
            .clip(ShapePath(tricep2))
            .border(
                shape = ShapePath(tricep2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}


val lats =
    "M73.9258 0.801514C83.5059 22.1434 101.84 44.6836 112.037 55.75C112.037 83.5 104.125 115.5 98.7499 121.5C93.3749 127.5 98.9999 140.875 90.9999 151.625C82.9999 162.375 82.8749 172.25 74.9999 165.5C72.0291 162.954 69.4497 161.048 66.9999 159.755C44.8749 143.625 26.1249 115.5 25.1249 106.875C24.9048 105.294 24.3412 100.943 24.237 99.875C23.7515 94.8987 21.8517 90.8651 19.911 86.7445L19.737 86.375C17.737 82.125 17.237 81.625 15.237 73.625C13.237 65.625 5.24991 46.6729 2.12491 37.0479C3.54948 28.432 2.32921 18.3659 0.489471 0.203888C17.5808 5.81967 36.3307 9.55635 53.5271 7.19852C60.6716 6.21892 67.5479 4.18736 73.9258 0.801514Z"

@Composable
fun RearDeltoid(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.2f), y = -height.times(0.565f))
    ) {

        Box(modifier = Modifier
            .size(height.div(6f), height.div(5f))
            .clip(ShapePath(rearDelt))
            .border(
                shape = ShapePath(rearDelt), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.2f), y = -height.times(0.565f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(6f), height.div(5f))
            .clip(ShapePath(rearDelt))
            .border(
                shape = ShapePath(rearDelt), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun RearShoulder(
    height: Dp, color: Brush, onClick: () -> Unit
) {


    Box(
        modifier = Modifier.offset(x = -height.times(0.195f), y = -height.times(0.565f))
    ) {

        Box(modifier = Modifier
            .size(height.div(6f), height.div(5f))
            .clip(ShapePath(sideDeltBack))
            .border(
                shape = ShapePath(sideDeltBack), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.195f), y = -height.times(0.565f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(6f), height.div(5f))
            .clip(ShapePath(sideDeltBack))
            .border(
                shape = ShapePath(sideDeltBack), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


}


@Composable
fun Traps(
    height: Dp, color: Brush, onClick: () -> Unit
) {


    Box(
        modifier = Modifier.offset(x = -height.times(0.09f), y = -height.times(0.62f))
    ) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(2.2f))
            .clip(ShapePath(trapsMiddle))
            .border(
                shape = ShapePath(trapsMiddle), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.09f), y = -height.times(0.62f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(2.2f))
            .clip(ShapePath(trapsMiddle))
            .border(
                shape = ShapePath(trapsMiddle), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.09f), y = -height.times(0.75f))
    ) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(5f))
            .clip(ShapePath(trapsUpper))
            .border(
                shape = ShapePath(trapsUpper), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.09f), y = -height.times(0.75f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(5f))
            .clip(ShapePath(trapsUpper))
            .border(
                shape = ShapePath(trapsUpper), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(
        modifier = Modifier.offset(x = -height.times(0.055f), y = -height.times(0.52f))
    ) {

        Box(modifier = Modifier
            .size(height.div(8.5f), height.div(4.2f))
            .clip(ShapePath(trapsLower))
            .border(
                shape = ShapePath(trapsLower), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.055f), y = -height.times(0.52f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(8.5f), height.div(4.2f))
            .clip(ShapePath(trapsLower))
            .border(
                shape = ShapePath(trapsLower), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


}


val trapsUpper =
    "M2.29788 96.5C-5.82711 100.125 15.2979 101 31.2979 104C42.2495 107 104.627 80.5 109.298 75.875C109.298 73 124.548 75.125 124.548 12.2498C124.548 -2.50022 120.923 1.37478 115.298 1.37478C106.923 1.87478 97.6729 0.874777 96.5479 2.24978C96.3896 2.44314 96.4045 2.63155 96.5479 2.85326C97.424 4.20815 100.726 9.93164 101.048 22.5C101.277 31.4514 98.0092 42.1501 93.1987 51.875C90.1497 58.0388 85.979 63.4599 80.5479 66.9998L71.4099 72.875C64.5349 76.875 52.6599 82.25 38.5349 87.125C24.4099 92 19.2849 89.625 4.78491 95.5C3.7955 95.9009 2.84274 96.2837 1.92285 96.6541"

val trapsMiddle =
    "M96.5479 2.24978C97.6729 0.874777 106.923 1.87478 115.298 1.37478C120.923 1.37478 124.548 -2.50022 124.548 12.2498C124.548 75.125 109.298 73 109.298 75.875C109.298 79.75 119.173 86.25 121.548 112.875C123.448 134.175 124.34 212.917 124.548 249.625C123.73 248.781 122.82 247.819 121.835 246.75C111.638 235.684 93.3039 213.143 83.7238 191.802C83.6226 191.576 83.5222 191.35 83.4229 191.125C79.4897 182.2 75.0758 172.748 70.8772 163.805C61.1989 143.19 52.6646 125.278 53.7979 122.75C53.9447 122.423 54.0929 122.121 54.241 121.843C55.4049 119.659 56.5674 118.949 57.0479 118.875C53.7979 114.917 44.0979 106.4 31.2979 104C15.3423 101.008 -5.70993 100.13 2.23107 96.5301C3.05521 96.1984 3.90563 95.8563 4.785 95.5C19.285 89.625 24.41 92 38.535 87.125C52.66 82.25 64.535 76.875 71.41 72.875L80.5479 66.9998C85.9791 63.4599 90.1498 58.0388 93.1988 51.875C98.0093 42.1501 101.277 31.4514 101.048 22.5C100.726 9.93164 97.4241 4.20815 96.5479 2.85326C96.4046 2.63155 96.3897 2.44314 96.5479 2.24978Z"

val trapsLower =
    "M71.5489 131.625C70.7308 130.781 69.8214 129.819 68.8361 128.75C58.6387 117.684 40.3049 95.1434 30.7248 73.8015C30.6235 73.5759 30.5232 73.3503 30.4239 73.125C26.4907 64.1995 22.0768 54.7476 17.8782 45.8046C8.1999 25.1896 -0.334422 7.27822 0.79892 4.75C0.945698 4.42257 1.09387 4.12075 1.242 3.8428C2.4059 1.65896 3.56841 0.948927 4.04892 0.875L69.5657 13.625C70.7767 45.8421 71.382 102.22 71.5489 131.625ZM71.5489 131.625C72.3671 130.781 73.2764 129.819 74.2617 128.75"

val sideDeltBack =
    "M39.3753 2.96851C37.5003 1.21851 28.1253 -2.15649 23.1253 2.96851C20.9831 5.0101 16.0064 10.2683 13.2373 14.9685C2.48734 30.7185 1.48734 51.9685 1.48734 62.9685C1.48734 71.6964 1.07746 79.9981 0.8675 83.1417C4.72721 76.6035 13.2829 72.351 23.0003 67.8207C29.9163 64.5964 37.4207 61.2315 44.2394 56.8017C45.5637 55.9414 46.8621 55.0409 48.1253 54.0935C65.1253 41.3435 70.7503 28.2185 82.3753 18.5935C84.2942 17.0047 85.5115 15.6067 86.1323 14.36C89.2724 8.05421 77.1516 5.62133 63.3753 1.96851C46.8753 -2.40649 41.2503 4.71851 39.3753 2.96851Z"

val rearDelt =
    "M0.750313 83.3433C4.55195 76.6904 13.1833 72.3972 23.0003 67.8204C29.9163 64.5962 37.4207 61.2313 44.2394 56.8015C45.5637 55.9412 46.8621 55.0407 48.1253 54.0933C65.1253 41.3433 70.7503 28.2183 82.3753 18.5933C84.2942 17.0045 85.5115 15.6065 86.1323 14.3598C89.2724 8.05401 77.1511 5.6232 63.3753 1.9683C51.3752 -1.21547 48.1252 9.87504 47.1253 13.7493C44.544 23.75 35.0002 32.875 30.0002 38C27.858 40.0416 18.6273 47.6078 14.1252 51.625C6.00024 58.875 1.25027 69.4065 1.25027 73.1249C1.25027 81.9249 1.0707 80.1349 0.862373 83.2183"

@Composable
fun FrontMuscleSelector(
    modifier: Modifier = Modifier, state: MuscleSelectorState = rememberMuscleSelectorState()
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(state.height)
                .aspectRatio(0.5f)
                .clip(ShapePath(pathHumanFront))
                .background(Color.DarkGray),
        )

        FrontNeck(
            height = state.height, color = state.getColor(MuscleGroups.NECK)
        ) {
            state.selectionToggle(MuscleGroups.NECK)
        }

        ChestPec(
            height = state.height, color = state.getColor(MuscleGroups.CHEST)
        ) {
            state.selectionToggle(MuscleGroups.CHEST)
        }

        SideDelt(
            height = state.height, color = state.getColor(MuscleGroups.SIDE_DELTOID)
        ) {
            state.selectionToggle(MuscleGroups.SIDE_DELTOID)
        }

        FrontDelt(
            height = state.height, color = state.getColor(MuscleGroups.FRONT_DELTOID)
        ) {
            state.selectionToggle(MuscleGroups.FRONT_DELTOID)
        }

        FrontTricep(
            height = state.height, color = state.getColor(MuscleGroups.TRICEP)
        ) {
            state.selectionToggle(MuscleGroups.TRICEP)
        }

        FrontForeArm(
            height = state.height, color = state.getColor(MuscleGroups.FOREARM)
        ) {
            state.selectionToggle(MuscleGroups.FOREARM)
        }

        FrontBicep(height = state.height, color = state.getColor(MuscleGroups.BICEP)) {
            state.selectionToggle(MuscleGroups.BICEP)
        }

        FrontLegs(
            height = state.height, state
        )

        Abs(
            height = state.height, color = state.getColor(MuscleGroups.ABS)
        ) {
            state.selectionToggle(MuscleGroups.ABS)
        }

        Oblique(
            height = state.height, color = state.getColor(MuscleGroups.OBLIQUE)
        ) {
            state.selectionToggle(MuscleGroups.OBLIQUE)
        }

        LatFront(height = state.height, color = state.getColor(MuscleGroups.LATS)) {
            state.selectionToggle(MuscleGroups.LATS)
        }
    }

}

@Composable
fun FrontLegs(
    height: Dp, state: MuscleSelectorState
) {
    AbductorAndHipFlexor(
        height = height, state.getColor(MuscleGroups.ABDUCTOR)
    ) { state.selectionToggle(MuscleGroups.ABDUCTOR) }
    Adductor(height = height, state.getColor(MuscleGroups.ADDUCTOR)) {
        state.selectionToggle(
            MuscleGroups.ADDUCTOR
        )
    }
    Quads(
        height = height, state.getColor(MuscleGroups.QUADS)
    ) { state.selectionToggle(MuscleGroups.QUADS) }
    Calves(
        height = height, state.getColor(MuscleGroups.CALF)
    ) { state.selectionToggle(MuscleGroups.CALF) }
}

@Composable
fun Calves(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(modifier = Modifier
        .offset(x = -height.times(0.08f), y = height.times(0.68f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(24f), height.div(2.2f))
            .clip(ShapePath(calf4))
            .border(
                shape = ShapePath(calf4), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
    Box(modifier = Modifier
        .offset(x = -height.times(0.145f), y = height.times(0.68f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(2.2f))
            .clip(ShapePath(calf3))
            .border(
                shape = ShapePath(calf3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = -height.times(0.13f), y = height.times(0.68f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(2.2f))
            .clip(ShapePath(calf2))
            .border(
                shape = ShapePath(calf2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = -height.times(0.12f), y = height.times(0.65f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(2f))
            .clip(ShapePath(calf1))
            .border(
                shape = ShapePath(calf1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    //reflection
    Box(modifier = Modifier
        .offset(x = height.times(0.08f), y = height.times(0.68f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(24f), height.div(2.2f))
            .clip(ShapePath(calf4))
            .border(
                shape = ShapePath(calf4), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
    Box(modifier = Modifier
        .offset(x = height.times(0.145f), y = height.times(0.68f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(2.2f))
            .clip(ShapePath(calf3))
            .border(
                shape = ShapePath(calf3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.13f), y = height.times(0.68f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(2.2f))
            .clip(ShapePath(calf2))
            .border(
                shape = ShapePath(calf2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.12f), y = height.times(0.65f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(2f))
            .clip(ShapePath(calf1))
            .border(
                shape = ShapePath(calf1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


}

@Composable
fun Adductor(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(modifier = Modifier
        .offset(x = -height.times(0.03f), y = height.times(0.14f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(32f), height.div(5f))
            .clip(ShapePath(adductor1))
            .border(
                shape = ShapePath(adductor1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = -height.times(0.025f), y = height.times(0.11f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(32f), height.div(5f))
            .clip(ShapePath(adductor2))
            .border(
                shape = ShapePath(adductor2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.03f), y = height.times(0.14f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(32f), height.div(5f))
            .clip(ShapePath(adductor1))
            .border(
                shape = ShapePath(adductor1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.025f), y = height.times(0.11f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(32f), height.div(5f))
            .clip(ShapePath(adductor2))
            .border(
                shape = ShapePath(adductor2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}


@Composable
fun Quads(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(modifier = Modifier
        .offset(x = -height.times(0.152f), y = -height.times(0.016f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(4f))
            .clip(ShapePath(quad1))
            .border(
                shape = ShapePath(quad1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = -height.times(0.152f), y = height.times(0.169f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(2.2f))
            .clip(ShapePath(quad2))
            .border(
                shape = ShapePath(quad2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = -height.times(0.112f), y = height.times(0.13f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(10f), height.div(2f))
            .clip(ShapePath(quad3))
            .border(
                shape = ShapePath(quad3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = -height.times(0.06f), y = height.times(0.25f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(23f), height.div(3f))
            .clip(ShapePath(quad4))
            .border(
                shape = ShapePath(quad4), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.152f), y = -height.times(0.016f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(4f))
            .clip(ShapePath(quad1))
            .border(
                shape = ShapePath(quad1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.152f), y = height.times(0.169f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(2.2f))
            .clip(ShapePath(quad2))
            .border(
                shape = ShapePath(quad2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.112f), y = height.times(0.13f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(10f), height.div(2f))
            .clip(ShapePath(quad3))
            .border(
                shape = ShapePath(quad3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.06f), y = height.times(0.25f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(23f), height.div(3f))
            .clip(ShapePath(quad4))
            .border(
                shape = ShapePath(quad4), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = -height.times(0.075f), y = height.times(0.085f))
        .graphicsLayer {
            rotationZ = 6f
        }) {

        Box(modifier = Modifier
            .size(height.div(9f), height.div(2.7f))
            .clip(ShapePath(sartorius))
            .border(
                shape = ShapePath(sartorius), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.075f), y = height.times(0.085f))
        .graphicsLayer {
            rotationZ = 6f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(9f), height.div(2.7f))
            .clip(ShapePath(sartorius))
            .border(
                shape = ShapePath(sartorius), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun AbductorAndHipFlexor(
    height: Dp, color: Brush, onClick: () -> Unit
) {
    Box(modifier = Modifier
        .offset(x = -height.times(0.162f), y = -height.times(0.115f))
        .graphicsLayer {
            rotationZ = 0f
        }) {

        Box(modifier = Modifier
            .size(height.div(32f), height.div(8f))
            .clip(ShapePath(abductor))
            .border(
                shape = ShapePath(abductor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.162f), y = -height.times(0.115f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(32f), height.div(8f))
            .clip(ShapePath(abductor))
            .border(
                shape = ShapePath(abductor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = -height.times(0.06f), y = -height.times(0.0001f))
        .graphicsLayer {
            rotationZ = 0f
        }) {
        Box(modifier = Modifier
            .size(height.div(20f), height.div(4f))
            .clip(ShapePath(hipFlexor))
            .border(
                shape = ShapePath(hipFlexor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.06f), y = -height.times(0.0001f))
        .graphicsLayer {
            rotationZ = 0f
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(20f), height.div(4f))
            .clip(ShapePath(hipFlexor))
            .border(
                shape = ShapePath(hipFlexor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}


@Composable
fun FrontNeck(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = height.times(0.025f), y = -height.times(0.7f))
    ) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(8f))
            .clip(ShapePath(frontNeck))
            .border(
                shape = ShapePath(frontNeck), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = -height.times(0.025f), y = -height.times(0.7f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(12f), height.div(8f))
            .clip(ShapePath(frontNeck))
            .border(
                shape = ShapePath(frontNeck), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

@Composable
fun Oblique(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(modifier = Modifier
        .offset(x = -height.times(0.13f), y = -height.times(0.25f))
        .graphicsLayer {}) {
        Box(modifier = Modifier
            .size(height.div(24f), height.div(20f))
            .clip(ShapePath(oblique6))
            .border(
                shape = ShapePath(oblique6), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = -height.times(0.13f), y = -height.times(0.22f))
        .graphicsLayer {}) {
        Box(modifier = Modifier
            .size(height.div(20f), height.div(12f))
            .clip(ShapePath(oblique7))
            .border(
                shape = ShapePath(oblique7), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = -height.times(0.12f), y = -height.times(0.14f))
        .graphicsLayer {}) {
        Box(modifier = Modifier
            .size(height.div(14f), height.div(5f))
            .clip(ShapePath(oblique8))
            .border(
                shape = ShapePath(oblique8), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.13f), y = -height.times(0.25f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(24f), height.div(20f))
            .clip(ShapePath(oblique6))
            .border(
                shape = ShapePath(oblique6), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.13f), y = -height.times(0.22f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(20f), height.div(12f))
            .clip(ShapePath(oblique7))
            .border(
                shape = ShapePath(oblique7), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.12f), y = -height.times(0.14f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(14f), height.div(5f))
            .clip(ShapePath(oblique8))
            .border(
                shape = ShapePath(oblique8), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = -height.times(0.13f), y = -height.times(0.31f))
        .graphicsLayer {}) {
        Box(modifier = Modifier
            .size(height.div(30f), height.div(20f))
            .clip(ShapePath(oblique4))
            .border(
                shape = ShapePath(oblique4), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }



    Box(modifier = Modifier
        .offset(x = -height.times(0.13f), y = -height.times(0.28f))
        .graphicsLayer {}) {
        Box(modifier = Modifier
            .size(height.div(26f), height.div(20f))
            .clip(ShapePath(oblique5))
            .border(
                shape = ShapePath(oblique5), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.13f), y = -height.times(0.31f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(30f), height.div(20f))
            .clip(ShapePath(oblique4))
            .border(
                shape = ShapePath(oblique4), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }



    Box(modifier = Modifier
        .offset(x = height.times(0.13f), y = -height.times(0.28f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(26f), height.div(20f))
            .clip(ShapePath(oblique5))
            .border(
                shape = ShapePath(oblique5), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }




    Box(modifier = Modifier
        .offset(x = -height.times(0.13f), y = -height.times(0.34f))
        .graphicsLayer {}) {
        Box(modifier = Modifier
            .size(height.div(20f), height.div(20f))
            .clip(ShapePath(oblique4))
            .border(
                shape = ShapePath(oblique4), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = -height.times(0.14f), y = -height.times(0.37f))
        .graphicsLayer {}) {
        Box(modifier = Modifier
            .size(height.div(14f), height.div(20f))
            .clip(ShapePath(oblique3))
            .border(
                shape = ShapePath(oblique3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.13f), y = -height.times(0.34f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(20f), height.div(20f))
            .clip(ShapePath(oblique4))
            .border(
                shape = ShapePath(oblique4), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.14f), y = -height.times(0.37f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(14f), height.div(20f))
            .clip(ShapePath(oblique3))
            .border(
                shape = ShapePath(oblique3), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }




    Box(modifier = Modifier
        .offset(x = -height.times(0.13f), y = -height.times(0.4f))
        .graphicsLayer {}) {
        Box(modifier = Modifier
            .size(height.div(10f), height.div(18f))
            .clip(ShapePath(oblique2))
            .border(
                shape = ShapePath(oblique2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.13f), y = -height.times(0.4f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(10f), height.div(18f))
            .clip(ShapePath(oblique2))
            .border(
                shape = ShapePath(oblique2), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun LatFront(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(modifier = Modifier
        .offset(x = -height.times(0.18f), y = -height.times(0.48f))
        .graphicsLayer {
            rotationZ = 94f
            rotationX = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(6f), height.div(30f))
            .clip(ShapePath(oblique1))
            .border(
                shape = ShapePath(oblique1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.18f), y = -height.times(0.48f))
        .graphicsLayer {
            rotationZ = 94f
            rotationX = 180f
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(6f), height.div(30f))
            .clip(ShapePath(oblique1))
            .border(
                shape = ShapePath(oblique1), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

@Composable
fun Abs(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box {

        Box(
            modifier = Modifier.offset(x = -height.times(0.055f), y = -height.times(0.38f))
        ) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs1))
                .border(
                    shape = ShapePath(abs1), color = Color.DarkGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier.offset(x = -height.times(0.06f), y = -height.times(0.32f))
        ) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs2))
                .border(
                    shape = ShapePath(abs2), color = Color.DarkGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(
            modifier = Modifier.offset(x = -height.times(0.055f), y = -height.times(0.23f))
        ) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs3))
                .border(
                    shape = ShapePath(abs3), color = Color.DarkGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }


        Box(modifier = Modifier
            .offset(x = height.times(0.055f), y = -height.times(0.38f))
            .graphicsLayer {
                rotationY = 180f
            }) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs1))
                .border(
                    shape = ShapePath(abs1), color = Color.DarkGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(modifier = Modifier
            .offset(x = height.times(0.06f), y = -height.times(0.32f))
            .graphicsLayer {
                rotationY = 180f
            }) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs2))
                .border(
                    shape = ShapePath(abs2), color = Color.DarkGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }

        Box(modifier = Modifier
            .offset(x = height.times(0.055f), y = -height.times(0.23f))
            .graphicsLayer {
                rotationY = 180f
            }) {

            Box(modifier = Modifier
                .size(height.div(9f), height.div(9f))
                .clip(ShapePath(abs3))
                .border(
                    shape = ShapePath(abs3), color = Color.DarkGray, width = 1.dp
                )
                .background(color)
                .clickable { onClick() })
        }
    }

    Box(
        modifier = Modifier.offset(x = height.times(0f), y = -height.times(0.1f))
    ) {

        Box(modifier = Modifier
            .size(height.div(4.6f), height.div(5f))
            .clip(ShapePath(absBottom))
            .border(
                shape = ShapePath(absBottom), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun FrontForeArm(
    height: Dp, color: Brush, onClick: () -> Unit
) {


    Box(modifier = Modifier
        .offset(x = -height.times(0.29f), y = -height.times(0.24f))
        .graphicsLayer {
            rotationZ = 5f
        }) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(wristFlexor))
            .border(
                shape = ShapePath(wristFlexor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.29f), y = -height.times(0.24f))
        .graphicsLayer {
            rotationZ = 5f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(wristFlexor))
            .border(
                shape = ShapePath(wristFlexor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.35f), y = -height.times(0.24f))
        .graphicsLayer {
            rotationZ = 5f
        }) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(forearm))
            .border(
                shape = ShapePath(forearm), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = -height.times(0.35f), y = -height.times(0.24f))
        .graphicsLayer {
            rotationZ = 5f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3f))
            .clip(ShapePath(forearm))
            .border(
                shape = ShapePath(forearm), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun FrontBicep(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(modifier = Modifier
        .offset(x = -height.times(0.26f), y = -height.times(0.44f))
        .graphicsLayer {
            rotationZ = 5f
        }) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(4f))
            .clip(ShapePath(bicepMinor))
            .border(
                shape = ShapePath(bicepMinor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.26f), y = -height.times(0.44f))
        .graphicsLayer {
            rotationZ = 5f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(4f))
            .clip(ShapePath(bicepMinor))
            .border(
                shape = ShapePath(bicepMinor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = -height.times(0.24f), y = -height.times(0.41f))
        .graphicsLayer {
            rotationZ = -6f
        }) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3.5f))
            .clip(ShapePath(bicepMajor))
            .border(
                shape = ShapePath(bicepMajor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.24f), y = -height.times(0.41f))
        .graphicsLayer {
            rotationZ = -6f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(3.5f))
            .clip(ShapePath(bicepMajor))
            .border(
                shape = ShapePath(bicepMajor), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


}

@Composable
fun FrontTricep(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(modifier = Modifier
        .offset(x = height.times(0.28f), y = -height.times(0.42f))
        .graphicsLayer {
            rotationZ = 22f
        }) {

        Box(modifier = Modifier
            .size(height.div(6f), height.div(6f))
            .clip(ShapePath(frontTricepBig))
            .border(
                shape = ShapePath(frontTricepBig), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = -height.times(0.28f), y = -height.times(0.42f))
        .graphicsLayer {
            rotationZ = 22f
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(6f), height.div(6f))
            .clip(ShapePath(frontTricepBig))
            .border(
                shape = ShapePath(frontTricepBig), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.23f), y = -height.times(0.38f))
        .graphicsLayer {
            rotationZ = 22f
        }

    ) {

        Box(modifier = Modifier
            .size(height.div(8f), height.div(8f))
            .clip(ShapePath(frontTricepSmall))
            .border(
                shape = ShapePath(frontTricepSmall), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
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
                shape = ShapePath(frontTricepSmall), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


}


@Composable
fun ChestPec(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.11f), y = -height.times(0.55f))
    ) {
        Box(modifier = Modifier
            .size(height.div(4.5f), height.div(4.5f))
            .clip(ShapePath(pathChest))
            .border(
                shape = ShapePath(pathChest), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }


    Box(modifier = Modifier
        .offset(x = height.times(0.11f), y = -height.times(0.55f))
        .graphicsLayer {
            rotationY = 180f
        }) {
        Box(modifier = Modifier
            .size(height.div(4.5f), height.div(4.5f))
            .clip(ShapePath(pathChest))
            .border(
                shape = ShapePath(pathChest), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

}

@Composable
fun FrontDelt(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.19f), y = -height.times(0.56f))
    ) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(5f))
            .clip(ShapePath(frontAndSideDelt))
            .border(
                shape = ShapePath(frontAndSideDelt), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.19f), y = -height.times(0.56f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(5f), height.div(5f))
            .clip(ShapePath(frontAndSideDelt))
            .border(
                shape = ShapePath(frontAndSideDelt), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }
}

@Composable
fun SideDelt(
    height: Dp, color: Brush, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.offset(x = -height.times(0.21f), y = -height.times(0.58f))
    ) {

        Box(modifier = Modifier
            .size(height.div(5.5f), height.div(5f))
            .clip(ShapePath(frontAndSideDelt))
            .border(
                shape = ShapePath(frontAndSideDelt), color = Color.DarkGray, width = 1.dp
            )
            .background(color)
            .clickable { onClick() })
    }

    Box(modifier = Modifier
        .offset(x = height.times(0.21f), y = -height.times(0.58f))
        .graphicsLayer {
            rotationY = 180f
        }) {

        Box(modifier = Modifier
            .size(height.div(5.5f), height.div(5f))
            .clip(ShapePath(frontAndSideDelt))
            .border(
                shape = ShapePath(frontAndSideDelt), color = Color.DarkGray, width = 1.dp
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

val pathHumanFront =
    "M258.729 15.1991C250.935 22.9159 246.63 31.8429 245.088 36.6526C243.865 40.4674 243.476 47.1006 243.503 53.9075C243.529 60.6912 243.965 67.57 244.36 71.8289C244.383 72.0743 244.223 72.2996 243.984 72.3599C243.745 72.4201 243.498 72.2972 243.402 72.0703C243.128 71.4253 242.585 70.4773 241.863 69.7488C241.135 69.014 240.314 68.5851 239.451 68.7419C238.467 68.9209 237.521 69.3192 236.74 70.0359C235.962 70.7504 235.318 71.8082 234.979 73.357C234.774 74.2913 234.68 75.4052 234.736 76.7287C234.86 79.6378 235.294 83.1423 235.961 86.3675C236.631 89.6067 237.524 92.5084 238.543 94.2471C239.089 95.1773 239.637 96.3398 240.192 97.5311C240.239 97.6314 240.286 97.7319 240.332 97.8325C240.846 98.9371 241.368 100.059 241.912 101.077C242.508 102.191 243.111 103.142 243.729 103.787C244.351 104.437 244.907 104.694 245.425 104.629C246.844 104.452 247.609 104.018 248.06 103.579C248.483 103.169 248.672 102.721 248.852 102.297C248.869 102.257 248.885 102.217 248.902 102.178C248.931 102.11 248.969 102.033 249.018 101.963C249.049 101.919 249.168 101.752 249.395 101.713C249.699 101.659 249.882 101.865 249.94 101.95C250.001 102.039 250.031 102.134 250.048 102.191C250.113 102.421 250.151 102.824 250.188 103.271C250.2 103.406 250.211 103.551 250.223 103.704C250.297 104.624 250.395 105.852 250.606 107.171C250.701 107.768 250.797 108.537 250.906 109.404C250.941 109.687 250.978 109.98 251.016 110.281C251.173 111.513 251.36 112.893 251.609 114.287C252.11 117.099 252.847 119.877 254.024 121.592C254.08 121.674 254.111 121.771 254.112 121.87C254.168 127.965 254.238 138.401 254.219 145.376C254.21 148.831 254.179 151.469 254.11 152.292C254.035 153.197 253.509 154.116 252.745 155.015C251.972 155.924 250.906 156.871 249.651 157.834C247.139 159.762 243.802 161.806 240.363 163.807C233.452 167.829 221.544 173.216 207.4 178.098C200.818 180.369 196.17 181.078 191.663 181.766C191.301 181.821 190.94 181.876 190.579 181.932C185.739 182.679 180.874 183.546 173.675 186.463C172.872 186.789 172.093 187.102 171.337 187.406L170.812 187.618C164.523 190.15 159.829 192.079 155.454 195.191C151.087 198.296 147.013 202.599 142.025 209.907C131.369 225.519 130.362 246.628 130.362 257.625C130.362 266.438 129.945 274.816 129.736 277.909C129.73 277.989 129.706 278.067 129.664 278.136C127.834 281.129 124.964 286.033 122.303 291.011C119.635 296.001 117.199 301.023 116.215 304.27C115.361 307.091 113.995 310.963 112.487 315.241C111.783 317.237 111.048 319.322 110.319 321.429C108.021 328.072 105.782 334.949 104.727 340.1C103.069 348.195 101.314 351.28 98.0725 356.981C97.122 358.652 96.0437 360.548 94.8025 362.861C94.3492 363.706 93.8892 364.555 93.4227 365.416C88.2335 374.992 82.2419 386.048 75.7186 409.509C72.162 422.3 69.6652 434.845 67.5404 445.876C67.3555 446.836 67.1734 447.784 66.9936 448.721C65.11 458.531 63.4818 467.012 61.5897 473.147C59.5231 479.848 55.9881 489.889 52.8309 498.439C51.252 502.715 54.0162 504.871 52.8541 507.803C52.2732 509.269 51.7721 510.495 51.38 511.403C51.1841 511.856 51.0139 512.234 50.8735 512.525C50.7398 512.801 50.6156 513.037 50.5119 513.175C50.3254 513.424 50.0519 513.656 49.7464 513.871C49.4356 514.09 49.0647 514.308 48.66 514.522C47.8505 514.95 46.875 515.375 45.9063 515.761C43.9681 516.534 42.0163 517.163 41.3806 517.354C40.4121 517.644 37.6079 518.79 36.3053 519.336L36.3042 519.337C31.84 521.197 27.4978 523.803 25.7775 525.032C25.0262 525.568 24.345 526.252 23.6242 526.975C23.5458 527.054 23.4669 527.133 23.3873 527.213C22.5921 528.008 21.7406 528.831 20.7441 529.429C20.0757 529.83 19.1696 530.311 18.2131 530.818C17.8097 531.033 17.3972 531.252 16.9899 531.471C15.5943 532.224 14.2568 532.986 13.4208 533.643C11.6884 535.004 9.47548 537.099 8.38689 538.55C7.89897 539.201 7.72359 539.685 7.54806 540.17L7.51929 540.249C7.32212 540.791 7.10394 541.34 6.46544 541.979C5.92065 542.523 5.15932 543.118 4.37853 543.727L4.18548 543.878C3.32369 544.552 2.44079 545.253 1.71544 545.979C1.09508 546.599 0.622155 547.936 0.780514 549.318C0.934113 550.659 1.67556 551.984 3.41739 552.659C5.24454 553.366 7.458 553.434 9.59935 553.04C11.7398 552.645 13.7586 551.796 15.1869 550.725C16.6424 549.633 17.5199 548.636 18.2418 547.76C18.3304 547.652 18.4176 547.546 18.504 547.44C19.1106 546.696 19.6783 546 20.4523 545.464C21.3061 544.873 22.6866 544.169 24.0659 543.614C25.4283 543.066 26.8819 542.625 27.8619 542.625C28.0318 542.625 28.1901 542.711 28.2821 542.854C28.3742 542.997 28.3875 543.177 28.3173 543.331C27.1092 545.998 24.35 552.29 22.9575 556.169C21.4175 560.459 18.9933 570.233 17.6739 575.553C17.5034 576.241 17.3513 576.854 17.222 577.371C16.0973 581.87 14.1002 590.233 13.1031 595.469C12.0997 600.736 10.8591 607.693 10.7363 610.15C10.7199 610.477 10.6817 610.885 10.638 611.351C10.504 612.779 10.3191 614.75 10.5608 616.591C10.7194 617.8 11.0568 618.878 11.6681 619.661C12.2622 620.422 13.1459 620.944 14.5077 621C17.2278 621.114 19.0249 619.316 20.2079 617.223C21.2986 615.293 21.7965 613.229 21.9666 612.523C21.9805 612.466 21.9922 612.417 22.0018 612.379C22.0783 612.073 22.2632 611.047 22.5006 609.714L22.5656 609.349C22.7862 608.11 23.0407 606.68 23.2823 605.407C23.5588 603.949 23.8269 602.649 24.0126 602.092C24.1582 601.655 24.5135 600.462 25.0006 598.828C25.7326 596.37 26.7624 592.914 27.8254 589.522C28.4828 587.425 29.1509 585.359 29.7644 583.586C30.8893 580.337 31.6722 578.113 32.2369 576.592C32.7972 575.082 33.1541 574.231 33.4278 573.752C33.6081 573.436 33.8838 573.206 34.2444 573.177C34.5768 573.151 34.8534 573.309 35.0349 573.467C35.2214 573.631 35.3707 573.847 35.4546 574.069C35.5282 574.264 35.6014 574.605 35.4029 574.902C35.3028 575.053 34.9106 575.77 34.4603 576.795C34.0141 577.81 33.5264 579.093 33.2233 580.366C33.006 581.279 32.4151 583.461 31.7007 586.1C31.3806 587.282 31.0357 588.556 30.6885 589.848C29.5619 594.042 28.4108 598.43 27.9772 600.598C27.544 602.764 27.3584 604.341 27.187 605.898L27.1506 606.23C26.9919 607.681 26.8306 609.155 26.4788 611.089C26.2717 612.229 25.9745 613.569 25.6565 615.004C25.4002 616.16 25.1304 617.377 24.8832 618.599C24.3221 621.374 23.8619 624.24 23.8619 626.75C23.8619 629.294 24.1442 631.325 24.8119 632.704C25.1417 633.385 25.5572 633.89 26.0641 634.226C26.5685 634.561 27.1957 634.75 27.9869 634.75C31.2164 634.75 32.7098 632.571 33.1778 631.752C33.653 630.92 35.1351 627.467 35.7493 624.764C36.0509 623.437 36.4716 621.162 36.8841 618.932L36.9171 618.753C37.3372 616.481 37.747 614.273 38.0018 613.254C38.1103 612.82 38.3896 611.62 38.775 609.965C39.2757 607.813 39.9556 604.893 40.6724 601.884C41.935 596.584 43.3166 590.992 44.0141 588.962C44.3567 587.966 44.6839 586.976 44.9925 586.041L45.0564 585.847C45.3409 584.984 45.6094 584.17 45.8578 583.447C46.1241 582.671 46.3706 581.991 46.592 581.461C46.7027 581.197 46.8103 580.962 46.9141 580.769C47.0134 580.584 47.1271 580.403 47.2584 580.271C47.7632 579.767 48.4805 579.797 48.9605 580.037C49.2075 580.161 49.4415 580.361 49.5742 580.637C49.7148 580.929 49.7227 581.271 49.5591 581.599L49.5584 581.6C49.5573 581.602 49.555 581.607 49.5515 581.616C49.5464 581.628 49.54 581.643 49.5321 581.663C49.5163 581.704 49.4966 581.757 49.4729 581.823C49.4257 581.955 49.3657 582.132 49.2942 582.349C49.1514 582.784 48.9655 583.375 48.7482 584.084C48.3138 585.502 47.7561 587.389 47.171 589.449C45.9993 593.573 44.723 598.369 44.1022 601.473C43.5369 604.299 43.203 606.585 42.9002 608.658C42.8698 608.866 42.8397 609.072 42.8097 609.276C42.4814 611.511 42.1658 613.534 41.5962 615.75C41.5222 616.037 41.4365 616.36 41.3428 616.714C40.7159 619.078 39.7337 622.782 39.5329 626.155C39.4174 628.095 39.5665 629.86 40.1456 631.173C40.7081 632.449 41.6756 633.308 43.2968 633.504C44.8908 633.696 46.256 632.969 47.4554 631.607C48.6622 630.236 49.6522 628.271 50.4565 626.136C51.7287 622.76 52.4934 619.091 52.9491 616.904C53.0702 616.323 53.1695 615.847 53.2507 615.508C53.6146 613.992 54.412 611.294 55.1923 608.653L55.2887 608.327C56.1049 605.564 56.8779 602.934 57.1226 601.772C57.6221 599.4 59.3778 591.87 60.3897 588.961C60.56 588.471 60.7461 587.896 60.9436 587.286C61.3089 586.157 61.7135 584.907 62.1311 583.848C62.4543 583.028 62.8036 582.272 63.1764 581.737C63.3621 581.471 63.5751 581.228 63.821 581.065C64.0776 580.896 64.397 580.798 64.7435 580.893C65.1835 581.013 65.3626 581.401 65.43 581.714C65.499 582.033 65.4908 582.422 65.4469 582.826C65.358 583.642 65.1015 584.688 64.7954 585.748C64.4874 586.814 64.1212 587.921 63.8025 588.865C63.7366 589.06 63.6731 589.247 63.6127 589.426C63.3772 590.12 63.1887 590.676 63.0927 591.012C62.8511 591.858 62.5436 593.515 62.2287 595.231L62.1932 595.424C61.8943 597.054 61.5918 598.704 61.345 599.629C61.2651 599.928 61.1432 600.366 60.9927 600.906C60.6206 602.24 60.0742 604.199 59.5583 606.247C58.9519 608.654 58.4011 611.133 58.2344 612.8C58.0463 614.681 58.0008 616.353 58.4707 617.606C58.6999 618.217 59.0487 618.718 59.5631 619.098C60.0806 619.48 60.7969 619.761 61.7953 619.878C62.6218 619.976 63.4344 619.544 64.2417 618.645C65.0447 617.75 65.7657 616.472 66.3908 615.091C67.0129 613.716 67.526 612.27 67.9241 611.06C68.0932 610.547 68.2447 610.065 68.3752 609.649C68.5449 609.109 68.6791 608.683 68.7702 608.445C69.0763 607.65 70.321 603.886 71.5421 600.082C72.7663 596.268 73.9423 592.489 74.123 591.645C74.3198 590.727 73.1475 588.314 74.0812 585.824C75.0189 583.324 75.3393 580.677 76.0397 579.276C76.6986 577.958 77.6817 574.933 78.6479 571.369C79.6182 567.791 80.5574 563.723 81.119 560.416C81.4022 558.749 81.7636 557.028 82.1306 555.299L82.1947 554.997C82.5414 553.363 82.8895 551.724 83.1823 550.099C83.8051 546.643 82.9245 543.342 82.4987 540.362C82.4566 540.067 82.4153 539.78 82.375 539.5C81.5808 533.983 82.4334 531.228 84.2754 526.808C84.9727 525.134 85.712 523.232 86.4564 521.318C87.6918 518.14 88.9413 514.927 90.0366 512.658C90.115 512.495 90.2023 512.313 90.2993 512.11C92.3799 507.763 97.4102 495.867 115.589 471.947C134.551 446.997 146.513 418.941 149.746 402.28C152.345 388.884 154.495 376.704 155.244 372.291C155.254 372.231 155.275 372.174 155.306 372.121C166.615 352.936 175.232 336.871 180.276 318.989C180.336 318.777 180.527 318.629 180.747 318.625C180.968 318.621 181.165 318.761 181.233 318.971C182.549 323.024 184.708 328.638 186.919 334.388C187.325 335.442 187.732 336.501 188.136 337.555C190.736 344.342 193.213 350.968 194.222 355.004C195.221 359.001 195.84 361.102 196.455 362.73C196.916 363.952 197.374 364.908 197.999 366.213C198.208 366.649 198.436 367.124 198.689 367.662L198.877 368.06C200.809 372.164 202.741 376.264 203.235 381.326C203.338 382.383 203.516 383.724 203.737 385.306C203.794 385.715 203.853 386.139 203.915 386.578C204.758 392.581 205.987 401.334 205.987 410.5C205.987 416.675 204.845 422.413 203.734 428.003L203.696 428.191C202.57 433.855 201.488 439.365 201.612 445.114C201.692 448.854 201.799 451.908 201.906 454.479C201.979 456.246 202.054 457.789 202.12 459.172C202.257 462.017 202.362 464.188 202.362 466.25C202.362 467.453 202.03 468.889 201.525 470.591C201.227 471.597 200.859 472.724 200.452 473.972C200.17 474.837 199.868 475.761 199.557 476.744C198.031 481.571 196.228 487.979 195.108 496.565C194.247 503.164 193.404 508.029 192.65 512.113C192.472 513.074 192.3 513.992 192.133 514.877C191.592 517.756 191.114 520.299 190.727 522.947C190.414 525.085 190.162 527.289 189.986 529.785C189.985 529.8 189.983 529.815 189.98 529.83C187.109 547.584 181.324 588.473 181.125 610.13C180.947 629.404 182.031 651.365 182.764 666.2C183.062 672.239 183.302 677.098 183.375 680.113C183.624 690.462 184.996 704.795 188.483 717.745C188.814 718.975 189.171 720.223 189.547 721.482C190.442 724.482 191.44 727.545 192.44 730.592L192.678 731.318C193.597 734.119 194.511 736.902 195.335 739.598C197.123 745.444 198.51 750.932 198.625 755.362C198.743 759.931 198.054 762.847 197.274 766.144C197.22 766.372 197.166 766.601 197.112 766.833C196.273 770.405 195.374 774.617 195.25 782.008C195 796.863 195.874 812.097 196.124 816.094C196.142 816.39 196.113 816.843 196.054 817.406C195.994 817.979 195.899 818.699 195.776 819.546C195.528 821.241 195.164 823.464 194.725 826.079C194.562 827.051 194.389 828.077 194.208 829.152C193.413 833.862 192.463 839.493 191.541 845.482C189.274 860.21 187.191 877.029 187.998 887.587C189.394 905.84 194.486 937.866 197.714 958.173C198.24 961.479 198.716 964.473 199.119 967.048C199.542 969.75 200.033 972.766 200.558 975.996C201.818 983.735 203.277 992.703 204.48 1001.53C206.182 1014.02 207.378 1026.29 206.748 1034.41C206.123 1042.48 205.685 1046.77 205.403 1049.49L205.382 1049.7C205.116 1052.27 205 1053.39 205 1055.12C205 1056.77 205.372 1058.82 205.782 1061.08L205.867 1061.55C206.303 1063.97 206.75 1066.57 206.75 1069C206.75 1070.79 206.839 1072.4 206.917 1073.79C206.941 1074.23 206.965 1074.66 206.984 1075.05C207.023 1075.88 207.043 1076.61 207.013 1077.23C206.983 1077.85 206.901 1078.4 206.705 1078.83C206.437 1079.42 206.018 1080.01 205.572 1080.65C205.457 1080.81 205.34 1080.97 205.224 1081.14C204.64 1081.99 204.006 1082.99 203.463 1084.31C203.031 1085.37 202.852 1086.34 202.674 1087.3C202.629 1087.54 202.585 1087.78 202.537 1088.02C202.297 1089.22 201.964 1090.41 201.011 1091.57C200.513 1092.17 199.649 1092.85 198.635 1093.61C198.4 1093.78 198.154 1093.97 197.899 1094.15C194.947 1096.34 190.814 1099.41 188.054 1104.01C186.031 1107.38 185.082 1110.75 184.73 1113.56C184.377 1116.39 184.629 1118.62 184.977 1119.72C185.155 1120.29 185.325 1120.64 185.47 1120.86C185.611 1121.08 185.736 1121.19 185.851 1121.29C185.948 1121.37 186.158 1121.52 186.295 1121.74C186.448 1121.98 186.5 1122.27 186.5 1122.62C186.5 1123.14 186.743 1124.01 187.231 1124.76C187.718 1125.5 188.366 1126 189.125 1126C190.1 1126 190.83 1125.91 191.316 1125.77C191.559 1125.7 191.719 1125.62 191.813 1125.56C191.866 1125.52 191.887 1125.5 191.893 1125.49C191.957 1125.26 192.175 1125.11 192.413 1125.13C192.656 1125.15 192.851 1125.34 192.873 1125.58C192.953 1126.46 193.128 1127.24 193.422 1127.81C193.71 1128.38 194.083 1128.69 194.571 1128.76C195.841 1128.94 196.982 1128.9 197.843 1128.65C198.7 1128.39 199.213 1127.95 199.397 1127.35C199.465 1127.13 199.677 1126.99 199.908 1127C200.14 1127.02 200.33 1127.19 200.368 1127.42C200.511 1128.28 200.829 1129.04 201.382 1129.59C201.925 1130.12 202.745 1130.5 204 1130.5C205.218 1130.5 206.045 1130.11 206.616 1129.55C207.198 1128.98 207.551 1128.2 207.739 1127.39C207.798 1127.13 208.046 1126.96 208.305 1127.01C208.565 1127.05 208.748 1127.28 208.724 1127.55C208.631 1128.57 208.804 1130.11 209.599 1131.37C210.372 1132.6 211.765 1133.62 214.25 1133.62C215.827 1133.62 216.903 1133.26 217.649 1132.75C218.396 1132.24 218.854 1131.56 219.147 1130.84C219.442 1130.12 219.564 1129.39 219.643 1128.79C219.654 1128.7 219.665 1128.61 219.675 1128.53C219.7 1128.33 219.722 1128.15 219.747 1128.01C219.765 1127.91 219.79 1127.79 219.828 1127.69C219.856 1127.61 219.937 1127.41 220.151 1127.3C220.503 1127.13 220.999 1127.06 221.406 1127.06C221.618 1127.06 221.834 1127.08 222.025 1127.12C222.191 1127.16 222.432 1127.23 222.603 1127.4C222.725 1127.52 222.761 1127.67 222.774 1127.72C222.791 1127.8 222.799 1127.88 222.803 1127.95C222.81 1128.1 222.807 1128.27 222.802 1128.46C222.792 1128.84 222.774 1129.32 222.796 1129.84C222.842 1130.9 223.058 1131.98 223.728 1132.65C224.5 1133.42 225.439 1134.12 226.794 1134.61C228.154 1135.1 229.96 1135.37 232.475 1135.25C238.69 1134.95 242.404 1131.31 244.632 1127.54C246.54 1124.31 247.326 1121.05 247.644 1119.73C247.699 1119.5 247.74 1119.33 247.771 1119.23C247.823 1119.06 247.91 1118.83 248.016 1118.56C248.729 1116.71 250.282 1112.68 247.565 1107.87C247.132 1107.11 246.736 1106.43 246.378 1105.81C245.778 1104.78 245.288 1103.94 244.927 1103.21C244.634 1102.62 244.412 1102.08 244.273 1101.54C243.914 1100.14 244.152 1098.86 244.91 1096.94C245.323 1095.89 245.814 1094.85 246.312 1093.8L246.441 1093.53C246.898 1092.57 247.357 1091.61 247.773 1090.62C248.683 1088.46 249.375 1086.22 249.375 1083.75C249.375 1081.24 249.04 1079.82 248.721 1078.65C248.689 1078.53 248.656 1078.41 248.624 1078.3C248.334 1077.25 248.066 1076.29 248.125 1074.85C248.154 1074.15 248.229 1073.01 248.323 1071.57C248.638 1066.76 249.163 1058.7 248.876 1053.53C248.785 1051.9 248.336 1049.85 247.702 1047.45C247.386 1046.25 247.026 1044.97 246.646 1043.62C246.266 1042.26 245.865 1040.84 245.47 1039.35C243.888 1033.4 242.374 1026.44 242.5 1019.12C242.751 1004.58 246.881 970.902 254.274 948.096C261.62 925.437 261.249 906.262 259.876 889.29C259.375 883.094 257.991 876.29 256.578 869.34C256.06 866.796 255.539 864.232 255.055 861.671C253.259 852.156 251.989 842.697 253.509 834.657C255.016 826.681 257.214 820.183 259.183 814.477L259.41 813.823C261.289 808.383 262.911 803.69 263.504 799.061C264.125 794.22 264.281 786.347 264.422 779.178L264.423 779.133C264.493 775.574 264.559 772.193 264.678 769.466C264.738 768.098 264.811 766.888 264.905 765.899C264.998 764.921 265.115 764.126 265.271 763.606C265.575 762.593 266.069 760.135 266.638 756.913C268.103 748.62 270.017 735.524 270.376 729.968C270.548 727.306 271.525 722.08 272.827 715.896C274.132 709.699 275.773 702.497 277.287 695.855L277.288 695.848L277.592 694.514C278.926 688.658 280.133 683.336 280.885 679.65C281.108 678.559 281.357 677.364 281.626 676.072C284.383 662.836 289.238 639.531 289.125 616.127V603.034C289.125 602.891 289.186 602.754 289.294 602.659C289.401 602.565 289.544 602.52 289.687 602.538C290.8 602.677 291.986 602.75 293.25 602.75C294.513 602.75 295.699 602.677 296.813 602.538C296.955 602.52 297.098 602.565 297.206 602.659C297.313 602.754 297.375 602.891 297.375 603.034V616.127C297.261 639.531 302.117 662.836 304.874 676.072C305.143 677.364 305.392 678.559 305.615 679.65C306.367 683.336 307.573 688.658 308.907 694.514L309.211 695.848L309.213 695.855C310.726 702.497 312.367 709.699 313.672 715.896C314.975 722.08 315.952 727.306 316.124 729.968C316.482 735.524 318.397 748.62 319.861 756.913C320.43 760.135 320.925 762.593 321.229 763.606C321.385 764.126 321.501 764.921 321.595 765.899C321.689 766.888 321.762 768.098 321.822 769.466C321.941 772.193 322.007 775.574 322.077 779.133L322.078 779.178C322.219 786.347 322.375 794.22 322.996 799.061C323.589 803.69 325.211 808.383 327.09 813.823L327.316 814.477C329.286 820.183 331.484 826.681 332.991 834.657C334.51 842.697 333.241 852.156 331.444 861.671C330.961 864.232 330.439 866.796 329.922 869.34C328.508 876.29 327.124 883.094 326.623 889.29C325.25 906.262 324.88 925.437 332.225 948.096C339.619 970.902 343.749 1004.58 344 1019.12C344.126 1026.44 342.612 1033.4 341.03 1039.35C340.634 1040.84 340.234 1042.26 339.854 1043.62L339.829 1043.7C339.458 1045.03 339.107 1046.28 338.798 1047.45C338.163 1049.85 337.715 1051.9 337.624 1053.53C337.336 1058.7 337.862 1066.76 338.177 1071.57C338.27 1073.01 338.345 1074.15 338.374 1074.85C338.434 1076.29 338.166 1077.25 337.875 1078.3C337.843 1078.41 337.811 1078.53 337.779 1078.65C337.459 1079.82 337.125 1081.24 337.125 1083.75C337.125 1086.22 337.816 1088.46 338.726 1090.62C339.143 1091.61 339.601 1092.57 340.059 1093.53L340.188 1093.8C340.685 1094.85 341.177 1095.89 341.59 1096.94C342.348 1098.86 342.586 1100.14 342.226 1101.54C342.088 1102.08 341.866 1102.62 341.573 1103.21C341.211 1103.94 340.722 1104.78 340.121 1105.81C339.764 1106.43 339.367 1107.11 338.935 1107.87C336.217 1112.68 337.771 1116.71 338.484 1118.56C338.589 1118.83 338.677 1119.06 338.729 1119.23C338.759 1119.33 338.801 1119.5 338.856 1119.73C339.174 1121.05 339.96 1124.31 341.868 1127.54C344.096 1131.31 347.809 1134.95 354.024 1135.25C356.539 1135.37 358.346 1135.1 359.706 1134.61C361.06 1134.12 362 1133.42 362.771 1132.65C363.442 1131.98 363.657 1130.9 363.703 1129.84C363.723 1129.39 363.711 1128.97 363.701 1128.61L363.697 1128.46C363.692 1128.27 363.689 1128.1 363.697 1127.95C363.701 1127.88 363.709 1127.8 363.726 1127.72C363.739 1127.67 363.775 1127.52 363.896 1127.4C364.068 1127.23 364.309 1127.16 364.475 1127.12C364.666 1127.08 364.882 1127.06 365.094 1127.06C365.5 1127.06 365.997 1127.13 366.348 1127.3C366.563 1127.41 366.644 1127.61 366.672 1127.69C366.71 1127.79 366.734 1127.91 366.752 1128.01C366.788 1128.21 366.819 1128.5 366.857 1128.79C366.936 1129.39 367.058 1130.12 367.353 1130.84C367.646 1131.56 368.104 1132.24 368.85 1132.75C369.596 1133.26 370.672 1133.62 372.25 1133.62C374.735 1133.62 376.128 1132.6 376.901 1131.37C377.696 1130.11 377.869 1128.57 377.775 1127.55C377.752 1127.28 377.935 1127.05 378.194 1127.01C378.454 1126.96 378.701 1127.13 378.76 1127.39C378.949 1128.2 379.302 1128.98 379.884 1129.55C380.455 1130.11 381.282 1130.5 382.5 1130.5C383.733 1130.5 384.551 1130.12 385.099 1129.58C385.657 1129.03 385.982 1128.26 386.132 1127.41C386.173 1127.19 386.363 1127.02 386.594 1127C386.824 1126.99 387.035 1127.13 387.103 1127.35C387.286 1127.95 387.8 1128.39 388.657 1128.65C389.518 1128.9 390.659 1128.94 391.929 1128.76C392.393 1128.69 392.766 1128.38 393.061 1127.8C393.359 1127.22 393.541 1126.43 393.627 1125.57C393.652 1125.33 393.847 1125.14 394.089 1125.13C394.327 1125.11 394.543 1125.26 394.606 1125.49C394.613 1125.5 394.633 1125.52 394.686 1125.56C394.781 1125.62 394.941 1125.7 395.184 1125.77C395.669 1125.91 396.4 1126 397.375 1126C398.134 1126 398.782 1125.5 399.269 1124.76C399.756 1124.01 400 1123.14 400 1122.62C400 1122.27 400.052 1121.98 400.205 1121.74C400.322 1121.55 400.492 1121.41 400.6 1121.33C400.618 1121.31 400.634 1121.3 400.648 1121.29C400.764 1121.19 400.889 1121.08 401.029 1120.86C401.174 1120.64 401.345 1120.29 401.523 1119.72C401.87 1118.62 402.123 1116.39 401.769 1113.56C401.417 1110.75 400.468 1107.38 398.446 1104.01C395.686 1099.41 391.552 1096.34 388.6 1094.15C388.346 1093.97 388.1 1093.78 387.865 1093.61C386.851 1092.85 385.987 1092.17 385.489 1091.57C384.535 1090.41 384.203 1089.22 383.963 1088.02C383.915 1087.78 383.87 1087.54 383.826 1087.3C383.647 1086.34 383.469 1085.37 383.037 1084.31C382.494 1082.99 381.86 1081.99 381.276 1081.14C381.159 1080.97 381.043 1080.81 380.928 1080.65C380.481 1080.01 380.062 1079.42 379.795 1078.83C379.598 1078.4 379.517 1077.85 379.487 1077.23C379.456 1076.61 379.477 1075.88 379.516 1075.05C379.535 1074.66 379.558 1074.23 379.583 1073.79C379.661 1072.4 379.75 1070.79 379.75 1069C379.75 1066.57 380.196 1063.97 380.633 1061.55L380.718 1061.08C381.128 1058.82 381.5 1056.77 381.5 1055.12C381.5 1053.39 381.384 1052.27 381.118 1049.7L381.096 1049.49C380.815 1046.77 380.377 1042.48 379.751 1034.41C379.122 1026.29 380.317 1014.02 382.02 1001.53C383.222 992.703 384.682 983.736 385.941 975.997C386.467 972.767 386.958 969.75 387.381 967.048C387.784 964.473 388.26 961.478 388.785 958.172C392.014 937.865 397.105 905.84 398.501 887.587C399.309 877.029 397.226 860.21 394.959 845.482C394.037 839.495 393.087 833.866 392.293 829.156C392.111 828.08 391.938 827.052 391.774 826.079C391.336 823.464 390.971 821.241 390.724 819.546C390.6 818.699 390.506 817.979 390.445 817.406C390.386 816.843 390.357 816.39 390.376 816.094C390.626 812.097 391.5 796.863 391.25 782.008C391.126 774.617 390.227 770.405 389.388 766.833C389.334 766.601 389.279 766.372 389.226 766.144C388.446 762.847 387.756 759.931 387.875 755.362C387.99 750.932 389.377 745.444 391.165 739.598C391.989 736.903 392.902 734.12 393.821 731.319L394.06 730.592C395.06 727.545 396.058 724.482 396.953 721.482C397.329 720.223 397.686 718.975 398.017 717.745C401.504 704.795 402.876 690.462 403.125 680.113C403.198 677.098 403.438 672.24 403.736 666.2C404.468 651.365 405.553 629.404 405.375 610.13C405.175 588.473 399.391 547.584 396.519 529.83C396.517 529.815 396.515 529.8 396.514 529.785C396.338 527.289 396.085 525.085 395.773 522.947C395.386 520.299 394.908 517.756 394.366 514.877C394.2 513.992 394.027 513.074 393.85 512.113C393.095 508.029 392.253 503.164 391.392 496.565C390.272 487.979 388.468 481.571 386.942 476.744C386.631 475.761 386.33 474.837 386.048 473.972C385.64 472.723 385.273 471.597 384.975 470.591C384.47 468.889 384.138 467.453 384.138 466.25C384.138 464.189 384.242 462.017 384.379 459.173C384.446 457.789 384.52 456.246 384.594 454.479C384.701 451.908 384.807 448.854 384.888 445.114C385.012 439.365 383.93 433.855 382.804 428.191L382.766 428.003C381.654 422.413 380.513 416.675 380.513 410.5C380.513 401.334 381.742 392.581 382.585 386.579C382.646 386.14 382.706 385.715 382.763 385.306C382.983 383.724 383.162 382.383 383.265 381.326C383.759 376.264 385.69 372.164 387.623 368.06L387.636 368.032L387.81 367.662C388.063 367.124 388.291 366.649 388.501 366.212C389.126 364.908 389.584 363.952 390.045 362.73C390.659 361.102 391.279 359.001 392.278 355.004C393.287 350.968 395.764 344.342 398.364 337.555C398.768 336.501 399.175 335.442 399.581 334.388C401.792 328.638 403.951 323.024 405.267 318.971C405.335 318.761 405.532 318.621 405.752 318.625C405.973 318.629 406.164 318.777 406.224 318.989C411.267 336.871 419.885 352.936 431.194 372.121C431.212 372.152 431.226 372.184 431.237 372.218C431.245 372.242 431.251 372.266 431.256 372.291C432.005 376.704 434.154 388.884 436.754 402.28C439.987 418.941 451.949 446.997 470.911 471.947C489.089 495.867 495.62 509.513 497.7 513.86C497.797 514.063 497.885 514.245 497.963 514.408C499.058 516.677 500.308 519.891 501.543 523.068C502.288 524.983 503.027 526.884 503.724 528.558C505.566 532.978 505.176 535.692 504.382 541.209C504.341 541.489 504.3 541.776 504.258 542.071C503.832 545.051 504.195 548.393 504.817 551.849C505.11 553.473 505.458 555.113 505.805 556.745L505.869 557.049C506.236 558.778 506.598 560.499 506.881 562.166C507.442 565.473 507.53 566.921 508.5 570.5C509.466 574.064 509.61 577.235 510.269 578.553C510.97 579.954 510.711 582.72 511.649 585.221C512.583 587.71 512.43 590.477 512.627 591.395C512.807 592.239 513.983 596.018 515.208 599.832C516.429 603.636 517.673 607.4 517.979 608.195C518.071 608.432 518.205 608.859 518.374 609.398C518.505 609.814 518.656 610.296 518.826 610.81C519.224 612.02 519.737 613.466 520.359 614.841C520.984 616.222 521.705 617.5 522.508 618.395C523.315 619.294 524.128 619.726 524.954 619.628C525.953 619.511 526.669 619.23 527.187 618.848C527.701 618.468 528.05 617.967 528.279 617.356C528.749 616.103 528.703 614.431 528.515 612.55C528.349 610.883 527.798 608.404 527.191 605.997C526.676 603.949 526.129 601.99 525.757 600.655C525.607 600.116 525.485 599.678 525.405 599.379C525.158 598.453 524.855 596.8 524.556 595.169L524.521 594.981C524.206 593.265 523.899 591.608 523.657 590.762C523.561 590.427 523.373 589.871 523.138 589.178C523.077 588.999 523.013 588.811 522.947 588.615C522.629 587.671 522.262 586.564 521.954 585.498C521.648 584.438 521.392 583.392 521.303 582.576C521.259 582.172 521.251 581.783 521.32 581.464C521.387 581.151 521.566 580.763 522.006 580.643C522.353 580.548 522.672 580.646 522.929 580.815C523.175 580.978 523.388 581.221 523.573 581.487C523.946 582.022 524.295 582.778 524.619 583.598C525.036 584.657 525.441 585.908 525.806 587.036C526.004 587.647 526.19 588.221 526.36 588.711C527.372 591.62 529.128 599.15 529.627 601.522C529.872 602.684 530.645 605.314 531.461 608.077L531.557 608.403C532.338 611.044 533.135 613.742 533.499 615.258C533.58 615.597 533.68 616.073 533.801 616.654C534.256 618.841 535.021 622.51 536.293 625.886C537.098 628.021 538.087 629.986 539.294 631.357C540.494 632.719 541.859 633.446 543.453 633.254C545.074 633.058 546.042 632.199 546.604 630.923C547.183 629.61 547.332 627.845 547.217 625.905C547.016 622.532 546.034 618.827 545.407 616.463C545.313 616.11 545.228 615.787 545.154 615.5C544.584 613.284 544.268 611.261 543.94 609.026C543.91 608.822 543.88 608.615 543.85 608.407C543.547 606.335 543.213 604.049 542.648 601.223C542.027 598.119 540.75 593.323 539.579 589.199C538.994 587.139 538.436 585.252 538.002 583.834C537.784 583.125 537.598 582.534 537.456 582.099C537.384 581.882 537.324 581.705 537.277 581.573C537.253 581.507 537.233 581.454 537.218 581.413C537.21 581.393 537.203 581.378 537.198 581.366C537.193 581.353 537.191 581.349 537.191 581.349C537.027 581.021 537.035 580.679 537.176 580.387C537.308 580.111 537.542 579.911 537.789 579.787C538.269 579.547 538.987 579.517 539.491 580.021C539.623 580.153 539.736 580.334 539.836 580.519C539.889 580.618 539.943 580.728 539.998 580.847C540.051 580.961 540.104 581.082 540.158 581.211C540.379 581.741 540.626 582.421 540.892 583.197C541.056 583.673 541.228 584.189 541.408 584.732C541.521 585.075 541.638 585.429 541.757 585.791C542.066 586.726 542.393 587.716 542.736 588.712C543.433 590.742 544.815 596.334 546.077 601.634C546.795 604.645 547.475 607.568 547.976 609.719C548.361 611.372 548.639 612.57 548.748 613.004C549.003 614.023 549.413 616.231 549.833 618.503C550.256 620.79 550.691 623.152 551 624.514C551.615 627.217 553.097 630.67 553.572 631.502C554.04 632.321 555.533 634.5 558.763 634.5C559.554 634.5 560.181 634.311 560.686 633.976C561.193 633.64 561.608 633.135 561.938 632.454C562.606 631.075 562.888 629.044 562.888 626.5C562.888 623.99 562.428 621.124 561.867 618.349C561.619 617.127 561.35 615.91 561.093 614.754C560.775 613.32 560.478 611.979 560.271 610.839C559.919 608.904 559.758 607.431 559.599 605.98L559.563 605.648C559.391 604.091 559.206 602.514 558.773 600.348C558.339 598.18 557.188 593.792 556.061 589.598C555.714 588.308 555.37 587.035 555.05 585.854C554.335 583.214 553.744 581.029 553.526 580.116C553.223 578.843 552.736 577.56 552.289 576.545C551.839 575.52 551.447 574.803 551.347 574.652C551.148 574.355 551.222 574.014 551.295 573.819C551.379 573.597 551.528 573.381 551.715 573.217C551.896 573.059 552.173 572.901 552.505 572.927C552.866 572.956 553.142 573.186 553.322 573.502C553.596 573.981 553.953 574.832 554.513 576.342C555.078 577.863 555.86 580.087 556.985 583.336C557.599 585.109 558.267 587.175 558.924 589.272C559.987 592.663 561.017 596.118 561.749 598.575C562.236 600.211 562.591 601.405 562.737 601.842C562.923 602.399 563.191 603.699 563.467 605.157C563.733 606.557 564.014 608.147 564.249 609.464C564.487 610.797 564.671 611.823 564.748 612.129C564.758 612.167 564.769 612.216 564.783 612.274C564.953 612.979 565.451 615.043 566.542 616.973C567.725 619.066 569.522 620.864 572.242 620.75C573.604 620.694 574.487 620.172 575.082 619.411C575.693 618.628 576.03 617.55 576.189 616.341C576.431 614.5 576.246 612.529 576.112 611.101C576.068 610.635 576.03 610.227 576.013 609.9C575.891 607.443 574.65 600.486 573.647 595.219C572.649 589.983 570.652 581.62 569.528 577.121C569.398 576.604 569.246 575.991 569.076 575.304C567.757 569.984 565.332 560.209 563.792 555.919C562.4 552.04 559.641 545.748 558.432 543.081C558.362 542.927 558.376 542.747 558.468 542.604C558.56 542.461 558.718 542.375 558.888 542.375C559.868 542.375 561.321 542.816 562.684 543.364C564.063 543.919 565.444 544.623 566.297 545.214C567.072 545.75 567.639 546.446 568.246 547.19C568.332 547.296 568.419 547.403 568.508 547.51C569.23 548.386 570.107 549.383 571.563 550.475C572.991 551.546 575.01 552.395 577.15 552.79C579.292 553.184 581.505 553.116 583.332 552.409C585.074 551.734 585.816 550.409 585.969 549.068C586.128 547.686 585.655 546.349 585.034 545.729C584.309 545.003 583.426 544.302 582.564 543.628L582.371 543.477C581.59 542.868 580.829 542.273 580.284 541.729C579.646 541.09 579.428 540.541 579.23 539.999L579.202 539.92C579.026 539.435 578.851 538.951 578.363 538.3C577.274 536.849 575.061 534.754 573.329 533.393C572.493 532.736 571.155 531.974 569.76 531.221C569.352 531.002 568.94 530.783 568.536 530.568C567.58 530.061 566.674 529.58 566.006 529.179C565.009 528.581 564.158 527.758 563.362 526.963C563.283 526.884 563.204 526.804 563.126 526.726C562.405 526.002 561.724 525.319 560.972 524.782C559.252 523.553 554.91 520.947 550.445 519.087L550.444 519.086C549.142 518.54 546.338 517.394 545.369 517.104C544.733 516.913 542.782 516.284 540.843 515.511C539.875 515.125 538.899 514.7 538.09 514.272C537.685 514.058 537.314 513.84 537.003 513.621C536.698 513.406 536.424 513.174 536.238 512.925C536.173 512.838 536.1 512.714 536.021 512.565C535.975 512.476 535.926 512.378 535.876 512.275C535.736 511.984 535.566 511.606 535.37 511.153C534.978 510.245 534.477 509.019 533.896 507.553C532.734 504.621 535.248 502.715 533.669 498.439C530.512 489.889 526.977 479.848 524.91 473.147C523.018 467.012 521.39 458.532 519.506 448.721C519.326 447.785 519.144 446.836 518.959 445.876C516.834 434.845 514.338 422.3 510.781 409.509C504.258 386.048 498.266 374.992 493.077 365.416C492.611 364.555 492.151 363.706 491.697 362.861C490.456 360.548 489.378 358.652 488.427 356.981C485.185 351.28 483.431 348.195 481.773 340.1C480.718 334.949 478.479 328.072 476.181 321.429C475.452 319.322 474.717 317.237 474.013 315.241C472.505 310.963 471.139 307.091 470.284 304.27C469.3 301.023 466.865 296.001 464.197 291.011C461.535 286.033 458.665 281.129 456.836 278.136C456.794 278.067 456.769 277.989 456.764 277.909C456.555 274.816 456.138 266.438 456.138 257.625C456.138 246.628 455.131 225.519 444.475 209.907C439.487 202.599 435.413 198.296 431.046 195.191C426.671 192.079 421.977 190.15 415.688 187.618C414.769 187.248 413.815 186.865 412.825 186.463C405.625 183.546 400.761 182.679 395.921 181.932C395.56 181.876 395.199 181.821 394.837 181.766C390.33 181.078 385.681 180.369 379.1 178.098C364.955 173.216 353.048 167.829 346.136 163.807C342.698 161.806 339.361 159.762 336.849 157.834C335.594 156.871 334.527 155.924 333.755 155.015C332.991 154.116 332.465 153.197 332.39 152.292C332.321 151.469 332.29 148.831 332.28 145.376C332.262 138.401 332.332 127.965 332.388 121.87C332.389 121.76 332.426 121.653 332.495 121.566C333.03 120.886 333.491 119.861 333.884 118.613C334.275 117.374 334.587 115.956 334.844 114.521C335.1 113.088 335.3 111.648 335.466 110.367C335.51 110.034 335.551 109.712 335.59 109.402C335.702 108.526 335.801 107.755 335.894 107.171C336.105 105.852 336.203 104.624 336.276 103.704C336.289 103.551 336.3 103.406 336.311 103.271C336.349 102.824 336.386 102.421 336.452 102.191C336.468 102.134 336.499 102.039 336.559 101.95C336.618 101.865 336.801 101.659 337.105 101.713C337.332 101.752 337.451 101.919 337.482 101.963C337.531 102.033 337.568 102.11 337.597 102.178C337.614 102.217 337.631 102.257 337.648 102.297C337.828 102.721 338.017 103.169 338.439 103.579C338.891 104.018 339.656 104.452 341.075 104.629C341.593 104.694 342.149 104.437 342.771 103.787C343.388 103.142 343.991 102.191 344.587 101.077C345.131 100.06 345.653 98.9374 346.167 97.8331L346.308 97.5311C346.862 96.3398 347.411 95.1773 347.956 94.2471C348.976 92.5084 349.869 89.6067 350.539 86.3675C351.206 83.1423 351.639 79.6378 351.763 76.7287C351.82 75.4052 351.725 74.2913 351.521 73.357C351.181 71.8082 350.538 70.7504 349.759 70.0359C348.978 69.3192 348.033 68.9209 347.048 68.7419C346.186 68.5851 345.365 69.014 344.637 69.7488C343.915 70.4773 343.372 71.4253 343.098 72.0703C343.002 72.2972 342.755 72.4201 342.516 72.3599C342.277 72.2996 342.117 72.0743 342.14 71.8289C342.535 67.57 342.971 60.6912 342.997 53.9075C343.023 47.1006 342.634 40.4674 341.412 36.6526C339.87 31.8432 335.558 22.9162 327.758 15.1992C319.967 7.49234 308.706 1 293.237 1C277.767 1 266.513 7.4923 258.729 15.1991ZM365.735 1128.04C365.735 1128.04 365.735 1128.04 365.736 1128.04C365.729 1128.03 365.73 1128.02 365.735 1128.04Z"

val pathHumanRear =
    "M262.229 14.1992C254.435 21.9161 250.13 30.8431 248.588 35.6528C247.365 39.4676 246.977 46.1007 247.003 52.9076C247.029 59.6914 247.465 66.5701 247.86 70.829C247.883 71.0744 247.723 71.2998 247.484 71.36C247.245 71.4202 246.998 71.2973 246.902 71.0704C246.628 70.4254 246.085 69.4774 245.363 68.7489C244.635 68.0141 243.814 67.5853 242.951 67.7421C241.967 67.921 241.021 68.3193 240.241 69.0361C239.462 69.7506 238.819 70.8084 238.479 72.3572C238.274 73.2915 238.18 74.4054 238.237 75.7289C238.36 78.638 238.794 82.1425 239.461 85.3677C240.131 88.6069 241.024 91.5086 242.043 93.2473C242.589 94.1774 243.137 95.3399 243.692 96.5313C243.739 96.6315 243.786 96.732 243.833 96.8327C244.346 97.9372 244.868 99.0597 245.412 100.077C246.008 101.192 246.611 102.142 247.229 102.787C247.851 103.437 248.407 103.694 248.925 103.629C250.344 103.452 251.109 103.018 251.561 102.579C251.983 102.169 252.172 101.721 252.352 101.297C252.369 101.257 252.386 101.217 252.402 101.178C252.431 101.111 252.469 101.033 252.518 100.963C252.549 100.919 252.668 100.752 252.895 100.713C253.199 100.659 253.382 100.865 253.44 100.95C253.501 101.039 253.532 101.134 253.548 101.192C253.613 101.422 253.651 101.824 253.688 102.271C253.7 102.406 253.711 102.551 253.723 102.704C253.797 103.625 253.895 104.852 254.106 106.171C254.204 106.785 254.303 107.597 254.414 108.518C254.447 108.788 254.481 109.067 254.516 109.353C254.673 110.624 254.861 112.052 255.109 113.477C255.358 114.903 255.667 116.316 256.063 117.559C256.461 118.81 256.937 119.852 257.503 120.564C257.573 120.651 257.611 120.759 257.612 120.871C257.668 126.966 257.738 137.401 257.719 144.377C257.71 147.831 257.679 150.469 257.61 151.292C257.535 152.197 257.009 153.116 256.245 154.015C255.472 154.925 254.406 155.871 253.151 156.834C250.639 158.762 247.302 160.807 243.863 162.807C236.952 166.829 225.045 172.216 210.9 177.098C204.318 179.369 199.67 180.078 195.163 180.766C194.801 180.821 194.44 180.876 194.079 180.932C189.239 181.679 184.375 182.546 177.175 185.464C176.372 185.789 175.593 186.102 174.837 186.407L174.312 186.618C168.023 189.15 163.329 191.079 158.954 194.191C154.587 197.296 150.513 201.599 145.525 208.907C134.869 224.519 133.862 245.628 133.862 256.625C133.862 265.438 133.445 273.816 133.236 276.909C133.23 276.989 133.206 277.067 133.164 277.136C131.334 280.129 128.464 285.033 125.803 290.011C123.135 295.001 120.699 300.023 119.716 303.27C119.215 304.923 118.541 306.931 117.767 309.164C117.215 310.757 116.613 312.465 115.986 314.242C113.071 322.512 109.62 332.297 108.227 339.1C106.569 347.195 104.814 350.28 101.573 355.981C100.622 357.652 99.5438 359.548 98.3026 361.862C97.8493 362.706 97.3893 363.555 96.9228 364.416C91.7336 373.992 85.742 385.048 79.2188 408.509C75.6621 421.3 73.1654 433.846 71.0405 444.876C70.8556 445.836 70.6735 446.784 70.4937 447.721C68.6102 457.532 66.9819 466.012 65.0898 472.148C63.0232 478.848 59.4882 488.89 56.3311 497.439C54.7521 501.715 53.2663 505.621 52.1042 508.553C51.5233 510.019 51.0222 511.245 50.6301 512.153C50.4343 512.606 50.264 512.984 50.1236 513.275C49.9899 513.552 49.8657 513.787 49.762 513.925C49.5755 514.174 49.302 514.407 48.9966 514.621C48.6857 514.84 48.3149 515.058 47.9101 515.272C47.1006 515.7 46.1251 516.126 45.1565 516.512C43.2182 517.284 41.2665 517.913 40.6307 518.104C39.6622 518.395 36.858 519.54 35.5554 520.086L35.5543 520.087C31.0901 521.947 26.748 524.553 25.0276 525.782C24.2764 526.319 23.5952 527.002 22.8744 527.726C22.7959 527.804 22.717 527.884 22.6374 527.963C21.8422 528.758 20.9907 529.581 19.9943 530.179C19.3258 530.58 18.4197 531.061 17.4633 531.569C17.0598 531.783 16.6473 532.002 16.24 532.221C14.8444 532.974 13.507 533.736 12.6709 534.393C10.9385 535.755 8.7256 537.849 7.63701 539.3C7.22256 539.853 7.03295 540.288 6.88138 540.693C6.85583 540.761 6.83062 540.831 6.80488 540.902C6.68029 541.247 6.54334 541.627 6.29663 542.014C6.14771 542.248 5.96107 542.483 5.71557 542.729C5.17078 543.273 4.40944 543.868 3.62865 544.478L3.4356 544.628C2.57381 545.302 1.69092 546.003 0.965568 546.729C0.566421 547.128 0.217049 547.836 0.0712355 548.689C-0.0732156 549.534 -0.00812447 550.467 0.358494 551.298C0.739196 552.161 1.45548 552.94 2.66751 553.409C4.49467 554.116 6.70812 554.185 8.84948 553.79C10.9899 553.395 13.0087 552.546 14.437 551.475C15.8925 550.384 16.77 549.386 17.4919 548.51C17.5806 548.403 17.6678 548.296 17.7542 548.19C18.3607 547.446 18.9284 546.75 19.7024 546.214C20.5562 545.623 21.9367 544.919 23.316 544.364C24.5445 543.87 25.8473 543.463 26.81 543.388L28.8122 539.759C28.9456 539.517 29.2497 539.429 29.4915 539.562C29.7333 539.696 29.8212 540 29.6878 540.242L27.5594 544.099C26.347 546.776 23.5971 553.049 22.2076 556.919C20.6677 561.209 18.2435 570.983 16.924 576.304C16.7535 576.991 16.6014 577.604 16.4721 578.121C15.3474 582.62 13.3504 590.984 12.3532 596.219C11.3498 601.486 10.1092 608.444 9.98641 610.9C9.97094 611.21 9.93318 611.611 9.89068 612.063C9.87494 612.231 9.85854 612.405 9.84239 612.584C9.78167 613.258 9.72172 614.023 9.70151 614.822C9.66074 616.434 9.78453 618.121 10.3466 619.428C10.6364 620.101 11.0358 620.657 11.5749 621.054C12.111 621.45 12.8161 621.711 13.7578 621.751C15.7597 621.834 17.2504 620.888 18.3743 619.553C20.232 617.347 20.9961 614.186 21.2169 613.273C21.2307 613.216 21.2423 613.168 21.252 613.129C21.3284 612.823 21.5133 611.797 21.7507 610.465L21.8157 610.1C22.0364 608.86 22.2909 607.43 22.5324 606.157C22.8089 604.7 23.077 603.399 23.2627 602.842C23.4083 602.405 23.7636 601.213 24.2507 599.578C24.9828 597.12 26.0125 593.664 27.0755 590.272C27.7329 588.175 28.4011 586.109 29.0145 584.337C30.1395 581.087 30.9224 578.863 31.487 577.342C32.0473 575.832 32.4043 574.981 32.6779 574.502C32.8582 574.187 33.1339 573.956 33.4946 573.927C33.827 573.901 34.1035 574.059 34.285 574.218C34.4716 574.381 34.6208 574.598 34.7047 574.819C34.7783 575.014 34.8516 575.355 34.6531 575.653C34.5529 575.803 34.1607 576.52 33.7104 577.545C33.2642 578.561 32.7765 579.843 32.4734 581.116C32.2561 582.029 31.6653 584.211 30.9509 586.85C30.6308 588.032 30.2859 589.306 29.9387 590.599C28.812 594.792 27.6609 599.18 27.2273 601.348C26.7941 603.514 26.6085 605.091 26.4372 606.649L26.4008 606.981C26.242 608.431 26.0807 609.905 25.729 611.84C25.5218 612.979 25.2246 614.32 24.9066 615.754C24.6504 616.91 24.3806 618.127 24.1334 619.349C23.5722 622.124 23.112 624.99 23.112 627.5C23.112 628.872 23.1938 630.102 23.3777 631.165C23.6262 632.601 24.0538 633.687 24.6749 634.408C25.2777 635.107 26.0953 635.5 27.237 635.5C29.9702 635.5 31.4553 633.943 32.1355 632.965C32.2598 632.786 32.3564 632.627 32.4279 632.502C32.9031 631.671 34.3853 628.217 34.9995 625.514C35.3011 624.187 35.7217 621.912 36.1342 619.682L36.1672 619.503C36.5873 617.232 36.9971 615.023 37.252 614.004C37.3605 613.57 37.6397 612.37 38.0251 610.715C38.5259 608.564 39.2057 605.643 39.9225 602.634C41.1851 597.334 42.5667 591.742 43.2642 589.713C43.6068 588.716 43.9341 587.726 44.2427 586.791L44.3065 586.597C44.591 585.735 44.8595 584.92 45.1079 584.197C45.3743 583.422 45.6208 582.741 45.8422 582.212C45.9528 581.947 46.0604 581.712 46.1643 581.519C46.2635 581.334 46.3772 581.153 46.5085 581.022C47.0133 580.517 47.7306 580.547 48.2106 580.787C48.4576 580.911 48.6916 581.111 48.8243 581.387C48.9649 581.679 48.9729 582.022 48.8092 582.349L48.8085 582.35C48.8074 582.353 48.8051 582.358 48.8016 582.366C48.7965 582.378 48.7901 582.393 48.7822 582.414C48.7664 582.454 48.7467 582.507 48.723 582.573C48.6758 582.705 48.6159 582.882 48.5444 583.099C48.4015 583.535 48.2156 584.125 47.9983 584.834C47.5639 586.252 47.0063 588.14 46.4211 590.199C45.2494 594.324 43.9731 599.119 43.3523 602.223C42.787 605.05 42.4531 607.335 42.1503 609.408C42.1199 609.616 42.0898 609.822 42.0599 610.026C41.7316 612.261 41.4159 614.284 40.8463 616.5C40.7723 616.788 40.6867 617.11 40.5933 617.463C39.832 620.336 38.5512 625.168 38.7717 628.971C38.8564 630.431 39.161 631.681 39.7564 632.595C40.3368 633.486 41.2142 634.093 42.5469 634.254C44.4269 634.481 45.9999 633.424 47.3437 631.551C48.9456 629.319 50.1043 626.074 50.9305 623.07C51.5118 620.957 51.9139 619.025 52.1977 617.662C52.3187 617.081 52.4182 616.603 52.5008 616.258C52.8647 614.742 53.6621 612.044 54.4424 609.403L54.5388 609.077C55.355 606.315 56.128 603.685 56.3728 602.522C56.8722 600.15 58.6279 592.62 59.6398 589.711C59.8102 589.221 59.9962 588.646 60.1938 588.036C60.559 586.907 60.9637 585.657 61.3813 584.598C61.7045 583.778 62.0537 583.022 62.4266 582.488C62.6122 582.221 62.8252 581.979 63.0712 581.816C63.3277 581.646 63.6472 581.548 63.9936 581.643C64.4337 581.763 64.6127 582.151 64.6801 582.464C64.7491 582.783 64.7409 583.172 64.697 583.576C64.6081 584.392 64.3516 585.439 64.0455 586.498C63.7376 587.564 63.3713 588.671 63.0526 589.615C62.9867 589.81 62.9232 589.998 62.8628 590.176C62.6274 590.87 62.4388 591.426 62.3428 591.763C62.1012 592.608 61.7937 594.265 61.4788 595.981L61.4433 596.175C61.1445 597.804 60.8419 599.454 60.5951 600.379C60.5153 600.679 60.3933 601.116 60.2428 601.656C59.8707 602.99 59.3243 604.949 58.8084 606.997C58.202 609.404 57.6512 611.883 57.4846 613.55C57.3852 614.543 57.3236 615.489 57.3621 616.353C57.4151 617.545 57.657 618.523 58.1969 619.239C58.7236 619.937 59.5882 620.457 61.0455 620.629C61.872 620.726 62.6845 620.294 63.4918 619.395C64.2948 618.5 65.0159 617.222 65.6409 615.841C66.263 614.466 66.7761 613.02 67.1742 611.811C67.3433 611.297 67.4948 610.815 67.6253 610.4C67.795 609.86 67.9292 609.433 68.0204 609.196C68.3264 608.4 69.5711 604.636 70.7922 600.832C72.0164 597.018 73.1924 593.239 73.3731 592.395C73.57 591.477 74.3977 589.065 75.3314 586.575C76.269 584.074 77.3394 581.427 78.0398 580.027C78.6987 578.709 79.6818 575.683 80.648 572.119C81.6183 568.541 82.5575 564.473 83.1191 561.167C83.4023 559.499 83.7638 557.778 84.1307 556.049L84.1949 555.747C84.5416 554.114 84.8897 552.474 85.1825 550.849C85.8052 547.393 86.1678 544.051 85.7421 541.071C85.6999 540.776 85.6586 540.489 85.6183 540.209C84.8241 534.692 84.4335 531.979 86.2755 527.558C86.9728 525.884 87.7121 523.983 88.4566 522.068C89.6919 518.891 90.9414 515.677 92.0368 513.408C92.1151 513.245 92.2024 513.063 92.2994 512.86C94.38 508.513 100.91 494.867 119.089 470.948C136.871 447.55 148.501 421.417 152.544 404.509C152.812 403.39 153.046 402.313 153.246 401.28C155.845 387.884 157.995 375.705 158.744 371.291C158.754 371.231 158.775 371.174 158.806 371.121C170.115 351.936 178.733 335.872 183.776 317.989C183.836 317.777 184.027 317.63 184.247 317.625C184.468 317.621 184.665 317.761 184.733 317.971C186.049 322.024 188.208 327.638 190.419 333.388C190.825 334.443 191.232 335.501 191.636 336.556C194.236 343.342 196.713 349.968 197.722 354.004C198.721 358.001 199.341 360.102 199.955 361.73C200.416 362.953 200.874 363.908 201.499 365.213C201.709 365.649 201.936 366.125 202.189 366.662L202.377 367.06C204.31 371.164 206.241 375.264 206.735 380.327C206.838 381.383 207.017 382.724 207.237 384.306C207.294 384.714 207.353 385.137 207.414 385.574C207.778 388.165 208.214 391.27 208.592 394.695C209.089 399.208 209.487 404.286 209.487 409.5C209.487 415.675 208.346 421.413 207.234 427.003L207.196 427.191C206.07 432.855 204.988 438.365 205.112 444.114C205.192 447.855 205.299 450.908 205.406 453.479C205.48 455.246 205.554 456.789 205.62 458.172C205.757 461.017 205.862 463.189 205.862 465.25C205.862 466.454 205.53 467.889 205.025 469.592C204.727 470.597 204.359 471.724 203.952 472.973C203.67 473.838 203.368 474.761 203.058 475.745C201.532 480.572 199.728 486.979 198.608 495.565C197.747 502.164 196.904 507.029 196.15 511.113C195.972 512.075 195.8 512.992 195.633 513.877C195.092 516.756 194.614 519.299 194.227 521.947C193.914 524.086 193.662 526.29 193.486 528.785C193.485 528.8 193.483 528.815 193.481 528.83C193.134 530.973 192.745 533.455 192.328 536.2C189.292 556.189 184.8 590.093 184.625 609.13C184.447 628.404 185.532 650.365 186.264 665.2C186.562 671.24 186.802 676.098 186.875 679.113C187.124 689.462 188.496 703.795 191.983 716.745C192.314 717.975 192.671 719.223 193.047 720.482C193.942 723.482 194.94 726.545 195.94 729.593L196.178 730.318C197.097 733.119 198.011 735.903 198.835 738.598C200.623 744.444 202.01 749.932 202.125 754.362C202.243 758.932 201.554 761.848 200.774 765.144C200.72 765.372 200.666 765.601 200.612 765.833C199.773 769.405 198.874 773.618 198.75 781.009C198.5 795.863 199.374 811.097 199.624 815.094C199.643 815.39 199.613 815.843 199.554 816.406C199.494 816.979 199.399 817.699 199.276 818.546C199.029 820.241 198.664 822.464 198.226 825.079C198.063 826.051 197.889 827.077 197.708 828.152C196.913 832.862 195.963 838.493 195.041 844.482C192.774 859.211 190.691 876.029 191.499 886.587C192.652 901.666 196.328 926.154 199.394 945.673C200.037 949.769 200.654 953.65 201.215 957.177C201.741 960.483 202.217 963.478 202.619 966.048C202.842 967.475 203.1 969.11 203.384 970.919C204.932 980.751 207.29 995.726 209.395 1010.51C211.885 1027.99 214.028 1045.24 214.049 1053.38C214.049 1055.78 213.722 1060.58 213.319 1064.94C213.117 1067.12 212.896 1069.2 212.685 1070.82C212.58 1071.64 212.477 1072.34 212.379 1072.88C212.286 1073.4 212.189 1073.84 212.08 1074.08C211.45 1075.47 210 1078.78 210 1081.63C210 1082.31 210.081 1083 210.188 1083.71C210.22 1083.93 210.256 1084.16 210.291 1084.38C210.37 1084.89 210.452 1085.41 210.512 1085.93C210.689 1087.45 210.696 1089.08 209.96 1090.82C209.225 1092.56 207.402 1094.46 205.703 1095.99C204.089 1097.44 202.514 1098.62 201.905 1099.07L201.799 1099.15C201.275 1099.54 200.794 1099.92 200.325 1100.28C198.182 1101.94 196.316 1103.39 191.834 1105.45C190.312 1106.15 188.913 1107.06 187.835 1108.07C186.752 1109.08 186.026 1110.16 185.784 1111.19C185.549 1112.19 185.756 1113.19 186.63 1114.13C187.528 1115.1 189.146 1116.03 191.761 1116.77C197.095 1118.28 201.579 1119.94 204.731 1121.22C206.307 1121.87 207.55 1122.42 208.401 1122.81C208.826 1123 209.153 1123.16 209.374 1123.26C209.485 1123.32 209.569 1123.36 209.626 1123.38L209.691 1123.42L209.708 1123.42L209.712 1123.43L209.713 1123.43C209.714 1123.43 209.714 1123.43 209.49 1123.88L209.714 1123.43C209.852 1123.5 209.951 1123.63 209.981 1123.78C210.342 1125.62 211.106 1126.89 212.23 1127.76C213.366 1128.64 214.92 1129.15 216.931 1129.38C223.075 1130.07 230.15 1130.62 236.332 1129.87C242.531 1129.11 247.359 1127.76 250.191 1122.67C250.326 1122.43 250.651 1121.67 251.016 1120.55C251.376 1119.44 251.763 1118 252.031 1116.42C252.57 1113.24 252.603 1109.59 251.065 1106.87C250.633 1106.11 250.236 1105.43 249.879 1104.81C249.278 1103.78 248.788 1102.94 248.427 1102.21C248.134 1101.62 247.912 1101.08 247.773 1100.54C247.6 1099.86 247.412 1098.32 247.359 1096.72C247.332 1095.91 247.339 1095.08 247.4 1094.32C247.462 1093.57 247.579 1092.86 247.792 1092.32C248.129 1091.46 248.284 1090.01 248.346 1088.31C248.391 1087.04 248.385 1085.68 248.379 1084.39C248.377 1083.96 248.375 1083.53 248.375 1083.13C248.375 1081.94 247.985 1080.04 247.509 1077.99C247.38 1077.43 247.244 1076.87 247.11 1076.31C246.761 1074.85 246.422 1073.44 246.228 1072.34C245.91 1070.53 245.95 1069.52 246.02 1068.13L246.026 1068.02C246.094 1066.67 246.182 1064.91 246.001 1061.65C245.625 1054.88 245.75 1032.61 246 1018.12C246.251 1003.58 250.381 969.902 257.774 947.096C259.824 940.773 261.273 934.722 262.267 928.916C264.835 913.91 264.367 900.531 263.377 888.29C262.875 882.094 261.492 875.29 260.078 868.34C259.561 865.796 259.039 863.232 258.556 860.671C256.759 851.156 255.489 841.697 257.009 833.657C258.516 825.681 260.714 819.183 262.684 813.478L262.91 812.823C264.789 807.383 266.411 802.69 267.004 798.062C267.625 793.22 267.781 785.347 267.922 778.178L267.923 778.134C267.993 774.574 268.059 771.193 268.178 768.467C268.238 767.098 268.311 765.888 268.405 764.9C268.499 763.921 268.615 763.126 268.771 762.607C269.075 761.593 269.57 759.135 270.138 755.913C271.603 747.62 273.518 734.524 273.876 728.968C274.023 726.691 274.758 722.545 275.779 717.54C276.802 712.522 278.12 706.604 279.443 700.765C279.889 698.796 280.336 696.835 280.772 694.921L281.092 693.514C282.426 687.658 283.633 682.336 284.385 678.65C285.634 672.529 287.679 661.938 289.42 652.735C290.291 648.134 291.085 643.881 291.666 640.709C291.957 639.122 292.194 637.807 292.361 636.854C292.531 635.878 292.619 635.324 292.626 635.219C292.636 635.064 292.692 634.759 292.77 634.331L292.809 634.122C292.912 633.558 293.056 632.758 293.227 631.676C293.568 629.511 294.018 626.21 294.466 621.391C295.361 611.754 296.25 596.051 296.25 571.25C296.25 570.974 296.474 570.75 296.75 570.75C297.026 570.75 297.25 570.974 297.25 571.25C297.25 596.051 298.138 611.754 299.034 621.391C299.482 626.21 299.932 629.511 300.273 631.676C300.444 632.758 300.588 633.558 300.691 634.122L300.729 634.331C300.808 634.759 300.864 635.064 300.874 635.219C300.88 635.324 300.969 635.878 301.139 636.854C301.306 637.807 301.543 639.122 301.834 640.709C302.415 643.881 303.209 648.134 304.08 652.735C305.821 661.938 307.866 672.529 309.115 678.65C309.867 682.336 311.074 687.658 312.408 693.514L312.728 694.921C313.164 696.835 313.611 698.796 314.057 700.765C315.38 706.604 316.698 712.522 317.721 717.54C318.742 722.545 319.477 726.691 319.624 728.968C319.982 734.524 321.897 747.62 323.361 755.913C323.93 759.135 324.425 761.593 324.729 762.607C324.885 763.126 325.001 763.921 325.095 764.9C325.189 765.888 325.262 767.098 325.322 768.467C325.441 771.193 325.507 774.574 325.577 778.134L325.578 778.178C325.719 785.347 325.875 793.22 326.496 798.062C327.089 802.69 328.711 807.383 330.59 812.823L330.816 813.478C332.786 819.183 334.984 825.681 336.491 833.657C338.011 841.697 336.741 851.156 334.944 860.671C334.461 863.232 333.939 865.796 333.422 868.34C332.008 875.29 330.624 882.094 330.123 888.29C329.133 900.531 328.665 913.91 331.233 928.916C332.227 934.722 333.676 940.773 335.726 947.096C343.119 969.902 347.249 1003.58 347.5 1018.12C347.75 1032.61 347.875 1054.88 347.499 1061.65C347.318 1064.91 347.406 1066.67 347.474 1068.02L347.48 1068.13C347.55 1069.52 347.59 1070.53 347.272 1072.34C347.078 1073.44 346.739 1074.85 346.389 1076.31C346.255 1076.87 346.12 1077.43 345.99 1077.99C345.515 1080.04 345.125 1081.94 345.125 1083.13C345.125 1083.53 345.123 1083.96 345.121 1084.39C345.115 1085.68 345.109 1087.04 345.154 1088.31C345.216 1090.01 345.371 1091.46 345.707 1092.32C345.92 1092.86 346.038 1093.57 346.099 1094.32C346.161 1095.08 346.168 1095.91 346.141 1096.72C346.088 1098.32 345.9 1099.86 345.727 1100.54C345.588 1101.08 345.366 1101.62 345.073 1102.21C344.711 1102.94 344.222 1103.78 343.621 1104.81C343.264 1105.43 342.867 1106.11 342.435 1106.87C340.9 1109.59 340.997 1113.2 341.57 1116.37C342.031 1118.92 342.776 1121.08 343.154 1122.18C343.233 1122.41 343.297 1122.59 343.338 1122.72C346.25 1128.38 350.711 1129.31 356.975 1130.14C363.229 1130.97 370.442 1130.06 376.569 1129.38C382.696 1128.69 383.375 1125.88 383.53 1123.74C383.569 1123.6 383.662 1123.49 383.786 1123.43L384.01 1123.88C383.786 1123.43 383.786 1123.43 383.787 1123.43L383.792 1123.42L383.809 1123.42L383.874 1123.38C383.931 1123.36 384.015 1123.32 384.126 1123.26C384.347 1123.16 384.674 1123 385.099 1122.81C385.95 1122.42 387.193 1121.87 388.769 1121.22C391.921 1119.94 396.405 1118.28 401.739 1116.77C404.354 1116.03 406.222 1115.1 407.12 1114.13C407.994 1113.19 408.201 1112.19 407.966 1111.19C407.724 1110.16 406.748 1109.08 405.665 1108.07C404.587 1107.06 403.188 1106.15 401.666 1105.45C397.184 1103.39 395.318 1101.94 393.175 1100.28C392.706 1099.92 392.225 1099.54 391.7 1099.15C391.189 1098.77 389.515 1097.54 387.797 1095.99C386.098 1094.46 384.275 1092.56 383.539 1090.82C382.804 1089.08 382.811 1087.45 382.988 1085.93C383.048 1085.41 383.13 1084.89 383.209 1084.38C383.244 1084.16 383.28 1083.93 383.312 1083.71C383.418 1083 383.5 1082.31 383.5 1081.63C383.5 1078.78 382.05 1075.47 381.42 1074.08C381.311 1073.84 381.214 1073.4 381.121 1072.88C381.023 1072.34 380.92 1071.64 380.815 1070.82C380.604 1069.2 380.382 1067.12 380.181 1064.94C379.778 1060.58 379.451 1055.78 379.451 1053.37C379.472 1045.24 381.615 1027.99 384.105 1010.51C386.21 995.727 388.568 980.752 390.115 970.92C390.4 969.111 390.658 967.476 390.881 966.048C391.283 963.48 391.759 960.488 392.284 957.185C392.845 953.656 393.462 949.772 394.106 945.673C397.172 926.154 400.848 901.666 402.001 886.587C402.809 876.029 400.726 859.211 398.459 844.482C397.537 838.495 396.587 832.866 395.793 828.157C395.611 827.081 395.438 826.052 395.274 825.079C394.836 822.464 394.471 820.241 394.224 818.546C394.101 817.699 394.006 816.979 393.946 816.406C393.887 815.843 393.857 815.39 393.876 815.094C394.126 811.097 395 795.863 394.75 781.009C394.626 773.618 393.727 769.405 392.888 765.833C392.834 765.601 392.78 765.372 392.726 765.144C391.946 761.848 391.256 758.931 391.375 754.362C391.49 749.932 392.877 744.444 394.665 738.598C395.489 735.903 396.402 733.12 397.321 730.319L397.56 729.593C398.56 726.545 399.558 723.482 400.453 720.482C400.829 719.223 401.186 717.975 401.517 716.745C405.004 703.795 406.376 689.462 406.625 679.113C406.698 676.098 406.938 671.24 407.236 665.2C407.968 650.365 409.053 628.404 408.875 609.13C408.7 590.093 404.208 556.189 401.172 536.2C400.755 533.455 400.366 530.973 400.019 528.83C400.017 528.815 400.015 528.8 400.014 528.785C399.838 526.29 399.586 524.086 399.273 521.947C398.886 519.299 398.408 516.756 397.866 513.877C397.7 512.992 397.527 512.075 397.35 511.113C396.596 507.029 395.753 502.164 394.892 495.565C393.772 486.979 391.968 480.572 390.442 475.745C390.132 474.761 389.83 473.837 389.548 472.972C389.141 471.723 388.773 470.597 388.475 469.592C387.97 467.889 387.638 466.454 387.638 465.25C387.638 463.189 387.742 461.017 387.879 458.173C387.946 456.789 388.02 455.246 388.094 453.479C388.201 450.908 388.308 447.855 388.388 444.114C388.512 438.365 387.43 432.855 386.304 427.191L386.266 427.003C385.154 421.413 384.013 415.675 384.013 409.5C384.013 404.286 384.41 399.208 384.908 394.695C385.286 391.27 385.722 388.165 386.086 385.574C386.147 385.137 386.206 384.714 386.263 384.306C386.483 382.724 386.662 381.383 386.765 380.327C387.259 375.264 389.19 371.164 391.123 367.06L391.137 367.032L391.31 366.662C391.564 366.124 391.791 365.649 392.001 365.212C392.626 363.908 393.084 362.953 393.545 361.73C394.159 360.102 394.779 358.001 395.778 354.004C396.787 349.968 399.264 343.342 401.864 336.556C402.268 335.502 402.675 334.444 403.08 333.39C405.292 327.639 407.451 322.024 408.767 317.971C408.835 317.761 409.032 317.621 409.252 317.625C409.473 317.63 409.664 317.777 409.724 317.989C414.767 335.872 423.385 351.936 434.694 371.121C434.712 371.152 434.726 371.184 434.738 371.218C434.745 371.242 434.752 371.266 434.756 371.291C435.505 375.705 437.655 387.884 440.254 401.28C440.454 402.313 440.688 403.39 440.956 404.509C444.999 421.417 456.629 447.55 474.411 470.948C492.59 494.867 499.12 508.513 501.201 512.86C501.298 513.063 501.385 513.246 501.463 513.408C502.559 515.677 503.808 518.891 505.043 522.068C505.788 523.983 506.527 525.884 507.224 527.558C509.066 531.979 508.676 534.692 507.882 540.209C507.841 540.489 507.8 540.776 507.758 541.071C507.332 544.051 507.695 547.393 508.317 550.849C508.61 552.474 508.958 554.113 509.305 555.745L509.369 556.049C509.736 557.778 510.098 559.499 510.381 561.167C510.942 564.473 511.882 568.541 512.852 572.119C513.818 575.683 514.801 578.709 515.46 580.027C516.161 581.427 517.231 584.074 518.169 586.575C519.102 589.065 519.93 591.477 520.127 592.395C520.308 593.239 521.484 597.018 522.708 600.832C523.929 604.636 525.174 608.4 525.48 609.196C525.571 609.433 525.705 609.859 525.874 610.399C526.005 610.814 526.157 611.296 526.326 611.811C526.724 613.02 527.237 614.466 527.859 615.841C528.484 617.222 529.205 618.5 530.008 619.395C530.815 620.294 531.628 620.726 532.455 620.629C533.912 620.457 534.776 619.937 535.303 619.239C535.843 618.523 536.085 617.545 536.138 616.353C536.176 615.489 536.115 614.543 536.015 613.55C535.849 611.883 535.298 609.404 534.692 606.997C534.176 604.949 533.629 602.99 533.257 601.656C533.107 601.116 532.985 600.679 532.905 600.379C532.658 599.453 532.355 597.801 532.056 596.169L532.021 595.981C531.706 594.265 531.399 592.608 531.157 591.763C531.061 591.427 530.873 590.871 530.638 590.178C530.577 589.999 530.513 589.811 530.447 589.615C530.129 588.671 529.762 587.564 529.454 586.498C529.148 585.439 528.892 584.392 528.803 583.576C528.759 583.172 528.751 582.783 528.82 582.464C528.887 582.151 529.066 581.763 529.506 581.643C529.853 581.548 530.172 581.646 530.429 581.816C530.675 581.979 530.888 582.221 531.073 582.488C531.446 583.022 531.796 583.778 532.119 584.598C532.536 585.658 532.941 586.908 533.306 588.037C533.504 588.647 533.69 589.221 533.86 589.711C534.872 592.62 536.628 600.15 537.127 602.522C537.372 603.685 538.145 606.315 538.961 609.077L539.058 609.404C539.838 612.044 540.635 614.742 540.999 616.258C541.082 616.603 541.181 617.081 541.302 617.662C541.586 619.025 541.988 620.957 542.569 623.07C543.396 626.074 544.554 629.319 546.156 631.551C547.5 633.424 549.073 634.481 550.953 634.254C552.286 634.093 553.163 633.486 553.744 632.595C554.339 631.681 554.644 630.431 554.728 628.971C554.949 625.168 553.668 620.336 552.907 617.463C552.813 617.11 552.728 616.788 552.654 616.5C552.084 614.284 551.768 612.261 551.44 610.026C551.41 609.822 551.38 609.616 551.35 609.407C551.047 607.335 550.713 605.049 550.148 602.223C549.527 599.119 548.251 594.324 547.079 590.199C546.494 588.14 545.936 586.252 545.502 584.834C545.284 584.125 545.098 583.535 544.956 583.099C544.884 582.882 544.824 582.705 544.777 582.573C544.753 582.507 544.734 582.454 544.718 582.414C544.71 582.393 544.703 582.378 544.698 582.366C544.693 582.354 544.691 582.349 544.691 582.349C544.527 582.022 544.535 581.679 544.676 581.387C544.808 581.111 545.042 580.911 545.289 580.787C545.769 580.547 546.487 580.517 546.992 581.022C547.123 581.153 547.236 581.334 547.336 581.519C547.389 581.618 547.443 581.728 547.498 581.847C547.551 581.961 547.604 582.083 547.658 582.212C547.879 582.741 548.126 583.422 548.392 584.197C548.556 584.673 548.728 585.189 548.908 585.732C549.021 586.075 549.138 586.429 549.257 586.791C549.566 587.726 549.893 588.716 550.236 589.713C550.933 591.742 552.315 597.334 553.577 602.634C554.295 605.645 554.975 608.568 555.476 610.719C555.861 612.372 556.14 613.57 556.248 614.004C556.503 615.023 556.913 617.232 557.333 619.503C557.756 621.79 558.191 624.152 558.5 625.514C559.115 628.217 560.597 631.671 561.072 632.502C561.144 632.627 561.24 632.786 561.365 632.965C562.045 633.943 563.53 635.5 566.263 635.5C567.405 635.5 568.222 635.107 568.825 634.408C569.446 633.687 569.874 632.601 570.122 631.165C570.306 630.102 570.388 628.872 570.388 627.5C570.388 624.99 569.928 622.124 569.367 619.349C569.119 618.127 568.85 616.91 568.593 615.755C568.275 614.32 567.978 612.979 567.771 611.84C567.419 609.905 567.258 608.431 567.099 606.98L567.063 606.649C566.891 605.091 566.706 603.514 566.273 601.348C565.839 599.18 564.688 594.792 563.561 590.599C563.215 589.308 562.87 588.036 562.55 586.854C561.835 584.214 561.244 582.029 561.027 581.116C560.723 579.843 560.236 578.561 559.79 577.545C559.339 576.52 558.947 575.803 558.847 575.653C558.648 575.355 558.722 575.014 558.795 574.819C558.879 574.598 559.028 574.381 559.215 574.218C559.396 574.059 559.673 573.901 560.005 573.927C560.366 573.956 560.642 574.187 560.822 574.502C561.096 574.981 561.453 575.832 562.013 577.342C562.578 578.863 563.361 581.087 564.485 584.337C565.099 586.109 565.767 588.175 566.424 590.272C567.487 593.663 568.517 597.119 569.249 599.576C569.736 601.212 570.092 602.405 570.237 602.842C570.423 603.399 570.691 604.7 570.968 606.157C571.233 607.557 571.515 609.147 571.749 610.465C571.987 611.797 572.172 612.823 572.248 613.129C572.258 613.168 572.269 613.216 572.283 613.273C572.504 614.186 573.268 617.347 575.126 619.553C576.25 620.888 577.74 621.834 579.742 621.751C580.684 621.711 581.389 621.45 581.925 621.054C582.464 620.657 582.864 620.101 583.153 619.428C583.715 618.121 583.839 616.434 583.798 614.822C583.778 614.023 583.718 613.258 583.658 612.584C583.598 611.921 583.535 611.324 583.514 610.9C583.391 608.444 582.15 601.486 581.147 596.219C580.15 590.984 578.153 582.62 577.028 578.121C576.899 577.604 576.747 576.991 576.576 576.304C575.257 570.984 572.832 561.209 571.292 556.919C569.903 553.049 567.153 546.776 565.941 544.099L563.812 540.242C563.679 540 563.767 539.696 564.008 539.562C564.25 539.429 564.554 539.517 564.688 539.759L566.69 543.388C567.653 543.463 568.955 543.87 570.184 544.364C571.563 544.919 572.944 545.623 573.798 546.214C574.572 546.75 575.139 547.446 575.746 548.19C575.832 548.296 575.919 548.403 576.008 548.51C576.73 549.386 577.607 550.384 579.063 551.475C580.491 552.546 582.51 553.395 584.651 553.79C586.792 554.185 589.005 554.116 590.832 553.409C592.044 552.94 592.761 552.161 593.141 551.298C593.508 550.467 593.573 549.534 593.429 548.689C593.283 547.836 592.934 547.128 592.534 546.729C591.809 546.003 590.926 545.302 590.064 544.628L589.871 544.478C589.09 543.868 588.329 543.273 587.784 542.729C587.539 542.483 587.352 542.248 587.203 542.014C586.957 541.627 586.82 541.247 586.695 540.902C586.669 540.831 586.644 540.761 586.619 540.693C586.467 540.288 586.277 539.853 585.863 539.3C584.774 537.849 582.561 535.755 580.829 534.393C579.993 533.736 578.656 532.974 577.26 532.221C576.853 532.002 576.44 531.783 576.037 531.569C575.08 531.061 574.174 530.58 573.506 530.179C572.509 529.581 571.658 528.758 570.862 527.963C570.783 527.884 570.704 527.805 570.626 527.726C569.905 527.003 569.224 526.319 568.472 525.782C566.752 524.553 562.41 521.947 557.946 520.087L557.945 520.086C556.642 519.54 553.838 518.395 552.869 518.104C552.234 517.913 550.282 517.284 548.344 516.512C547.375 516.126 546.399 515.7 545.59 515.272C545.185 515.058 544.814 514.84 544.503 514.621C544.198 514.407 543.924 514.174 543.738 513.925C543.673 513.839 543.6 513.714 543.522 513.565C543.475 513.476 543.426 513.378 543.376 513.275C543.236 512.984 543.066 512.606 542.87 512.153C542.478 511.245 541.977 510.019 541.396 508.553C540.234 505.621 538.748 501.715 537.169 497.439C534.012 488.89 530.477 478.848 528.41 472.148C526.518 466.013 524.89 457.532 523.006 447.721C522.827 446.785 522.644 445.836 522.459 444.876C520.335 433.846 517.838 421.3 514.281 408.509C507.758 385.048 501.766 373.992 496.577 364.416C496.111 363.555 495.651 362.706 495.197 361.862C493.956 359.548 492.878 357.652 491.927 355.981C488.686 350.28 486.931 347.195 485.273 339.1C483.88 332.297 480.429 322.512 477.513 314.242C476.887 312.464 476.284 310.757 475.733 309.164C474.959 306.931 474.285 304.923 473.784 303.27C472.801 300.023 470.365 295.001 467.697 290.011C465.036 285.033 462.165 280.129 460.336 277.136C460.294 277.067 460.269 276.989 460.264 276.909C460.055 273.816 459.638 265.438 459.638 256.625C459.638 245.628 458.631 224.519 447.975 208.907C442.987 201.599 438.913 197.296 434.546 194.191C430.171 191.079 425.477 189.15 419.188 186.618C418.269 186.248 417.315 185.865 416.325 185.464C409.125 182.546 404.261 181.679 399.421 180.932C399.06 180.876 398.699 180.821 398.337 180.766C393.83 180.078 389.182 179.369 382.6 177.098C368.455 172.216 356.548 166.829 349.636 162.807C346.198 160.807 342.861 158.762 340.349 156.834C339.094 155.871 338.028 154.925 337.255 154.015C336.491 153.116 335.965 152.197 335.89 151.292C335.821 150.469 335.79 147.831 335.781 144.377C335.762 137.401 335.832 126.966 335.888 120.871C335.889 120.759 335.927 120.651 335.997 120.564C336.563 119.852 337.039 118.81 337.437 117.559C337.833 116.316 338.142 114.903 338.391 113.477C338.639 112.052 338.827 110.624 338.984 109.353C339.019 109.066 339.053 108.787 339.086 108.517C339.198 107.596 339.296 106.784 339.394 106.171C339.605 104.852 339.703 103.625 339.777 102.704C339.789 102.551 339.8 102.406 339.812 102.271C339.849 101.824 339.887 101.422 339.952 101.192C339.968 101.134 339.999 101.039 340.06 100.95C340.118 100.865 340.301 100.659 340.605 100.713C340.832 100.752 340.951 100.919 340.982 100.963C341.031 101.033 341.069 101.111 341.097 101.178C341.114 101.217 341.131 101.257 341.148 101.297C341.328 101.721 341.517 102.169 341.939 102.579C342.391 103.018 343.156 103.452 344.575 103.629C345.093 103.694 345.649 103.437 346.271 102.787C346.888 102.142 347.492 101.192 348.088 100.077C348.631 99.0598 349.153 97.9376 349.667 96.8333L349.808 96.5313C350.362 95.3399 350.911 94.1774 351.457 93.2473C352.476 91.5086 353.369 88.6069 354.039 85.3677C354.706 82.1425 355.14 78.638 355.263 75.7289C355.32 74.4054 355.226 73.2915 355.021 72.3572C354.681 70.8084 354.038 69.7506 353.259 69.0361C352.479 68.3193 351.533 67.921 350.548 67.7421C349.686 67.5853 348.865 68.0141 348.137 68.7489C347.415 69.4774 346.872 70.4254 346.598 71.0704C346.502 71.2973 346.255 71.4202 346.016 71.36C345.777 71.2998 345.617 71.0744 345.64 70.829C346.035 66.5701 346.471 59.6914 346.497 52.9076C346.523 46.1007 346.134 39.4676 344.912 35.6528C343.37 30.8431 339.065 21.9161 331.271 14.1992C323.486 6.49246 312.232 0.000164151 296.763 0.000164151C296.759 0.000164151 296.754 0.000109226 296.75 0C296.746 0.000109226 296.741 0.000164151 296.737 0.000164151C281.268 0.000164151 270.014 6.49246 262.229 14.1992Z"


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

val frontNeck =
    "M19 17.5C22.0417 15.5833 29.5 9.475 35 0.375L34.75 14.25V27.375C33.9583 28.4583 31.9 34.45 30 49.75C28.1 65.05 28.875 72.2083 29.5 73.875L17.625 75.375L19.875 67.25L24.875 40.625L28.625 27.375C24 36.5 17.25 58.75 16.875 60.25C16.5 61.75 15.875 65.125 11.5 73.25C8 79.75 2.45833 82.125 0.125 82.5C1.29167 80.7917 3.75 76.975 4.25 75.375C4.875 73.375 4.625 74 8.125 53.125C10.925 36.425 16.5417 22.4167 19 17.5Z"

val abductor =
    "M13 6.625L15 10.625L8.375 27.125L4.875 36.75L0.5 49.625L2.75 36.75L4.875 23L7.625 11.875L11 0.875L13 6.625Z"

val hipFlexor =
    "M24.625 54.125C26.25 64.5 22 69.25 20.5 79.875C19.4987 86.9673 19.5302 90.2015 19.7375 91.3203L19.875 91.75C19.8267 91.681 19.7786 91.5423 19.7375 91.3203L11.875 66.75C10.3333 60.6667 7 46.575 6 38.875C5 31.175 2.08333 9.75 0.75 0L2.875 3.875L7.25 17.375L9.75 24.5L15.25 36.25L24.625 54.125Z"

val quad1 =
    "M46.5817 13.75C39.3317 10.625 30.0817 7.5 25.5817 0.875L19.7067 15L15.4567 27L12.3317 35.25L10.0817 45.25L9.33173 51.625L8.45673 57.875L5.45673 79.125C4.16506 90.0417 1.43172 113.75 0.831726 121.25C0.231728 128.75 0.581726 148.208 0.831726 157C3.83173 125.5 9.83173 114 12.5817 105.875C15.3317 97.75 21.5817 85.125 31.2067 71C40.8317 56.875 44.7067 44.75 46.5817 32.375C48.4567 20 46.5817 14.5 46.5817 13.75Z"

val quad2 =
    "M23.4865 23C28.3615 14.625 27.6115 17.875 38.1115 0L31.3615 21.75L26.4865 39C25.3198 43.6667 22.5364 55.75 20.7365 66.75C18.9365 77.75 18.4865 90.8333 18.4865 96V110.5L19.3615 124.75L20.7365 138C21.7781 145.375 23.9865 160.825 24.4865 163.625C25.1115 167.125 29.1115 183.5 30.8615 189.875C32.6115 196.25 35.6115 200 38.8615 216.5C41.4615 229.7 35.9448 242.833 32.8615 247.75C28.7615 255.35 23.6531 258.583 21.6115 259.25C20.7115 256.75 17.8198 237.042 16.4865 227.5C18.2365 221.25 17.2365 212 13.1115 201.625C8.98645 191.25 5.36145 176 4.11145 168.875C2.86145 161.75 -0.38855 115 0.36145 97C0.961449 82.6 6.27812 62.9167 8.86145 54.875C12.1115 47.0417 19.5865 29.7 23.4865 23Z"

val quad3 =
    "M43.7383 91.0007C38.2383 75.0007 33.8633 54.0007 32.8633 37.2507C32.2607 27.1582 30.4386 11.5158 28.9883 0.171875L28.2383 7.37571L26.3633 17.1257L23.6133 26.5007L19.8633 35.8757C16.8633 44.1674 9.71328 66.1757 5.11328 87.8757C-0.636719 115.001 -0.761719 134.251 0.988281 160.001C2.38828 180.601 6.98828 203.917 9.11328 213.001C10.2383 217.626 13.7133 230.226 18.6133 243.626C22.8611 255.242 21.2784 267.072 19.4517 273.381C19.151 274.136 18.8688 274.963 18.6133 275.876C18.885 275.188 19.1712 274.349 19.4517 273.381C21.2101 268.966 23.6006 267.029 24.9883 265.001C26.6133 262.626 33.6133 263.876 37.7383 265.001C41.0383 265.901 43.1966 272.626 43.8633 275.876L44.7383 262.751L46.9883 247.251L51.1133 218.626L53.9883 189.876L55.4883 160.876V143.626C55.2383 137.751 54.6133 125.101 54.1133 121.501C53.4883 117.001 49.2383 107.001 43.7383 91.0007Z"

val quad4 =
    "M11.4356 42.375C11.6439 35.2917 11.6606 16.95 10.0606 0.25C11.1856 3.45833 13.8855 11.875 15.6855 19.875C17.9355 29.875 20.1855 45.5 21.5605 54.625C22.6605 61.925 24.6855 79.25 25.5605 87L27.8105 106.875C27.5105 111.075 25.6855 124.625 24.8105 130.875L22.4355 142.875L21.6855 149.125C20.2272 153.75 16.6355 163.625 13.9355 166.125C10.5605 169.25 9.43555 170.75 4.18555 166.625C-1.06445 162.5 0.0605469 151.25 0.185547 146.75C0.310547 142.25 2.81055 123.5 4.93555 111.625C6.63555 102.125 9.06057 78 10.0606 67.125L11.4356 42.375Z"

val sartorius =
    "M10.125 6.375L14.75 13.625L16.5 28.375L18.375 41.625L19.625 50.5L21.75 63.375L23.75 72.75L28 88.125L32.75 102.125L36.125 113L39.625 125.5L42 135.75L44.375 148.875L46 162.625L47.25 178.875L47.625 198.5L47.25 213.625V225.5L45 238.875L42.375 214.75L39.75 193.875L35.75 167C34.0833 158.5 30.2 139.8 28 133C25.25 124.5 20 109.375 17.375 102.125C15.275 96.325 12.4167 86.125 11.25 81.75L8.25 65.125L5.25 41.625L1 0.625L6 3L10.125 6.375Z"

val adductor1 =
    "M1.83811 20.5C4.08811 14.125 6.83811 5.25 5.83811 0.25L13.2131 20.5L17.8381 35.375L19.2131 41.125C21.0881 48.2917 24.8631 66.45 24.9631 81.75C25.0631 97.05 24.0048 110.208 23.4631 114.875L19.2131 134.125L14.2131 157.625V147.625V115.75C13.9631 108.125 12.7881 89 10.0881 73.5C6.71311 54.125 3.08811 45 1.83811 41.125C0.58811 37.25 -0.41189 26.875 1.83811 20.5Z"

val adductor2 =
    "M23.1247 42.625C20.0831 36.5833 11.1997 19.675 -0.000263214 0.375C1.24976 5.75 8.16641 25.5417 10.2498 32.5C10.8331 34.0833 12.7498 41.375 15.7498 57.875C18.7498 74.375 17.8331 98.4167 16.9998 108.375C17.6247 104.875 19.3747 94.9 21.3747 83C23.3747 71.1 23.3747 51.125 23.1247 42.625Z"

val calf1 =
    "M-7.62939e-06 27.125C3.99999 20.125 7.74999 10.75 7.12499 0L9 5.375L10.625 11.625L12.5 18.25L14.125 26.125L16 35.5L17.375 43L18.25 49.125L19.5 57.25L21 65.875L22.375 76.125L23.375 84.25L24.5 94.5L26.625 118.125L28.875 146.5L30.875 167.75L32.75 182.875L35.25 200L38.875 219.625L42.5 239L36.25 241.375L34.625 232.75L32.75 222.125L31.75 218.875L27 200.875L20.5 172.875L14.375 145.5L5.875 106.5C4.33333 98.7083 1.3 78.95 1.5 62.25C1.7 45.55 0.583328 31.875 -7.62939e-06 27.125Z"

val calf2 =
    "M9.5 35.25C9.7 18.55 8.58333 4.875 7.99999 0.125L4.625 4.875L3.875 16.625C3.41667 22.8333 2.2 37.05 1 44.25C-0.200001 51.45 1.5 64.9167 2.5 70.75L8.125 101.125L15 141.625C16.2083 148.458 18.9 165.175 20 177.375C21.1 189.575 20.4583 197.458 20 199.875L17.875 211.125L19 220.5L28.5 222.25L35.5 218.5L35 212.5L33.5 197.375L31.75 179.125L27.125 141.625L22.375 118.5L13.875 79.5C12.3333 71.7083 9.3 51.95 9.5 35.25Z"

val calf3 =
    "M24.0001 173.375C25.1001 185.575 24.4584 193.458 24.0001 195.875L21.8751 207.5L22.8751 216.625L17.5001 215.25L19.3751 195.5L19.6251 188.625L19.1251 179.375L17.5001 164.625L15.3751 150L12.8751 135.25L10.1251 116.875L7.75006 102L5.25006 85.875L3.25006 72.375L1.62506 58.125L0.500061 46.875V38.875L2.25006 18.625L4.37506 5L8.62506 0.875L7.87506 12.625C7.41672 18.8333 6.20006 33.05 5.00006 40.25C3.80005 47.45 5.50006 60.9167 6.50006 66.75L12.1251 97.125L19.0001 137.625C20.2084 144.458 22.9001 161.175 24.0001 173.375Z"

val calf4 =
    "M11.5547 14.625C13.4297 12 17.3047 5.45 17.8047 0.25L18.1797 4L20.3047 16.125L21.9297 24.25L23.1797 31.5L24.1797 38.375L24.9297 47.875L25.3047 58.125L24.6797 70.625C24.388 72.9583 23.6297 78.775 22.9297 83.375C22.0547 89.125 19.3047 98.25 18.4297 100.75C17.7297 102.75 14.5547 115.333 13.0547 121.375L9.05469 146.25L7.05469 166.375L4.67969 141.125L2.67969 109.375C2.22135 100.292 1.20469 78.525 0.804688 64.125C0.304688 46.125 3.42969 33.125 4.42969 28C5.22969 23.9 9.51302 17.375 11.5547 14.625Z"


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
