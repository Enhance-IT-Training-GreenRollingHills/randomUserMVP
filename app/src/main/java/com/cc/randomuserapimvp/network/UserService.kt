package com.cc.randomuserapimvp.network

import com.cc.randomuserapimvp.model.APIData
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName

interface UserService  {


    @GET("api/")
    suspend fun getInfo(@Query ("results") results:Int):APIData
}