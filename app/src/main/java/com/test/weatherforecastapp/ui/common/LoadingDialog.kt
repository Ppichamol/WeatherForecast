package com.test.weatherforecastapp.ui.common

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.*
import com.test.weatherforecastapp.R
import com.test.weatherforecastapp.ui.theme.ExtendedColor

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {

        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever
        )

        //Change Lottie Color
        val tintColor = ExtendedColor.Neutral1
        val colorFilter = remember(tintColor) {
            PorterDuffColorFilter(tintColor.toArgb(), PorterDuff.Mode.SRC_ATOP)
        }

        val colorProp = rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = colorFilter,
            "**"
        )
        val dynamicProps = rememberLottieDynamicProperties(colorProp)

        LottieAnimation(
            composition = composition,
            progress = { progress },
            dynamicProperties = dynamicProps,
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Crop
        )
    }
}