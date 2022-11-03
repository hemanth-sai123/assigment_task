package com.example.jetpackcompose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import kotlinx.coroutines.launch

class UserDetailsActivity: ComponentActivity() {

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //Text("UserDetailsActivity")
            UserDetails(this)
        }

    }
}

@ExperimentalUnitApi
@Composable
fun UserDetails(mContext:Context){
    var reward =RewardDataObject.rewardData
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {

        Text(
            text =reward!!.userID,modifier = Modifier.wrapContentSize(),fontSize = TextUnit(value = 25f,type = TextUnitType.Sp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text =reward!!.userName,modifier = Modifier.wrapContentSize(),fontSize = TextUnit(value = 15f,type = TextUnitType.Sp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                var intent = Intent(mContext,RewardActivity::class.java)

                mContext.startActivity(intent)
        }) {
            Text("Reward Details")
        }

    }
}