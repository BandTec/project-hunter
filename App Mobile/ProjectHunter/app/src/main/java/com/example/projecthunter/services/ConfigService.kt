package com.example.projecthunter.services
import com.example.projecthunter.models.GamerInfoJogoModel
import com.example.projecthunter.models.UserModel
import retrofit2.Call
import retrofit2.http.*


interface ConfigService {
    @PUT("/gamer/{id}")
    fun atualizar(@Path("id") id: Integer?, @Body userModel: UserModel) : Call<Void>

    @GET("/gamer/usuario/{usuario}")
    fun getUserData(@Path("usuario") usuario:String): Call<UserModel>

    @PUT("/gamerinfo/{email}")
    fun putGameUser(@Path("emal") email:String, @Body gamer: GamerInfoJogoModel): Call<Void>

}