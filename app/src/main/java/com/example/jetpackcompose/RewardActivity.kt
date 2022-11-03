package com.example.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.jetpackcompose.model.RewardData

class RewardActivity: ComponentActivity() {

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //Text("UserDetailsActivity")
            RewardDetals()
        }

    }
}

@ExperimentalUnitApi
@Composable
fun RewardDetals(){
    var reward =RewardDataObject.rewardData
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {

        Text(
            text = reward!!.userName,
            modifier = Modifier.wrapContentSize(),
            fontSize = TextUnit(value = 25f, type = TextUnitType.Sp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Reward Services",
            modifier = Modifier.wrapContentSize(),
            fontSize = TextUnit(value = 15f, type = TextUnitType.Sp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            reward.rewardCategoryServices?.size?.let {
                items(it) {
                    Column() {
                        Text(
                            text = reward.rewardCategoryServices!![it].serviceName,
                            modifier = Modifier.wrapContentSize(),
                            fontSize = TextUnit(value = 15f, type = TextUnitType.Sp)
                        )
                    }
                }

            }

        }
    }
}