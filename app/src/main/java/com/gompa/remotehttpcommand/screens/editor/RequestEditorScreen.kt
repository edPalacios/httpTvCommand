package com.gompa.remotehttpcommand.screens.editor

import android.content.Context
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gompa.models.HttpMethod
import com.gompa.remotehttpcommand.navigation.ScreenDirections
import com.gompa.remotehttpcommand.ui.theme.RemoteHttpCommandTheme

@Composable
fun RequestEditorScreen(
    navController: NavController,
    viewModel: RequestEditorViewModel = viewModel(factory = RequestEditorViewModelFactory)
) {
//    Button(onClick = { navController.popBackStack() }) { // TODO handle navigation

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        LazyColumn(
            Modifier.weight(0.9f)
        ) {
            item {
                RequestEditorTitleRow(navigationController = navController, viewModel)
                RequestEditorUrlRow(viewModel)
                RequestEditorBodyRow(viewModel = viewModel)
                RequestMethodSelector(viewModel)
                RequestCheckBox(text = "Retry") { viewModel.onRetryChanged(it) }
                RequestCheckBox(text = "Follow redirects") { viewModel.onFollowRedirectsChanged(it) }
                RequestTimeout(viewModel)
                RequestHeadersRow(viewModel)
            }
        }
        RequestBottomRow(
            context = context,
            this,
            onSave = { viewModel.saveRequest{ navController.popBackStack() } },
            onDelete = { viewModel.deleteRequest() })
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RequestHeadersRow(viewModel: RequestEditorViewModel) {
    Spacer(Modifier.size(16.dp))

    val headers = remember { mutableStateListOf<Header>() } // TODO check if we can get rid of this
//    val headers : List<Header> by viewModel.headers.observeAsState(emptyList())
    val isClicked = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Headers"
        )

        AddHeader(isClicked)
        headers.forEach {
            HeaderValue(header = it) {
                headers.remove(it)
                viewModel.onHeaderRemoved(it)
            }
        }

        AnimatedVisibility(visible = isClicked.value) {
            var header by remember { mutableStateOf(Header()) }

            AlertDialog(
                title = {
                    Text(text = "Create Header")
                },
                text = {
                    Spacer(Modifier.size(16.dp))
                    Column {
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
                            viewModel.onHeaderAdded(header)
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
private fun HeaderValue(header: Header, onHeaderRemoved: () -> Unit) {
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
                .clickable { onHeaderRemoved() }
        )
    }
}

@Composable
private fun AddHeader(isClicked: MutableState<Boolean>) {

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
fun RequestBottomRow(
    context: Context,
    columnScope: ColumnScope,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    columnScope.apply {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            RequestButton(text = "Save", context = context, rowScope = this) { onSave() }
            RequestButton(text = "Delete", context = context, rowScope = this) { onDelete() }
        }
    }
}

@Composable
fun RequestButton(text: String, context: Context, rowScope: RowScope, onClick: () -> Unit) {
    rowScope.apply {
        Button(
            modifier = Modifier
                .padding(16.dp)
                .weight(1f),
            onClick = { onClick() }) {
            Text(text = text)
        }
    }
}

@Composable
fun RequestEditorTitleRow(navigationController: NavController, viewModel: RequestEditorViewModel) {
    Row {
        LabeledOutlinedTextField(label = "Title", storeInput = { viewModel.onTitleChanged(it) })

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
fun RequestEditorUrlRow(viewModel: RequestEditorViewModel) {
    LabeledOutlinedTextField(
        label = "Url",
        modifier = Modifier.fillMaxWidth(),
        storeInput = { viewModel.onUrlChanged(it) })
}

@Composable
fun RequestEditorBodyRow(viewModel: RequestEditorViewModel) {
    var isChecked by remember { mutableStateOf(false) }
    Row(modifier = Modifier.fillMaxWidth()) {

        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Text(
            text = "Request body",
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        )

        LabeledOutlinedTextField(
            label = "Body",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            storeInput = { viewModel.onRequestBodyChanged(it, isEnabled = isChecked) },
            enabled = isChecked
        )

    }
}

@Composable
fun LabeledOutlinedTextField(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
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
        modifier = modifier,
        enabled = enabled
    )
}

@Composable
fun RequestMethodSelector(viewModel: RequestEditorViewModel) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Method", modifier = Modifier.align(Alignment.CenterHorizontally))

        val list = remember {
            mutableStateOf(
                listOf(
                    MethodItem(HttpMethod.GET.name),
                    MethodItem(HttpMethod.POST.name),
                    MethodItem(HttpMethod.PUT.name),
                    MethodItem(HttpMethod.PATCH.name),
                    MethodItem(HttpMethod.OPTIONS.name)
                )
            )
        }

        LazyRow(Modifier.align(Alignment.CenterHorizontally)) {
            itemsIndexed(items = list.value) { pos, item ->
                MethodText(item, list, viewModel)
            }
        }
    }
}

/**
 * @param list - internal composable state to track the selected item and change color
 * @param viewModel - screen state holder to host the value of the selected item
 */
@Composable
fun MethodText(
    item: MethodItem,
    list: MutableState<List<MethodItem>>,
    viewModel: RequestEditorViewModel
) {

    val color by animateColorAsState(
        targetValue = if (list.value.find { it.name == item.name }?.isSelected == true) Color.Green else Color.LightGray,
        animationSpec = tween(durationMillis = 250)
    )

    Box(modifier = Modifier
        .padding(5.dp)
        .border(width = 2.dp, color = color, RoundedCornerShape(corner = CornerSize(20.dp)))
        .background(color = Color.Transparent, shape = RoundedCornerShape(20.dp))
        .clickable {
            val isMethodSelected: (MethodItem) -> Boolean =
                { it.name == item.name && !item.isSelected }
            list.value = list.value.map { it.copy(isSelected = isMethodSelected(it)) }
            viewModel.onMethodSelected(list.value.find { isMethodSelected(it) })
        }
    ) {
        Text(
            text = item.name,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun RequestCheckBox(text: String, storeState: (Boolean) -> Unit) {
    val isChecked = remember { mutableStateOf(false) }
    Row {
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                storeState(it)
            }
        )
        Text(text = text, modifier = Modifier.padding(start = 8.dp))
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RequestTimeout(viewModel: RequestEditorViewModel) {
    Spacer(modifier = Modifier.size(16.dp))
    val timeOut: String by viewModel.timeOut.observeAsState("30")
    OutlinedTextField(
        value = timeOut,
        onValueChange = { viewModel.onTimeOutChanged(it) },
        label = { Text(text = "Time out") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.5f),
        textStyle = TextStyle(textAlign = TextAlign.Center),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )

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
                    val viewModel = RequestEditorViewModel(RequestEditorModule.iconRepository(), RequestEditorModule.requestRepository())
                    RequestEditorTitleRow(rememberNavController(), viewModel)
                    RequestEditorUrlRow(viewModel)
                    RequestEditorBodyRow(viewModel)
                    RequestMethodSelector(viewModel)
                    RequestCheckBox(text = "Retry") {}
                    RequestCheckBox(text = "Follow redirects") {}
                    RequestTimeout(viewModel)
                    RequestHeadersRow(viewModel)
                }
            }

            RequestBottomRow(context = context, columnScope = this, {}, {})
        }
    }
}
