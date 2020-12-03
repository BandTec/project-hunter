package com.example.projecthunter.utils

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.example.projecthunter.services.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiConnectionUtils {
    fun getRetrofitInstance(): Retrofit {

        val path = "http://ec2-34-238-228-208.compute-1.amazonaws.com:8080"

        return Retrofit.Builder()
            .baseUrl(path)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun loginService() : LoginService {
        return getRetrofitInstance().create(LoginService::class.java)
    }

    fun logoffService() : LogoffService {
        return getRetrofitInstance().create(LogoffService::class.java)
    }

    fun recSenha() : RecSenhaService {
        return getRetrofitInstance().create(RecSenhaService::class.java)
    }

    fun cadastroService() : CadastroService {
        return getRetrofitInstance().create(CadastroService::class.java)
    }

    fun configService() : ConfigService {
        return getRetrofitInstance().create(ConfigService::class.java)
    }

    fun matchesService() : MatchesService{
        return getRetrofitInstance().create(MatchesService::class.java)
    }

    fun profileService(): ProfileInfoService{
        return getRetrofitInstance().create(ProfileInfoService::class.java)
    }
}