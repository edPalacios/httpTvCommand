package com.gompa.remotehttpcommand.navigation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HttpEditorScreen(navController: NavController) {
    Button(onClick = { navController.popBackStack() }) {
        Text(text = "launch tv Command")
    }
}
