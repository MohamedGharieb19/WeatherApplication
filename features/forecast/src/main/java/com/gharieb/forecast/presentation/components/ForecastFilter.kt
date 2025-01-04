package com.gharieb.forecast.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ForecastFilter(onDaysSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("1", "3", "5", "7").forEach { days ->
            Text(
                text = "$days Days",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(8.dp)
                    .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .clickable { onDaysSelected(days) },
                textAlign = TextAlign.Center
            )
        }
    }
}