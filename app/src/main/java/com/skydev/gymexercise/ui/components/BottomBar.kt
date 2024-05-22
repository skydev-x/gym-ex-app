package com.skydev.gymexercise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(modifier = modifier) {
            Text(text = "Bottom Bar")
        }
    }
}