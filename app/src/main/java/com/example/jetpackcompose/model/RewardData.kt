package com.example.jetpackcompose.model

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
data class RewardData(
    val userID:String, val userName:String
)




@Entity(tableName = "reward_table")
data class RewardUser(
    @PrimaryKey()
    @ColumnInfo(name="userID")
    val userID: String,
    @ColumnInfo(name="userName")
    val userName: String,
    //@ColumnInfo(name="userHobbies")
    val userHobbies: List<String>,
    @ColumnInfo(name="sessionToken")
    val sessionToken: String,

    @ColumnInfo(name="userRewards")
    val rewardCategoryServices: List<RewardCategoryServices>?,
   // @ColumnInfo(name="author")
//    val author: String,
//    @ColumnInfo(name="userName")
//    val userName: String,

)

@Entity(tableName = "reward_category_table")
data class RewardCategoryServices(
    @PrimaryKey()
    @ColumnInfo(name="serviceID")
    val serviceID: String,
    @ColumnInfo(name="serviceName")
    val serviceName: String,
    @ColumnInfo(name="servicesDescription")
    val servicesDescription: String,
    @ColumnInfo(name="serviceRewardTotal")
    val serviceRewardTotal: Int,
    @ColumnInfo(name="serviceFormula")
    val serviceFormula: String,
    // @ColumnInfo(name="author")
//    val author: String,
//    @ColumnInfo(name="userName")
//    val userName: String,

)