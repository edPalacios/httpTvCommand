package com.gompa.remotehttpcommand.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.gompa.models.Request
import com.gompa.remotehttpcommand.navigation.ScreenDirections
import com.gompa.remotehttpcommand.screens.editor.RequestEditorModule
import com.gompa.remotehttpcommand.screens.editor.RequestEditorViewModel
import com.gompa.remotehttpcommand.screens.editor.repository.RequestRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RemoteCommandScreen(
    navController: NavController,
    viewModel: RemoteCommandViewModel = viewModel(factory = RemoteCommandViewModelFactory)
) {

    val requestState = viewModel.requestsState.observeAsState()

    LazyVerticalGrid(
        cells = GridCells.Adaptive(50.dp),
        modifier = Modifier.fillMaxSize()
    ) {

        items(requestState.value?.size ?: 0) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Localized description",
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.navigate(ScreenDirections.httpEditor.destination) },
            )
        }
    }

}

class RemoteCommandViewModel(private val requestRepository: RequestRepository) : ViewModel() {

    private val _requestsState : MutableLiveData<List<Request>> = MutableLiveData()

    val requestsState by lazy {
        viewModelScope.launch {
            requestRepository.getRequests()
                .catch { Log.e("RemoteCommandViewModel", "Error fetching request from database: $it") }
                .mapLatest { if(it.isEmpty()) listOf(Request()) else it } // at the moment just ensure we return one default item
                .collect {
                    _requestsState.value = it
                }

        }
        _requestsState
    }
}


@Suppress("UNCHECKED_CAST")
object RemoteCommandViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RemoteCommandViewModel::class.java)) {
            return RemoteCommandViewModel(
                RequestEditorModule.requestRepository()
            ) as T
        }
        throw IllegalArgumentException("Cannot create view model from class: $modelClass")
    }
}
