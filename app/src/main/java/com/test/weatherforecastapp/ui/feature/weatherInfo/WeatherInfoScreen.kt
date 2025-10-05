package com.test.weatherforecastapp.ui.feature.weatherInfo

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.test.weatherforecastapp.R
import com.test.weatherforecastapp.model.DisplayCityModel
import com.test.weatherforecastapp.model.DisplayHoursDataModel
import com.test.weatherforecastapp.ui.common.FetchAPIError
import com.test.weatherforecastapp.ui.common.LoadingDialog
import com.test.weatherforecastapp.ui.common.NoInternetConnection
import com.test.weatherforecastapp.ui.theme.ExtendedColor

@Composable
@Preview
fun WeatherInfoScreen(
    cityInfo: DisplayCityModel? = null,
    viewModel: WeatherInfoViewModel = hiltViewModel(),
    onBackPress: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()
    var bgDisplay by remember { mutableIntStateOf(R.drawable.img_bg_sky_blue) }
    var isCelsius by remember { mutableStateOf(true) }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        if (cityInfo != null) {
            viewModel.fetchWeatherInfo(
                lat = cityInfo.lat,
                lon = cityInfo.lon
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Crossfade(
            targetState = bgDisplay,
            animationSpec = tween(durationMillis = 800)
        ) { resId ->
            Image(
                painter = painterResource(id = resId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    Scaffold (
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Column {
                TopbarTab(
                    isCelsius,
                    onToggle = { isToggle ->
                        isCelsius = isToggle
                    },
                    onBackPress = {
                        onBackPress()
                    }
                )
                if (!isConnected) {
                    NoInternetConnection(
                        isDarkBackground = true
                    )
                }
            }
        },
        containerColor = Color.Transparent

    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            when (uiState) {
                is WeatherInfoModelUiState.LoadingState -> {
                    LoadingDialog()
                }

                is WeatherInfoModelUiState.FetchDataSuccess -> {
                    val data = (uiState as WeatherInfoModelUiState.FetchDataSuccess)
                    CurrentWeatherHeader(
                        item = data.currentWeather,
                        cityName = data.cityName,
                        cityInfo = cityInfo,
                        isCelsius = isCelsius
                    )

                    HourlyForecast(
                        items = data.weatherList,
                        isCelsius = isCelsius
                    )

                    DailyForecast(
                        items = data.dailyForecast,
                        isCelsius = isCelsius
                    )

                    OthersWeatherInfo(
                        item = data.currentWeather,
                        isCelsius = isCelsius
                    )

                    if(!data.isDayTime) {
                        bgDisplay = R.drawable.img_bg_night
                    }
                }

                is WeatherInfoModelUiState.EmptyState -> {}

                is WeatherInfoModelUiState.FetchDataError -> {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        FetchAPIError(
                            isDarkBackground = true
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TopbarTab(
    isCelsius: Boolean,
    onToggle: (Boolean) -> Unit,
    onBackPress: () -> Unit
) {
    var isBackPressed = false


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_backward),
            contentDescription = null,
            tint = ExtendedColor.Neutral2,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(30.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        if(!isBackPressed) {
                            onBackPress()
                            isBackPressed = true
                        }
                    }
                ),
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TempUnitToggle(
                isCelsius = isCelsius,
                onToggle = { isToggle ->
                    onToggle(isToggle)
                }
            )
        }
    }
}

@Composable
fun CurrentWeatherHeader(
    item: DisplayHoursDataModel,
    cityName: String,
    cityInfo: DisplayCityModel? = null,
    isCelsius: Boolean = true,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = cityName.ifBlank { cityInfo?.name ?: "-" },
            color = ExtendedColor.White,
            fontWeight = FontWeight.W500,
            fontSize = 26.sp,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = item.setTempUnit(item.temp, isCelsius),
            color = ExtendedColor.White,
            fontWeight = FontWeight.W300,
            fontSize = 70.sp,
            lineHeight = 74.sp,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${item.title}: ${item.description}",
            color = ExtendedColor.White,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "H:${item.setTempUnit(item.minTemp, isCelsius)} L:${item.setTempUnit(item.maxTemp, isCelsius)}",
            color = ExtendedColor.Neutral2,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun HourlyForecast(
    items: List<DisplayHoursDataModel>,
    isCelsius: Boolean = true,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = ExtendedColor.BackgroundItemTransparent,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_watch),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(16.dp),
                tint = ExtendedColor.Neutral2
            )

            Text(
                text = "3 HOURLY FORECAST",
                color = ExtendedColor.Neutral2,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.4f),
                        blurRadius = 3f
                    )
                )
            )
        }

        HorizontalDivider(thickness = 0.5.dp, color = ExtendedColor.Neutral1)

        LazyRow {
            items(items.size) { position ->
                HourlyForecastItem(
                    items[position],
                    isCelsius = isCelsius
                )
            }
        }
    }
}

@Composable
fun HourlyForecastItem(
    item: DisplayHoursDataModel? = null,
    isCelsius: Boolean = true,
) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = item?.getHourlyTime() ?: "-:--",
            color = ExtendedColor.White,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        AsyncImage(
            model = item?.imageUrl(),
            contentDescription = null,
            modifier = Modifier.size(44.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = item?.setTempUnit(item.temp, isCelsius) ?: "-",
            color = ExtendedColor.White,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
fun DailyForecast(
    items: List<DisplayHoursDataModel>,
    isCelsius: Boolean = true,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = ExtendedColor.BackgroundItemTransparent,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_calendar),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(16.dp),
                tint = ExtendedColor.Neutral2
            )

            Text(
                text = "5-DAY FORECAST",
                color = ExtendedColor.Neutral2,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.4f),
                        blurRadius = 3f
                    )
                )
            )
        }

        HorizontalDivider(thickness = 0.5.dp, color = ExtendedColor.Neutral1)

        repeat (5) { position ->
            DailyForecastItem(
                item = items[position],
                isCelsius = isCelsius
            )
        }
    }
}

@Composable
fun DailyForecastItem(
    item: DisplayHoursDataModel,
    isCelsius: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.getDayTimeFormat(),
            color = ExtendedColor.White,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )

        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl(),
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp).size(44.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${item.setTempUnit(item.minTemp, isCelsius)} / ${item.setTempUnit(item.maxTemp, isCelsius)}",
                color = ExtendedColor.White,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun OthersWeatherInfo(
    item: DisplayHoursDataModel,
    isCelsius: Boolean = true,
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            InfoBox(
                modifier = Modifier.weight(1f),
                title = "Feels like",
                value = item.setTempUnit(item.feelsLike, isCelsius),
                icon = R.drawable.ic_temperature
            )

            InfoBox(
                modifier = Modifier.weight(1f),
                title = "Humidity",
                value = "${item.humidity}%",
                icon = R.drawable.ic_humidity
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            InfoBox(
                modifier = Modifier.weight(1f),
                title = "Wind",
                value = "${item.wind} m/s",
                icon = R.drawable.ic_wind
            )

            InfoBox(
                modifier = Modifier.weight(1f),
                title = "Visibility",
                value = "${item.visibility} km",
                icon = R.drawable.ic_visibility
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {

            InfoBox(
                modifier = Modifier.weight(1f),
                title = "Sunrise",
                value = "${item.getSunriseTime()} AM",
                icon = R.drawable.ic_sunrise
            )

            InfoBox(
                modifier = Modifier.weight(1f),
                title = "Sunset",
                value = "${item.getSunsetTime()} PM",
                icon = R.drawable.ic_sunrise
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            InfoBox(
                modifier = Modifier.weight(1f),
                title = "Sea Level",
                value = "${item.pressureSea} hPA",
                icon = R.drawable.ic_sea_level
            )

            InfoBox(
                modifier = Modifier.weight(1f),
                title = "Ground Level",
                value = "${item.pressureGround} hPA",
                icon = R.drawable.ic_pressure
            )
        }
    }
}

@Composable
fun InfoBox(
    modifier: Modifier = Modifier,
    title: String = "-",
    icon: Int = R.drawable.ic_calendar,
    value: String = "-"
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = ExtendedColor.BackgroundItemTransparent,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(24.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(35.dp),
            tint = ExtendedColor.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            color = ExtendedColor.Neutral2,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.4f),
                    blurRadius = 3f
                )
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            color = ExtendedColor.White,
            fontWeight = FontWeight.W600,
            fontSize = 18.sp,
            lineHeight = 22.sp,
        )
    }
}

@Composable
fun TempUnitToggle(
    isCelsius: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (isCelsius) "C°" else "F°",
            color = ExtendedColor.White,
            fontWeight = FontWeight.W600,
            fontSize = 18.sp,
            lineHeight = 22.sp,
        )

        Switch(
            checked = isCelsius,
            onCheckedChange = {
                onToggle(!isCelsius)
            },
            modifier = Modifier
                .height(30.dp)
                .scale(0.7f),
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = ExtendedColor.White,
                uncheckedTrackColor = ExtendedColor.Purple80,
                uncheckedBorderColor = Color.Unspecified,
                checkedThumbColor = ExtendedColor.White,
                checkedTrackColor = ExtendedColor.LightBlue,
            )
        )
    }
}