package com.example.jetpackcompose

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

import androidx.compose.foundation.lazy.items

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import com.example.jetpackcompose.model.RewardUser
import java.time.format.TextStyle

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
                    RecycleView(this@MainActivity,LocalContext.current.applicationContext as Application,
                        it
                    )
                }
            }

        }

    }

    @Composable
    fun MainContent() {

        // Create a boolean variable
        // to store the display menu state
        var mDisplayMenu by remember { mutableStateOf(false) }

        // fetching local context
        val mContext = LocalContext.current

        // Creating a Top bar
        TopAppBar(
            title = { Text("Reward System", color = Color.White) } ,backgroundColor = Color.Blue,
            actions = {

                // Creating Icon button favorites, on click
                // would create a Toast message
                IconButton(onClick = { mContext.startActivity(Intent(mContext, CreateActivity::class.java)) }) {
                    Icon(Icons.Default.Add, "")
                }

//                // Creating Icon button for dropdown menu
//                IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
//                    Icon(Icons.Default.MoreVert, "")
//                }

//                // Creating a dropdown menu
//                DropdownMenu(
//                    expanded = mDisplayMenu,
//                    onDismissRequest = { mDisplayMenu = false }
//                ) {
//
//                    // Creating dropdown menu item, on click
//                    // would create a Toast message
//                    DropdownMenuItem(onClick = { Toast.makeText(mContext, "Settings", Toast.LENGTH_SHORT).show() }) {
//                        Text(text = "Settings")
//                    }
//
//                    // Creating dropdown menu item, on click
//                    // would create a Toast message
//                    DropdownMenuItem(onClick = { Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show() }) {
//                        Text(text = "Logout")
//                    }
//                }
            }
        )
    }
}
@Composable
fun rt(){

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
//                Image(
//                    painter = painterResource(id = image),
//                    contentDescription = null,
//                    modifier = Modifier.size(130.dp)
//                        .padding(8.dp),
//                    contentScale = ContentScale.Fit,
//                )
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

//    Row(
//        modifier= Modifier
//            .fillMaxWidth()
//            .fillMaxHeight().clickable {
//                var intent =Intent(mContext,UserDetailsActivity::class.java)
//                //intent.putExtra("position",index)
//                RewardDataObject.rewardData=item
//                mContext.startActivity(intent)
//
//            },
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Start,
//    ) {
//
//        Text(
//            text = item.userName,modifier = Modifier.wrapContentSize(),fontSize = TextUnit(value = 16f,type = TextUnitType.Sp)
//        )
//
//    }
}

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun RecycleView(a: MainActivity, application: Application,list: List<RewardUser>){


    Log.d("jkkjsjdk ","kd "+list.toString())
    //rt()
    LazyColumn{
        list?.size?.let {
            items(it){

                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {

                    Log.d("dismissed ","")

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
                                Log.d("ll;;ls;sdl; "," "+it.toString())
                                list!!.toMutableList().removeAt(it)
                                Log.d("ll;;ls;sdl; "," "+list!!.size.toString())
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
                            setUpRow(list!![it],a)
                        }
                    }
                )

                Divider(Modifier.fillMaxWidth(), Color.DarkGray)


//                Box(
//                    modifier = Modifier
//                        .height(70.dp)
//                        .background(
//                            color = Color.Green
//                        ).padding(5.dp)
//                ) {
////                    Row(modifier = Modifier
////                        .fillParentMaxWidth()
////
////                    ) {
////                        Column() {
////                            Text(text = items!![it].userName.toString(), fontSize = 20.sp)
////                            Text(text = items!![it].userID)
////                        }
////                    }
//////                    Card(modifier = Modifier.height(100.dp)) {
//////
//////                    }
//                }

            }
        }
//        Lazycolumn{
//
//        }
//        itemsIndexed(
//            vm.users
//        ){
//                index,string ->
//            Text(string)
//        }

//        items(1) {
//            Column {
//                Text(it.name)
//                Text("${it.id}")
//            }
//
//        }
    }
}