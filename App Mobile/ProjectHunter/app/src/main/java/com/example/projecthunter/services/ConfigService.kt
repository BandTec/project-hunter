package com.example.projecthunter.services
import com.example.projecthunter.models.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path


interface ConfigService {
    @PUT("/gamer/{id}")
    fun atualizar(@Path("id") id: Integer?, @Body userModel: UserModel) : Call<Void>

    @GET("/gamer/usuario/{usuario}")
    fun getUserData(@Path("usuario") usuario:String): Call<List<UserModel>>

}