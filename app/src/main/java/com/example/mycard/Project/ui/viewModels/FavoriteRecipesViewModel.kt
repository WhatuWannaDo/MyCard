package com.example.mycard.Project.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mycard.Project.MVVM.Models.FavoriteRecipesModel
import com.example.mycard.Project.data.database.DataBase
import com.example.mycard.Project.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    val getAllRecipes : Flow<List<FavoriteRecipesModel>>
    private val repository : FavoriteRepository

    init {
        val recipesDao = DataBase.getDatabase(application).FavoriteRecipesDAO()
        repository = FavoriteRepository(recipesDao)
        getAllRecipes = repository.getAllRecipes
    }

    fun insertIntoFavoriteRecipes(favoriteRecipesModel: FavoriteRecipesModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertIntoRecipes(favoriteRecipesModel)
        }
    }

    fun deleteFromFavoriteRecipes(favoriteRecipesModel: FavoriteRecipesModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFromRecipes(favoriteRecipesModel)
        }
    }


    class FavoriteViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(FavoriteRecipesViewModel::class.java)) {
                return FavoriteRecipesViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}