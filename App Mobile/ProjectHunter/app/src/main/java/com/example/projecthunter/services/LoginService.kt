package com.example.projecthunter.services
import com.example.projecthunter.Login
import com.example.projecthunter.models.LoginModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Headers
import retrofit2.http.POST


interface LoginService {
    @GET("/gamer/{email}/{senha}")
    fun login(@Path("email") email:String, @Path("senha") senha:String) : Call<LoginModel>

}