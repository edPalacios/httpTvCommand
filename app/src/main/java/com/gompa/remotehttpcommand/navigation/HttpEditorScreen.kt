package com.gompa.remotehttpcommand.navigation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HttpEditorScreen(navController: NavController, viewModel: HttpEditorViewModel = viewModel()) {
    Button(onClick = { navController.popBackStack() }) {
        Text(text = "launch tv Command")
    }
}


class HttpEditorViewModel() : ViewModel() {

}