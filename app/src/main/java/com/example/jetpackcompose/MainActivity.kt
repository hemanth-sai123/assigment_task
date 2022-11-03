package com.example.jetpackcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text

import androidx.compose.runtime.Composable

import com.example.jetpackcompose.view_model.RewardListViewModel

import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import com.example.jetpackcompose.model.RewardUser

class MainActivity : ComponentActivity() {
    @ExperimentalUnitApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Column {
                val vm by remember { mutableStateOf(RewardListViewModel(application)) }
                //vm.addUser(RewardUser(1,"name","sai"))
                val list  by vm.getRewardObservable().observeAsState()

                MainContent()
                list?.let {
                    RecycleView(this@MainActivity,
                        it
                    )
                }
            }

        }

    }

    @Composable
    fun MainContent() {

        // fetching local context
        val mContext = LocalContext.current

        // Creating a Top bar
        TopAppBar(
            title = { Text("Reward System", color = Color.White) } ,backgroundColor = Color.Blue,
            actions = {

                // Creating Icon button favorites, on click
                // would create a Toast message
                IconButton(onClick = { mContext.startActivity(Intent(mContext, MovieListActivity::class.java)) }) {
                    Icon(Icons.Default.Home, "")
                }
            }
        )
    }
}


@ExperimentalUnitApi
@Composable
fun setUpRow(
    item:RewardUser,
    //index:Int,
mContext:Context
){
        Card(
            modifier = Modifier.padding(10.dp)
                .fillMaxWidth()
                .clickable {
                var intent =Intent(mContext,UserDetailsActivity::class.java)
                //intent.putExtra("position",index)
                RewardDataObject.rewardData=item
                mContext.startActivity(intent)

            }
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.medium,
            elevation = 5.dp,
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Column(Modifier.padding(8.dp)) {
                    Text(
                        text = item.userName,
                        style = MaterialTheme.typography.h4,
                        color = MaterialTheme.colors.onSurface,
                    )
                    Text(
                        text = item.userID,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }


}

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun RecycleView(activity: MainActivity,list: List<RewardUser>){


    LazyColumn{
        list?.size?.let {
            items(it){

                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {



                }
                SwipeToDismiss(
                    state = dismissState,
                    modifier = Modifier
                        .padding(vertical = Dp(1f)),
                    directions = setOf(
                        DismissDirection.EndToStart
                    ),
                    dismissThresholds = { direction ->
                        FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.1f else 0.05f)
                    },
                    background = {
                        val color by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.Default -> Color.White
                                else -> Color.Red
                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier.clickable {

                                list!!.toMutableList().removeAt(it)

                            }
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(20f)),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Delete Icon",
                                modifier = Modifier.scale(scale)
                            )
                        }
                    },
                    dismissContent = {

                        Card(
                            elevation = animateDpAsState(
                                if (dismissState.dismissDirection != null) 4.dp else 0.dp
                            ).value,
                            modifier = Modifier
                                .fillMaxWidth()

                                .align(alignment = Alignment.CenterVertically)
                        ) {
                            setUpRow(list!![it],activity)
                        }
                    }
                )

                Divider(Modifier.fillMaxWidth(), Color.DarkGray)


            }
        }

    }
}