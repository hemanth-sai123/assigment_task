package com.example.jetpackcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*

import androidx.compose.material.Text

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jetpackcompose.model.Movies
import com.example.jetpackcompose.view_model.RewardListViewModel

class MovieListActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var arrayList =ArrayList<Movies>()
        setContent {
            Column() {
                TopAppBar(
                    title = { Text("MoviesScreen", color = Color.White) },
                    actions = {

                        // Creating Icon button favorites, on click
                        // would create a Toast message
                        IconButton(onClick = {
                            MoviesList.favorite=arrayList
                            startActivity(Intent(this@MovieListActivity, FavoriteScreen::class.java))
                         }) {
                            Icon(Icons.Default.Favorite, "")
                        }
                    }
                    )

                val vm by remember { mutableStateOf(RewardListViewModel(application)) }
                vm.getMovieResults()

                val list by vm.getAllMoviesObservable().observeAsState()

                list?.let { Grid(it,arrayList,this@MovieListActivity) }
            }
        }

    }

}
@ExperimentalFoundationApi
@Composable
fun Grid(list: List<Movies>, arrayList: ArrayList<Movies>,mContext:Context){
    //val numbers = (0..20).toList()

    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(list.size) {

            var isSelected by remember { mutableStateOf(false)}
            var index =list[it].id?.let { it1 -> checkFavorite(arrayList, it1) }

            if(isSelected){

                if(index==-1){
                    arrayList.add(list[it])
                }

            }else{
                if(index!=-1){
                    if (index != null) {
                        arrayList.removeAt(index)
                    }
                }
            }


            val movieList =list[it]
            val painter = rememberAsyncImagePainter("http://image.tmdb.org/t/p/w500${movieList.backdrop_path}")
            val descrption =movieList.overview
            val title =movieList.original_title
            Box(modifier = Modifier.combinedClickable(
                onClick = {
                    var intent =Intent(mContext, MovieDescriptionActivity::class.java)
                    //intent.putExtra("Status","Favorite")
                    MoviesList.totalList=list[it]
                    mContext.startActivity(intent)

                },
                onLongClick = {
                    isSelected = !isSelected
                    if(isSelected){

                    }
                },
            ).fillMaxSize(0.5f)
                .padding(16.dp)){
                ImageCard(painter = painter, contentDescription = descrption?:"" , title = title?:"",isSelected)

        }
    }}
}

fun checkFavorite(arrayList: ArrayList<Movies>,id:Int): Int {

    val index = arrayList.indexOfFirst{
        it.id == id
    }
    return index
}


@Composable
fun ImageCard(
    painter:Painter,
    contentDescription:String,
    title:String,
    isSelected: Boolean
){

    Card(
        modifier =Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation=if(isSelected) 30.dp else 0.dp
    ){

        Box(
            modifier =Modifier.height(200.dp)
        ) {


            Image(
                painter =painter,
                contentDescription=contentDescription,
                contentScale =ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),

                        startY = 300f
                    )
                )
            ){

            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp), contentAlignment = Alignment.BottomStart){

                Text(text = title, style = TextStyle(color = Color.White, fontSize = 16.sp,))
            }
        }
    }
}
