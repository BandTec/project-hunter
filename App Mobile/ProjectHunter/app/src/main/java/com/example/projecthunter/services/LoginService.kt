package com.example.projecthunter.services
import com.example.projecthunter.models.LoginModel
import com.example.projecthunter.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface LoginService {
    @GET("/gamer/{email}/{senha}")
    fun login(@Path("email") email:String, @Path("senha") senha:String) : Call<List<UserModel>>


    @GET("/gamer/usuario/{usuario}")
    fun users(@Path("usuario") usuario:String) : Call<List<UserModel>>

}