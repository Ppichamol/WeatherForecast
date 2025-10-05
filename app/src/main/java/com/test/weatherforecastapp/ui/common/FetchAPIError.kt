package com.test.weatherforecastapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.weatherforecastapp.R
import com.test.weatherforecastapp.ui.theme.ExtendedColor

@Composable
fun FetchAPIError(
    isDarkBackground: Boolean = false
) {
    var iconColor = ExtendedColor.HintColor
    var textColor = ExtendedColor.DarkGrey

    if (isDarkBackground) {
        iconColor = ExtendedColor.White
        textColor = ExtendedColor.Neutral2
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_fetch_error),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Please try again later",
            color = textColor,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Something went wrong \nPlease check your connection and try again.",
            color = textColor,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}