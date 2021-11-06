package com.example.newsapp.newsapi

import retrofit2.Call
import retrofit2.http.*

interface NewsApiInterface {

    @GET("palestine.xml")
    public fun getPalestineRss(): Call<RSS>
    @GET("world.xml")
    public fun getWorldRss(): Call<RSS>
    @GET("islam.xml")
    public fun getIslamicRss(): Call<RSS>
    @GET("entertainment.xml")
    public fun getEntertainmentRss(): Call<RSS>


}