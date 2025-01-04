package com.gharieb.weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gharieb.weather.presentation.WeatherState

@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    state.weatherInfo?.current?.let { data ->
        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        text = state.weatherInfo.location?.name ?: "",
                        modifier = Modifier.weight(3f),
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = data.lastUpdated,
                        modifier = Modifier.weight(2f),
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                AsyncImage(
                    model = data.condition?.icon,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = data.temperature,
                    fontSize = 50.sp,
                    color = MaterialTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = data.condition?.text ?: "",
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
        Button(
            onClick = {
                onButtonClick()
            }
        ) {
            Text("Go To Forecast", color = MaterialTheme.colors.onSurface)
        }
    }
}