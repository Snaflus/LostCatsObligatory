package com.example.lostcats.repository

import com.example.lostcats.models.Cat
import retrofit2.Call
import retrofit2.http.*

interface LostCatsService {
    @GET("Cats")
    fun getAllCats(): Call<List<Cat>>

    @POST("Cats")
    fun saveCat(@Body cat: Cat): Call<Cat>

    @DELETE("Cats/{id}")
    fun deleteCat(@Path("id") id: Int): Call<Cat>
}