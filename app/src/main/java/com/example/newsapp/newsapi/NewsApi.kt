package com.example.newsapp.newsapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class NewsApi(val baseUrl:String) {
    private lateinit var client: NewsApiInterface
    init {
        val tikxml = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(TikXmlConverterFactory.create(tikxml))
            .client(okHttpClient)
            .build()
        client = retrofit.create(NewsApiInterface::class.java)
    }

    public fun getNews(): LiveData<NewsResponse<RSS>>{
        var liveData = MutableLiveData<NewsResponse<RSS>>()
        client.getPalestineRss().enqueue(object: Callback<RSS> {
            override fun onResponse(
                call: Call<RSS>,
                response: Response<RSS>
            ) {
                var storeResponse = NewsResponse<RSS>()
                storeResponse.status = NewsResponse.Result.SUCCESS
                storeResponse.response = response.body()
                var items = response.body()?.channel?.items
                liveData.value = storeResponse
            }

            override fun onFailure(call: Call<RSS>, t: Throwable) {
                Log.d("ttt", t.message.toString())
                var storeResponse = NewsResponse<RSS>()
                storeResponse.status = NewsResponse.Result.FAIL
                storeResponse.errorMessage = t.message
                liveData.value = storeResponse
                Log.d("ttt", t.localizedMessage)
            }
        })
        return liveData
    }
    public fun getWorldNews(): LiveData<NewsResponse<RSS>>{
        var liveData = MutableLiveData<NewsResponse<RSS>>()
        client.getWorldRss().enqueue(object: Callback<RSS> {
            override fun onResponse(
                call: Call<RSS>,
                response: Response<RSS>
            ) {
                var storeResponse = NewsResponse<RSS>()
                storeResponse.status = NewsResponse.Result.SUCCESS
                storeResponse.response = response.body()
                var items = response.body()?.channel?.items
                liveData.value = storeResponse
            }

            override fun onFailure(call: Call<RSS>, t: Throwable) {
                Log.d("ttt", t.message.toString())
                var storeResponse = NewsResponse<RSS>()
                storeResponse.status = NewsResponse.Result.FAIL
                storeResponse.errorMessage = t.message
                liveData.value = storeResponse
                Log.d("ttt", t.localizedMessage)
            }
        })
        return liveData
    }
    public fun getIslamicNews(): LiveData<NewsResponse<RSS>>{
        var liveData = MutableLiveData<NewsResponse<RSS>>()
        client.getIslamicRss().enqueue(object: Callback<RSS> {
            override fun onResponse(
                call: Call<RSS>,
                response: Response<RSS>
            ) {
                var storeResponse = NewsResponse<RSS>()
                storeResponse.status = NewsResponse.Result.SUCCESS
                storeResponse.response = response.body()
                var items = response.body()?.channel?.items
                liveData.value = storeResponse
            }

            override fun onFailure(call: Call<RSS>, t: Throwable) {
                Log.d("ttt", t.message.toString())
                var storeResponse = NewsResponse<RSS>()
                storeResponse.status = NewsResponse.Result.FAIL
                storeResponse.errorMessage = t.message
                liveData.value = storeResponse
                Log.d("ttt", t.localizedMessage)
            }
        })
        return liveData
    }
    public fun getEntertainmentNews(): LiveData<NewsResponse<RSS>>{
        var liveData = MutableLiveData<NewsResponse<RSS>>()
        client.getEntertainmentRss().enqueue(object: Callback<RSS> {
            override fun onResponse(
                call: Call<RSS>,
                response: Response<RSS>
            ) {
                var storeResponse = NewsResponse<RSS>()
                storeResponse.status = NewsResponse.Result.SUCCESS
                storeResponse.response = response.body()
                var items = response.body()?.channel?.items
                liveData.value = storeResponse
            }

            override fun onFailure(call: Call<RSS>, t: Throwable) {
                Log.d("ttt", t.message.toString())
                var storeResponse = NewsResponse<RSS>()
                storeResponse.status = NewsResponse.Result.FAIL
                storeResponse.errorMessage = t.message
                liveData.value = storeResponse
                Log.d("ttt", t.localizedMessage)
            }
        })
        return liveData
    }
}