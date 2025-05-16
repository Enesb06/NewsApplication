package com.example.newsapplication.api



import com.example.newsapplication.model.NewsResponse // Bu data class'ı birazdan oluşturacağız
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") countryCode: String = "us",
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>


}