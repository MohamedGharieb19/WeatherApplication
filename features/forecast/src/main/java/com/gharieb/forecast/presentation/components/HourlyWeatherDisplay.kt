package com.gharieb.forecast.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gharieb.forecast.domain.entity.ForecastDayItem

@Composable
fun HourlyWeatherDisplay(weatherData: ForecastDayItem?, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .background(
                color = if (weatherData?.isToday == true) Color.Gray else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = weatherData?.date?:"",
            color = MaterialTheme.colors.onSurface
        )
        AsyncImage(
            model = weatherData?.day?.condition?.icon,
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = weatherData?.day?.averageTemperature?:"",
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
}