package com.example.mycard.Project.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mycard.Project.data.database.DataBase
import com.example.mycard.Project.data.models.databaseModels.FolderModel
import com.example.mycard.Project.domain.repository.FolderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FolderViewModel(application: Application) : AndroidViewModel(application) {

    val getAllFolders : Flow<List<FolderModel>>
    private val repository : FolderRepository

    init {
        val folderDao = DataBase.getDatabase(application).folderDAO()
        repository =  FolderRepository(folderDao)
        getAllFolders = repository.getAllFolders
    }

    suspend fun addFolder(folderModel: FolderModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFolder(folderModel)
        }
    }

    suspend fun deleteFolder(folderModel: FolderModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFolder(folderModel)
        }
    }
    class FolderViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(FolderViewModel::class.java)) {
                return FolderViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}