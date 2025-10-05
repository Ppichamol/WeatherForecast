package com.test.weatherforecastapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.weatherforecastapp.R
import com.test.weatherforecastapp.ui.theme.ExtendedColor

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
fun NoInternetConnection(
    isDarkBackground: Boolean = false
) {
    var iconColor = ExtendedColor.HintColor
    var textColor = ExtendedColor.DarkGrey

    if (isDarkBackground) {
        iconColor = ExtendedColor.White
        textColor = ExtendedColor.Neutral2
    }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = ExtendedColor.BackgroundItemTransparent)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_no_internet),
            contentDescription = null,
            modifier = Modifier.size(25.dp),
            tint = iconColor
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "No Internet Connection",
            color = textColor,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            lineHeight = 22.sp,
        )
    }
}