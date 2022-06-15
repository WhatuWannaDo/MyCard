package com.example.mycard.Project.data.repository

import com.example.mycard.Project.data.models.databaseModels.FolderModel
import kotlinx.coroutines.flow.Flow

interface FolderRepositoryInt {
    val getAllFolders : Flow<List<FolderModel>>

    suspend fun addFolder(folderModel: FolderModel) : Unit

    suspend fun deleteFolder(folderModel: FolderModel) : Unit
}