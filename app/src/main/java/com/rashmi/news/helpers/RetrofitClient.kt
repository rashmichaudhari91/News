package com.rashmi.news.helpers


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        val apiInstance: NewsAPI = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(NewsAPI::class.java)
    }
}
