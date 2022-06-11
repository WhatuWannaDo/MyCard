package com.example.mycard.Project.MVVM.util

import androidx.room.TypeConverter
import com.example.mycard.Project.MVVM.Models.NutrientsFavoriteModel
import com.example.mycard.Project.data.models.databaseModels.CardModel
import com.google.gson.Gson


class TypeConverterRecipes {
    @TypeConverter
    fun listToJson(value: List<NutrientsFavoriteModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<NutrientsFavoriteModel>::class.java)?.toList()
}

class TypeConverterFolder {
    @TypeConverter
    fun listToJson(value: List<CardModel>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<CardModel>::class.java)?.toList()
}