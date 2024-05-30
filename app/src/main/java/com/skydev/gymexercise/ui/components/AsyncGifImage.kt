package com.skydev.gymexercise.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AsyncGifImage(
    modifier: Modifier = Modifier,
    url: String,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .decoderFactory(ImageDecoderDecoder.Factory())
            .build(),
        contentDescription = null,
        modifier = modifier
            .size(200.dp)
            .clip(RoundedCornerShape(6.dp)),
        contentScale = ContentScale.Crop
    )
}
