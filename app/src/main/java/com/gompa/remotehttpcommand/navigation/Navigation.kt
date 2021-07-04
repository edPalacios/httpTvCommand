package com.gompa.remotehttpcommand.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gompa.remotehttpcommand.screens.HttpEditorScreen
import com.gompa.remotehttpcommand.screens.IconChooserScreen
import com.gompa.remotehttpcommand.screens.RemoteCommandScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenDirections.remoteCommand.destination) {
        composable(route = ScreenDirections.remoteCommand.destination) {
            RemoteCommandScreen(navController = navController)
        }
        composable(route = ScreenDirections.httpEditor.destination) {
            HttpEditorScreen(navController = navController)
        }

        composable(route = ScreenDirections.iconChooser.destination) {
            IconChooserScreen(navController = navController)
        }

    }
}

