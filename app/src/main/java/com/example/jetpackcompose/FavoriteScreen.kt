package com.example.jetpackcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.model.Movies

class FavoriteScreen : ComponentActivity() {

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column() {
                TopAppBar(
                    title = { Text("Favorite Screen", color = Color.White) })

                var list =MoviesList.favorite
                list?.let { GridFavorite(it,this@FavoriteScreen) }
            }
        }

    }
}

@ExperimentalFoundationApi
@Composable
fun GridFavorite(list: List<Movies>,mContext: Context){

    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(list.size) {



            val movieList =list[it]
            val painter = rememberAsyncImagePainter("http://image.tmdb.org/t/p/w500${movieList.backdrop_path}")
            val descrption =movieList.overview
            val title =movieList.original_title
            Box(modifier = Modifier
                .clickable {
                    MoviesList.totalList=movieList
                    var intent =Intent(mContext, MovieDescriptionActivity::class.java)
                    mContext.startActivity(intent)
                }
                .fillMaxSize(0.5f)
                .padding(16.dp)){
                ImageCard(painter = painter, contentDescription = descrption?:"" , title = title?:"",false)

//            }
            }
        }
    }
}
