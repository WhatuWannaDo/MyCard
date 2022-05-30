package com.example.mycard.Project.MVVM.util

import androidx.room.TypeConverter
import com.example.mycard.Project.MVVM.Models.NutrientsFavoriteModel
import com.google.gson.Gson


class TypeConverterRecipes {
    @TypeConverter
    fun listToJson(value: List<NutrientsFavoriteModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<NutrientsFavoriteModel>::class.java).toList()
}