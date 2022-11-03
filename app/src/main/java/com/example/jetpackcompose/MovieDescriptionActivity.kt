package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.model.Movies

class MovieDescriptionActivity: ComponentActivity() {
    @ExperimentalUnitApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column() {
                TopAppBar(
                    title = { Text("Description Screen", color = Color.White) })

                var list =MoviesList.totalList
                list?.let { MovieDescription(it) }


            }
        }

    }
}
@ExperimentalUnitApi
@Composable
fun MovieDescription(list: Movies) {
    var movieList =list
    Column(modifier = Modifier.fillMaxSize() ) {
        val painter = rememberAsyncImagePainter("http://image.tmdb.org/t/p/w500${movieList.backdrop_path}")
        val descrption =movieList.overview
        val title =movieList.original_title
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(1.0f)
        ){
            Image(
                painter =painter,
                contentDescription=descrption,
                //contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
//            }
        }
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(1.0f).padding(20.dp)
        ){
            Column() {
                movieList.original_title?.let {
                    Text(
                        text = it,modifier = Modifier.wrapContentSize(),fontSize = TextUnit(
                            value = 16f,
                            type = TextUnitType.Sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                movieList.overview?.let {
                    Text(
                        text = it,modifier = Modifier.wrapContentSize(),fontSize = TextUnit(value = 16f,type = TextUnitType.Sp)
                    )
                }
            }

        }

    }
}