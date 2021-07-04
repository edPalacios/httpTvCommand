package com.gompa.remotehttpcommand.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.TaxiAlert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IconChooserScreen(navController: NavController, viewModel: HttpEditorViewModel = viewModel(factory = IconRepositoryViewModelFactory)) {
    // TODO load icons from some library or similar to allow customization
    Scaffold(
        topBar = { Toolbar(navController) }
    ) {
        Icons(navController, viewModel)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Icons(navController: NavController, viewModel: HttpEditorViewModel) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(50.dp),
        modifier = Modifier.fillMaxSize()
    ) {

        items(100) {
            Icon(
                imageVector = Icons.Default.TaxiAlert,
                contentDescription = "icon description",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        viewModel.onSaveIcon(Icons.Default.TaxiAlert)
                        navController.navigateUp()
                    },
            )
        }
    }
}

@Composable
private fun Toolbar(navController: NavController) {
    TopAppBar(
        title = {
            Text(text = "Select an icon")
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, "navigation_up")
            }
        }
    )
}

@Preview
@Composable
fun Preview() {

}
