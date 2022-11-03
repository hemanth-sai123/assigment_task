package com.example.jetpackcompose

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetpackcompose.model.Movies
import com.example.jetpackcompose.model.MoviesModel
import com.example.jetpackcompose.model.RewardUser
import com.example.jetpackcompose.services.RetroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class RewardRepository(private val dao: RewardDao,private val movieDao:MovieDao) {
    fun getRewardFromRoom(): LiveData<List<RewardUser>> =dao.getAllData()
//
//    suspend fun getBookFromRoom(id: Int): Flow<RewardUser>

    suspend fun addRewardToRoom(b: RewardUser)=dao.addReward(b)
    suspend fun addMoviesToRoom(b: Movies)=movieDao.addMovie(b)

    fun getMoviesFromRoom(): List<Movies>? =movieDao.getMovies()

    fun getAllMovies(
        moviesListLiveData: MutableLiveData<List<Movies>>,
        repository: RewardRepository,
        viewModelScope: CoroutineScope
    ) {
        var call = RetroService.getBoomServiceRetroInstance().getMovieResults("0d2ca8a57687b6cc7527daba6d8e441f")
        call.enqueue(object : retrofit2.Callback<MoviesModel> {
            override fun onResponse(call: Call<MoviesModel>?, response: Response<MoviesModel>?) {


                if (response!!.isSuccessful) {
                    val  result =response.body().results
                    moviesListLiveData.value =result
                    for (i in result){
                        viewModelScope.launch {
                            addMoviesToRoom(i)
                        }

                    }
                    //Log.d("success "," "+response.body().toString())

                }

            }

            override fun onFailure(call: Call<MoviesModel>?, t: Throwable?) {

                moviesListLiveData.value =getMoviesFromRoom()
                Log.d("error ","  "+t.toString())
            }

        })


    }
//
//    suspend fun updateBookInRoom(book: RewardUser)
//
//    suspend fun deleteBookFromRoom(book: RewardUser)
}