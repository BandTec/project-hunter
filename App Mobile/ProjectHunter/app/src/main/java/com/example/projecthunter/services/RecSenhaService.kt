package com.example.projecthunter.services
import com.example.projecthunter.models.LoginModel
import com.example.projecthunter.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RecSenhaService {
    @GET("/email/{email}/")
    fun recSenha(@Path("email") email:String) : Call<List<UserModel>>

}