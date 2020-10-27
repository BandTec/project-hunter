package com.example.projecthunter.services
import com.example.projecthunter.models.LoginModel
import com.example.projecthunter.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Body


interface CadastroService {
    @POST("/gamer/criar")
    fun cadastro(@Body body: UserModel) : Call<Void>

    @GET("/gamer/usuario/{usuario}")
    fun getNovoUsuario(@Path("usuario") usuario:String) : Call<List<UserModel>>

}