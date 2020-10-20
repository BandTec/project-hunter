package com.example.projecthunter.services
import com.example.projecthunter.models.LoginModel
import com.example.projecthunter.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body


interface ConfigService {
    @PUT("/gamer/{id}")
    fun atualizar(@Body body: UserModel) : Call<List<UserModel>>

}