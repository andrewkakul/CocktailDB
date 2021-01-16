package com.example.cocktaildb.network

import com.example.cocktaildb.model.CocktailDBResponse
import com.example.cocktaildb.model.FiltersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktaildbEndpoint {
 @GET(value = "filter.php")
 fun getCocktails(
     @Query("c") searchRequest: String): Call<CocktailDBResponse>

 @GET(value = "list.php")
 fun getFilters(
     @Query("c") searchRequest: String): Call<FiltersResponse>
}