package com.gharieb.weatherapp.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.gharieb.core.theme.WeatherAppTheme
import com.gharieb.weatherapp.presentation.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }
            WeatherAppTheme(darkTheme = isDarkTheme) {
                Scaffold (
                    topBar = { ToggleThemeButton(isDarkTheme = isDarkTheme, onToggleTheme = { isDarkTheme = !isDarkTheme }) }
                ) { innerPadding ->
                    NavGraph(navController = rememberNavController(),contentPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun ToggleThemeButton(isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primaryVariant)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = isDarkTheme,
            onCheckedChange = { onToggleTheme() },
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "Toggle Theme",
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )
    }
}

