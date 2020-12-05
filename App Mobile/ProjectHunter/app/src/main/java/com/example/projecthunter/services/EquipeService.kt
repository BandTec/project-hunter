package com.example.projecthunter.services

import com.example.projecthunter.models.EquipeGamerModel
import com.example.projecthunter.models.EquipeJogoModel
import com.example.projecthunter.models.EquipeModel
import com.example.projecthunter.models.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EquipeService {
    @GET("/equipe/nome/{nome}")
    fun getTeamData(@Path("nome") nome:String): Call<List<EquipeModel>>

    @GET("/equipegamer/existe/{idEquipe}/{idGamer}")
    fun isUserOnTeam(@Path("idEquipe") idEquipe:Int, @Path("idGamer") idGamer:Int ) : Call<Void>

    @POST("/equipegamer/")
    fun enterTeam(@Body equipe:EquipeGamerModel) : Call<Void>

    @GET("/equipejogo/equipe/{idEquipe}/")
    fun getTeamGames(@Path("idEquipe") idEquipe:Int) : Call<List<EquipeJogoModel>>

    @GET("/equipegamer/equipe/{nome}/")
    fun getTeamPlayers(@Path("nome") nome:String) : Call<List<EquipeGamerModel>>
}