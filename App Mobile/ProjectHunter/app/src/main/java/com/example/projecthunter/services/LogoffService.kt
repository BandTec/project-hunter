package com.example.projecthunter.services
import com.example.projecthunter.models.LoginModel
import com.example.projecthunter.models.UserModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface LogoffService {
    @POST("/gamer/logoff")
    fun logoff() : Call<JsonObject>


}