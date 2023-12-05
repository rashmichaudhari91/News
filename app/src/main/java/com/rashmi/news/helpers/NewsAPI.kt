package com.rashmi.news.helpers

import com.rashmi.news.model.APIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    //f5e9f9050da742e2aa02da0d9ce75f59

    @GET("everything?q=android")
    fun getNews(
        @Query("page") page: Int?,
        @Query("apiKey") apiKey: String?
    ): Call<APIResponse>

}