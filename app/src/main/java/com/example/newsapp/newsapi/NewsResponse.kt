package com.example.newsapp.newsapi

class NewsResponse<T> {
    enum class Result{
        SUCCESS, FAIL
    }
    var status: Result? = null
    var response: T? = null
    var errorMessage:String? = null
}