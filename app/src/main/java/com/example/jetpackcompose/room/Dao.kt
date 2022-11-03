package com.example.jetpackcompose

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.example.jetpackcompose.model.Movies
import com.example.jetpackcompose.model.RewardUser
import kotlinx.coroutines.flow.Flow
//var r="reward_table"

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table")
    fun getMovies(): List<Movies>


    @Insert(onConflict =IGNORE)
    fun addMovie(b: Movies)

}


@Dao
interface RewardDao {

    @Query("SELECT * FROM reward_table")
    fun getAllData(): LiveData<List<RewardUser>>

    @Insert(onConflict =IGNORE)
    fun addReward(b: RewardUser)

    @Update
    fun updateReward(b: RewardUser)

    @Delete
    fun deleteReward(b: RewardUser)
}