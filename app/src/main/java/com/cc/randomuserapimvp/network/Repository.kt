package com.cc.randomuserapimvp.network

import com.cc.randomuserapimvp.model.APIData
import com.cc.randomuserapimvp.util.LogToConsole
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Repository {

    private var service:UserService


    init {

        service = createRetrofit().create(UserService::class.java)

    }

    private fun createRetrofit() : Retrofit {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    suspend fun getInfo (total:Int) : APIData {
        val info = service.getInfo(total)
        LogToConsole.log("info : $info")
        return info
    }
}