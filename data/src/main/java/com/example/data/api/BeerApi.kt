package com.example.data.api

import com.example.data.model.BeerDetailResponse
import com.example.data.model.BeerResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BeerApi {

    @GET("beers")
    suspend fun getBeerList(@Query("beer_name") beerName: String, @Query("page") page: Int, @Query("per_page") perPage: Int = 20): List<BeerResponse>

    @GET("beers/{id}")
    suspend fun getBeerDetail(@Path("id") id: String): List<BeerDetailResponse>

}