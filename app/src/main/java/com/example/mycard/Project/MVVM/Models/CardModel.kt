package com.example.mycard.Project.MVVM.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_products")
data class CardModel(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val productName : String,
    val productAmount : String,
    val description : String
)
