package com.gharieb.forecast.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gharieb.forecast.presentation.components.ForecastFilter
import com.gharieb.forecast.presentation.components.HourlyWeatherDisplay

@Composable
fun ForecastScreen(navHostController: NavHostController) {
    val viewModel: ForecastViewModel = hiltViewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primaryVariant),
        ) {
            IconButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    navHostController.navigateUp()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back button"
                )
            }
            viewModel.state.forecastInfo?.forecast?.forecastDayList.let { data ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Forecast",
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.onSurface,
                    )
                    ForecastFilter { days ->
                        viewModel.handleIntent(ForecastIntent.LoadCurrentWeather(days = days))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyRow(content = {
                        items(data?.size ?: 0) { index ->
                            HourlyWeatherDisplay(
                                weatherData = data?.get(index),
                                modifier = Modifier
                                    .height(100.dp)
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    })
                }
            }

        }

        if (viewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        viewModel.state.errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
