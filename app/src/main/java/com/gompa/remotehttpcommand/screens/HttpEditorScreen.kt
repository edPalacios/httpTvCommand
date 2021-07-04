package com.gompa.remotehttpcommand.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gompa.remotehttpcommand.navigation.ScreenDirections
import com.gompa.remotehttpcommand.ui.theme.RemoteHttpCommandTheme

@Composable
fun HttpEditorScreen(navController: NavController, viewModel: HttpEditorViewModel = viewModel(factory = IconRepositoryViewModelFactory)) {
//    Button(onClick = { navController.popBackStack() }) { // TODO handle navigation

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        LazyColumn(
            Modifier
                .weight(0.9f)
        ) {
            item {
                HttpEditorTitleRow(navigationController = navController, viewModel)
                HttpEditorUrlRow()
                HttpMethodSelector()
                HttpCheckBox(text = "Retry")
                HttpCheckBox(text = "Follow redirects")
                HttpHeadersRow()
            }
        }
        HttpBottomRow(context = context, this)
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HttpHeadersRow() {
    Spacer(Modifier.size(16.dp))

    val headers = remember { mutableStateListOf<Header>() }
    val isClicked = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Headers"
        )

        AddHeader(isClicked, headers)
        headers.forEach {
            HeaderValue(header = it, headers = headers)
        }


        AnimatedVisibility(visible = isClicked.value) {
            var header by remember { mutableStateOf(Header()) }

            AlertDialog(
                title = {
                    Text(text = "Create Header")
                },
                text = {
                    Spacer(Modifier.size(16.dp))
                    Column() {
                        LabeledOutlinedTextField(label = "Name") { header = header.copy(name = it) }
                        LabeledOutlinedTextField(label = "Value") {
                            header = header.copy(value = it)
                        }
                    }
                },
                confirmButton = {
                    Button(
                        enabled = header.name.isNotBlank() && header.value.isNotBlank(),
                        onClick = {
                            headers.add(header)
                            isClicked.value = false
                        }) {
                        Text(text = "Save")
                    }
                },
                dismissButton = {
                    Button(onClick = { isClicked.value = false }) {
                        Text(text = "Dismiss")
                    }
                },
                onDismissRequest = { isClicked.value = false }
            )
        }
    }
}

@Composable
private fun HeaderValue(header: Header, headers: SnapshotStateList<Header>) {
    Row(modifier = Modifier.padding(start = 8.dp)) {
        Text(text = header.name, modifier = Modifier.align(Alignment.CenterVertically))
        Text(
            text = header.value,
            Modifier
                .align(Alignment.CenterVertically)
        )
        Icon(Icons.Default.RemoveCircle, contentDescription = "Remove header",
            Modifier
                .padding(start = 8.dp)
                .clickable { headers.remove(header) }
        )
    }
}

@Composable
private fun AddHeader(isClicked: MutableState<Boolean>, headers: SnapshotStateList<Header>) {

    Row {
        IconButton(onClick = { isClicked.value = !isClicked.value }) {
            Icon(Icons.Default.AddCircle, contentDescription = "add_header_icon")
        }

        Text(
            text = "Add header",
            Modifier
                .align(Alignment.CenterVertically)
                .alpha(0.2f)
        )
    }
}

@Composable
fun HttpBottomRow(context: Context, columnScope: ColumnScope) {
    columnScope.apply {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            HttpButton(text = "Delete", context = context, rowScope = this)
            HttpButton(text = "Save", context = context, rowScope = this)
        }
    }
}

@Composable
fun HttpButton(text: String, context: Context, rowScope: RowScope) {
    rowScope.apply {
        Button(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            onClick = { Toast.makeText(context, "save and close", Toast.LENGTH_LONG).show() }) {
            Text(text = text)
        }
    }
}

@Composable
fun HttpEditorTitleRow(navigationController: NavController, viewModel: HttpEditorViewModel) {
    Row {
        LabeledOutlinedTextField(label = "Title")

        Text(
            text = "Icon", Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )
        IconButton(
            onClick = { navigationController.navigate(ScreenDirections.iconChooser.destination) },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            val icon by viewModel.icon.observeAsState()
            Icon(icon ?: Icons.Default.AddAPhoto, contentDescription = "Icon chooser")
        }
    }
}

@Composable
fun HttpEditorUrlRow() {
    Row(modifier = Modifier.fillMaxWidth()) {
        LabeledOutlinedTextField(label = "Url", modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun LabeledOutlinedTextField(
    label: String,
    modifier: Modifier = Modifier,
    storeInput: ((String) -> Unit)? = null
) {
    val input = remember { mutableStateOf("") }
    OutlinedTextField(
        value = input.value,
        onValueChange = {
            input.value = it
            storeInput?.invoke(it)
        },
        label = { Text(label) },
        modifier = modifier
    )
}

@Composable
fun HttpMethodSelector() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Method", modifier = Modifier.align(Alignment.CenterHorizontally))

        val list = remember {
            mutableStateOf(
                listOf(
                    MethodItem("GET"),
                    MethodItem("POST"),
                    MethodItem("PUT"),
                    MethodItem("PATCH")
                )
            )
        }

        LazyRow(Modifier.align(Alignment.CenterHorizontally)) {

            itemsIndexed(items = list.value) { pos, item ->
                MethodText(item, list)
            }
        }
    }
}

@Composable
fun MethodText(item: MethodItem, list: MutableState<List<MethodItem>>) {

    val color by animateColorAsState(
        targetValue = if (list.value.find { it.name == item.name }?.isSelected == true) Color.Green else Color.LightGray,
        animationSpec = tween(durationMillis = 250)
    )
    Box(modifier = Modifier
        .padding(5.dp)
        .border(width = 2.dp, color = color, RoundedCornerShape(corner = CornerSize(20.dp)))
        .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp))
        .clickable {
            list.value = list.value.map {
                it.copy(isSelected = it.name == item.name && !item.isSelected)
            }
        }
    ) {
        Text(
            text = item.name,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)

        )
    }
}

@Composable
fun HttpCheckBox(text: String) {
    val isChecked = remember { mutableStateOf(false) }
    Row {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = { isChecked.value = it }
        )
        Text(text = text, modifier = Modifier.padding(start = 8.dp))
    }
}

@ExperimentalFoundationApi
@Preview()
@Composable
fun DefaultPreview() {
    RemoteHttpCommandTheme {
        val context = LocalContext.current
        Column(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                Modifier
                    .padding(16.dp)
                    .weight(0.9f)
            ) {
                item {
                    HttpEditorTitleRow(rememberNavController(), HttpEditorViewModel(IconRepository))
                    HttpEditorUrlRow()
                    HttpMethodSelector()
                    HttpCheckBox(text = "Retry")
                    HttpCheckBox(text = "Follow redirects")
                    HttpHeadersRow()
                }
            }

            HttpBottomRow(context = context, columnScope = this)
        }
    }
}

data class Header(val name: String = "", val value: String = "")

data class MethodItem(val name: String, var isSelected: Boolean = false)


class HttpEditorViewModel(private val iconRepository: IconRepository) : ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _headers = MutableLiveData<List<Header>>()
    val headers: LiveData<List<Header>> = _headers

    val icon: LiveData<ImageVector> = iconRepository.icon

    fun onSaveIcon(icon: ImageVector) { // TODO replace ImageVector
       iconRepository.onSaveIcon(icon)
    }


}

@Suppress("UNCHECKED_CAST")
object IconRepositoryViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HttpEditorViewModel::class.java)) {
            return HttpEditorViewModel(IconRepository) as T
        }
        throw IllegalArgumentException("Cannot create view model from class: $modelClass")
    }
}


// TODO Dummy repository in memory to speed up things. This should be done with Room keeping the ref of the icon from a lib
object IconRepository : LiveData<ImageVector>() {
    private val _icon = MutableLiveData<ImageVector>() // TODO replace VectorDrawable
    val icon: LiveData<ImageVector> = _icon

    fun onSaveIcon(icon: ImageVector) {
        _icon.value = icon
    }

}