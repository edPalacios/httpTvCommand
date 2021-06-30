package com.gompa.remotehttpcommand.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(block: (NavHostController) -> Unit = {}) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenDirections.remoteCommand.destination) {
        composable(route = ScreenDirections.remoteCommand.destination) {
            RemoteCommandScreen(navController = navController)
        }
        composable(route = ScreenDirections.httpEditor.destination) {
            HttpEditorScreen(navController = navController)
        }

    }
    block(navController)
}

