package com.example.mycard.Project.domain.repository

import com.example.mycard.Project.data.database.FolderDAO
import com.example.mycard.Project.data.models.databaseModels.FolderModel
import kotlinx.coroutines.flow.Flow

class FolderRepository(private val dao: FolderDAO) {

    val getAllFolders : Flow<List<FolderModel>> = dao.getAllFolders()

    suspend fun addFolder(folderModel: FolderModel){
        dao.addFolder(folderModel)
    }

    suspend fun deleteFolder(folderModel: FolderModel){
        dao.deleteFolder(folderModel)
    }
}