package com.example.cocktaildb.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    const val  BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    fun createService(): CocktaildbEndpoint {
        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val service = retrofit.create(CocktaildbEndpoint::class.java)
        return service
    }
}