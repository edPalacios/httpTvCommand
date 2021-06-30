package com.gompa.remotehttpcommand.navigation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun RemoteCommandScreen(navController: NavController) {
    Button(onClick = { navController.navigate(ScreenDirections.httpEditor.destination) }) {
        Text(text = "launch http creator")
    }
}
