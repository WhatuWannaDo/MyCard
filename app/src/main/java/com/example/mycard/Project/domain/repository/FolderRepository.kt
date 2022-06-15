package com.example.mycard.Project.domain.repository

import com.example.mycard.Project.data.database.FolderDAO
import com.example.mycard.Project.data.models.databaseModels.FolderModel
import com.example.mycard.Project.data.repository.FolderRepositoryInt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderRepository @Inject constructor(private val dao: FolderDAO) : FolderRepositoryInt {

    override val getAllFolders : Flow<List<FolderModel>> = dao.getAllFolders()

    override suspend fun addFolder(folderModel: FolderModel){
        dao.addFolder(folderModel)
    }

    override suspend fun deleteFolder(folderModel: FolderModel){
        dao.deleteFolder(folderModel)
    }
}