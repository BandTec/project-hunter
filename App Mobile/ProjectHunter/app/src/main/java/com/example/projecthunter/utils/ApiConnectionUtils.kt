package com.example.projecthunter.utils

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.example.projecthunter.services.LoginService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiConnectionUtils {
     fun getRetrofitInstance(): Retrofit {

         val path = "http://ec2-52-0-122-72.compute-1.amazonaws.com:8080"

         val gson = GsonBuilder()
             .setLenient()
             .create()
         val okHttpClient = OkHttpClient.Builder()
             .readTimeout(100, TimeUnit.SECONDS)
             .connectTimeout(100, TimeUnit.SECONDS)
             .build()

         return Retrofit.Builder()
             .baseUrl(path)
             .client(okHttpClient)
             .addConverterFactory(GsonConverterFactory.create(gson))
             .build()
    }

    fun loginService() : LoginService {
        return getRetrofitInstance().create(LoginService::class.java)
    }
}