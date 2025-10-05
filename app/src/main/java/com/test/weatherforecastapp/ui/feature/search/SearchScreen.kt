package com.test.weatherforecastapp.ui.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.weatherforecastapp.R
import com.test.weatherforecastapp.model.DisplayCityModel
import com.test.weatherforecastapp.ui.common.FetchAPIError
import com.test.weatherforecastapp.ui.common.LoadingDialog
import com.test.weatherforecastapp.ui.common.NoInternetConnection
import com.test.weatherforecastapp.ui.theme.ExtendedColor

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onCitySelected: (DisplayCityModel) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        ExtendedColor.BlueGradient1,
                        ExtendedColor.BlueGradient2
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    )

    Scaffold (
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            Column {
                SearchTextField(
                    onSearchClick = { cityNameInput ->
                        if (isConnected) {
                            viewModel.searchCityList(cityName = cityNameInput)
                        }
                    },
                    onClearSearchClick = {
                        viewModel.clearSearchList()
                    }
                )

                if (!isConnected) {
                    NoInternetConnection()
                }
            }
        },
        containerColor = ExtendedColor.White

    ) { paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues)
        ) {
            when (uiState) {
                is SearchModelUiState.InitState -> {
                    InitSearchLocation()
                }

                is SearchModelUiState.EmptyState -> {}

                is SearchModelUiState.LoadingState -> {
                    LoadingDialog()
                }

                is SearchModelUiState.FetchDataSuccess -> {
                    SearchLocationList(
                        items = (uiState as SearchModelUiState.FetchDataSuccess).data,
                        onItemSelected = { cityInfo ->
                            onCitySelected(cityInfo)
                        }
                    )
                }

                is SearchModelUiState.NotFoundState -> {
                    NoSearchLocationFound()
                }

                is SearchModelUiState.FetchDataError -> {
                    FetchAPIError()
                }
            }
        }
    }
}

@Composable
fun SearchTextField(
    onSearchClick: (String) -> Unit,
    onClearSearchClick: () -> Unit
) {
    var cityNameInput by rememberSaveable { mutableStateOf("") }

    Column {
        Text(
            text = "Weather",
            color = ExtendedColor.White,
            fontWeight = FontWeight.W600,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            ExtendedColor.BlueGradient1,
                            ExtendedColor.BlueGradient2
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
                .padding(start = 16.dp, top = 16.dp, bottom = 12.dp),
            fontSize = 30.sp,
            lineHeight = 34.sp,
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            BasicTextField(
                value = cityNameInput,
                onValueChange = { cityNameInput = it },
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f)
                    .background(
                        color = ExtendedColor.SurfaceContainer,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = ExtendedColor.SurfaceContainer,
                        shape = RoundedCornerShape(12.dp)
                    ),
                textStyle = TextStyle(
                    color = ExtendedColor.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                cursorBrush = SolidColor(ExtendedColor.HintColor),
                maxLines = 1,
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(
                                horizontal = 10.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = null,
                            tint = ExtendedColor.HintColor,
                            modifier = Modifier.size(14.dp)
                        )

                        Box (
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(4f)
                        ) {
                            if (cityNameInput.isEmpty()) {
                                Text(
                                    text = "Search for a city",
                                    color = ExtendedColor.HintColor,
                                    fontSize = 16.sp
                                )
                            }

                            innerTextField()
                        }

                        if (cityNameInput.isNotEmpty()) {
                            Icon(
                                painter = painterResource(R.drawable.ic_close),
                                contentDescription = null,
                                tint = ExtendedColor.HintColor,
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(11.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = {
                                            cityNameInput = ""
                                            onClearSearchClick()
                                        }
                                    )
                            )
                        }
                    }
                }
            )

            Text(
                text = "Search",
                color = ExtendedColor.Black,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            if(cityNameInput.isNotEmpty()) {
                                onSearchClick(cityNameInput)
                            }
                        }
                    ),
                fontSize = 16.sp
            )
        }

        HorizontalDivider(thickness = 1.dp, color = ExtendedColor.SurfaceContainer)
    }

}

@Composable
fun InitSearchLocation(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_map_pin),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
                text = "Search for a city",
                color = ExtendedColor.Black,
                fontWeight = FontWeight.W500,
                fontSize = 18.sp
        )
    }
}

@Composable
fun NoSearchLocationFound(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_result_not_found),
            contentDescription = null,
            tint = ExtendedColor.HintColor,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "No results",
            color = ExtendedColor.DarkGrey,
            fontWeight = FontWeight.W600,
            fontSize = 20.sp,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "No results found for this keyword.",
            color = ExtendedColor.DarkGrey,
            fontWeight = FontWeight.W400,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SearchLocationList(
    items: List<DisplayCityModel>,
    onItemSelected: (DisplayCityModel) -> Unit
) {
    LazyColumn (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        items(items.count()) { position ->
            SearchLocationListItem(
                item = items[position],
                onItemSelected = { cityInfo ->
                    onItemSelected(cityInfo)
                }
            )
        }
    }
}


@Composable
fun SearchLocationListItem(
    item: DisplayCityModel,
    onItemSelected: (DisplayCityModel) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onItemSelected(item)
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_location),
            contentDescription = null,
            tint = ExtendedColor.RedPin,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(25.dp)
        )
        Column {
            Text(
                text = item.name,
                color = ExtendedColor.Black,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                lineHeight = 20.sp
            )

            Text(
                text = item.country,
                color = ExtendedColor.HintColor,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }

    HorizontalDivider(thickness = 1.dp, color = ExtendedColor.SurfaceContainer)
}