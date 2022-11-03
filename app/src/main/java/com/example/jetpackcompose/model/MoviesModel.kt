package com.example.jetpackcompose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class MoviesModel(
    val results: List<Movies>
)

@Entity(tableName = "movie_table")
data class Movies(
    @PrimaryKey()
    @ColumnInfo(name="id")
    val id:Int?=null,
    @ColumnInfo(name="backdrop_path")
    val backdrop_path:String?=null,
    @ColumnInfo(name="original_title")
    val original_title:String?=null,
    @ColumnInfo(name="overview")
    val overview:String?=null
)
