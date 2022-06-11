package com.example.mycard.Project.data.models.databaseModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_folder")
data class FolderModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val data : String
)
