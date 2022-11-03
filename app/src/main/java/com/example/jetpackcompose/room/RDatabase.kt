package com.example.jetpackcompose

import android.content.Context
import androidx.room.*
import com.example.jetpackcompose.model.Movies
import com.example.jetpackcompose.model.RewardCategoryServices
//import androidx.room.RoomDatabase
import com.example.jetpackcompose.model.RewardUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConvertersString {
    @TypeConverter
    fun gettingListFromString(genreIds: String): List<String>? {
        val list: MutableList<String> = ArrayList()
        val array = genreIds.split(",").toTypedArray()
        for (s in array) {
            if (!s.isEmpty()) {
                list.add(s)
            }
        }
        return list
    }

    @TypeConverter
    fun writingStringFromList(list: List<String>): String? {
        var genreIds = ""
        for (i in list) {
            genreIds += ",$i"
        }
        return genreIds
    }
}

class Converters {

    @TypeConverter
    fun listToJsonString(value: List<RewardCategoryServices>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<RewardCategoryServices>::class.java).toList()
}

@Database(entities = [RewardUser::class, Movies::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class,ConvertersString::class)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun rewardDao(): RewardDao
    abstract fun moviesDao(): MovieDao

    companion object{
        @Volatile
        private var INSTANCE:RoomDataBase?=null

        fun getDatabase(context: Context):RoomDataBase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBase::class.java,"database"
                ).allowMainThreadQueries().build()
                INSTANCE =instance
                return instance
            }
        }
    }
}