package com.skydev.gymexercise.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    progress: Long,
    total: Long,
    content: @Composable () -> Unit
) {
    val animatedProgress = (progress / total.toFloat())

    Box(modifier = modifier.size(200.dp), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = animatedProgress,
            strokeWidth = 8.dp,
            color = Color.Green,
            modifier = Modifier.size(160.dp)
        )
        content()
        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green
            ),
        )
    }
}