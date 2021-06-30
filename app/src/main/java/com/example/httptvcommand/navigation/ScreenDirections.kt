package com.example.httptvcommand.navigation

import androidx.navigation.compose.NamedNavArgument

interface Direction {
    val arguments: List<NamedNavArgument>
    val destination: String
}

object ScreenDirections {
    val remoteCommand = object : Direction {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = "tvCommand"
    }

    val httpEditor = object : Direction {
        override val arguments: List<NamedNavArgument> = emptyList()
        override val destination: String = "httpEditor"
    }
}