package com.example.mycard.Project.data.database

import androidx.room.*
import com.example.mycard.Project.data.models.databaseModels.FolderModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDAO {

    @Query("SELECT * FROM recipe_folder")
    fun getAllFolders() : Flow<List<FolderModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFolder(folderModel: FolderModel)

    @Delete
    suspend fun deleteFolder(folderModel: FolderModel)


}