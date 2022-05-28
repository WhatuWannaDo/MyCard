package com.example.mycard.Project.Network

import com.example.mycard.Project.MVVM.Models.GroceryModel
import com.example.mycard.Project.MVVM.Models.HeadModel
import com.example.mycard.Project.MVVM.Models.MenuItemsModel
import com.example.mycard.Project.MVVM.Models.RecipesModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.net.URL

const val API_KEY = "e86726a441b243d1b05e5203a81d7b7c"

interface DictAPI {

    @GET("food/ingredients/autocomplete")
    suspend fun getTextFromDictApi(
        @Query("apiKey") apiKey : String,
        @Query("query") text : String,
        @Query("number") value : String
        ): Response<List<HeadModel>>

    @GET("food/products/search")
    suspend fun getGroceryFromApi(
        @Query("apiKey") apiKey: String,
        @Query("query") query : String,
        @Query("number") value: String
    ) : Response<GroceryModel>

    @GET("food/menuItems/search")
    suspend fun getMenuItemsApi(
        @Query("apiKey") apiKey: String,
        @Query("query") query : String,
        @Query("number") value: String
    ) : Response<MenuItemsModel>

    @GET("recipes/complexSearch")
    suspend fun getRecipesApi(
        @Query("apiKey") apiKey: String,
        @Query("query") query : String,
        @Query("cuisine") cuisine : String?, // The cuisine(s) of the recipes. One or more, comma separated (will be interpreted as 'OR'). https://spoonacular.com/food-api/docs#Cuisines
        @Query("diet") diet : String?, // The diet for which the recipes must be suitable. https://spoonacular.com/food-api/docs#Diets
        @Query("intolerances") intolerances : String?, // A comma-separated list of intolerances. All recipes returned must not contain ingredients that are not suitable for people with the intolerances entered.
        @Query("equipment") equipment : String?, // The equipment required. Multiple values will be interpreted as 'or'. For example, value could be "blender, frying pan, bowl".
        @Query("includeIngredients") includeIngredients : String?, // A comma-separated list of ingredients that should/must be used in the recipes.
        @Query("excludeIngredients") excludeIngredients : String?, // A comma-separated list of ingredients or ingredient types that the recipes must not contain.
        @Query("type") type : String?, // https://spoonacular.com/food-api/docs#Meal-Types
        //would be added soon @Query("instructionsRequired") instructionsRequired : Boolean, // Whether the recipes must have instructions.
        //would be added soon @Query("addRecipeInformation") addRecipeInformation : Boolean, // If set to true, you get more information about the recipes returned.
        @Query("titleMatch") titleMatch : String?, // Enter text that must be found in the title of the recipes.
        @Query("maxReadyTime") maxReadyTime : String?, // The maximum time in minutes it should take to prepare and cook the recipe.
        @Query("minCarbs") minCarbs : String?, // "10"	The minimum amount of carbohydrates in grams the recipe must have.
        @Query("maxCarbs") maxCarbs : String?, // "100"	The maximum amount of carbohydrates in grams the recipe can have.
        @Query("minProtein") minProtein : String?, // "10"	The minimum amount of protein in grams the recipe must have.
        @Query("maxProtein") maxProtein : String?, // "100"	The maximum amount of protein in grams the recipe can have.
        @Query("minCalories") minCalories : String?, // "50"	The minimum amount of calories the recipe must have.
        @Query("maxCalories") maxCalories : String?, // "800"	The maximum amount of calories the recipe can have.
        @Query("minFat") minFat : String?, // "1"	The minimum amount of fat in grams the recipe must have.
        @Query("maxFat") maxFat : String?, // "100"	The maximum amount of fat in grams the recipe can have.
        @Query("minSugar") minSugar : String?, // "0"	TThe minimum amount of sugar in grams the recipe must have.
        @Query("maxSugar") maxSugar : String?, // "100"	The maximum amount of sugar in grams the recipe must have.
        @Query("number") value: String
    ) : Response<RecipesModel>

    //sending received url which one received from getRecipesApi func, for transfer object in navigation
    @GET("")
    suspend fun getByURL(
        @Url url: String
    ): Response<RecipesModel>
}