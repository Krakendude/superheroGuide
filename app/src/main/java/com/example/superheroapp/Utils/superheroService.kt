package com.example.superheroleague.utils

import com.example.superheroapp.data.Superhero
import com.example.superheroapp.data.SuperheroSearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface superheroService {

    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") name: String): SuperheroSearchResponse

    @GET("{charcter-id}")
    suspend fun findSuperheroById(@Path("charcter-id") id: String): Superhero

    companion object {
        fun getInstance(): superheroService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://superheroapi.com/api/7252591128153666/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(superheroService::class.java)
        }
    }
}