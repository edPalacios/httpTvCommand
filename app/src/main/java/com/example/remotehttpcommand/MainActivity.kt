package com.example.remotehttpcommand

import android.R
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.remotehttpcommand.ui.theme.RemoteHttpCommandTheme
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RemoteHttpCommandTheme {
                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
                    Grid()
                }
            }
        }
    }


@ExperimentalFoundationApi
@Composable
fun Grid() {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val area = (screenHeight / 50) * (screenWidth / 50)
//    val res = area / 64
    println("///// ${screenHeight / 50}")
    println("///// ${screenWidth / 50}")
    println("///// $area")
//    println("///// $res")
    LazyVerticalGrid(cells = GridCells.Adaptive(minSize = 64.dp)) {

        items(count = area) {
            Box(modifier = Modifier.fillMaxSize()) {
                var offsetX by remember { mutableStateOf(0f) }
                var offsetY by remember { mutableStateOf(0f) }

                Image(
                    painter = painterResource(id = R.drawable.ic_input_add),
                    contentDescription = "contentDescription",
                    modifier = Modifier
                        .padding(5.dp)
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        .background(Color.Blue)
                        .size(50.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                change.consumeAllChanges()
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        }
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RemoteHttpCommandTheme {
        Grid()
    }
}