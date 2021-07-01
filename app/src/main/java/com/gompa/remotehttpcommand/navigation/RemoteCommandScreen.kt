package com.gompa.remotehttpcommand.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RemoteCommandScreen(
    navController: NavController,
    viewModel: RemoteCommandViewModel = viewModel()
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(50.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(100) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Localized description",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigate(ScreenDirections.httpEditor.destination) },
            )
        }
    }

}

class RemoteCommandViewModel() : ViewModel() {

}

