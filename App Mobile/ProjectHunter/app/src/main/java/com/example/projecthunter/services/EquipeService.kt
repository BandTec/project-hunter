package com.example.projecthunter.services

import com.example.projecthunter.models.EquipeModel
import com.example.projecthunter.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EquipeService {
    @GET("/equipe/nome/{nome}")
    fun getTeamData(@Path("nome") nome:String): Call<List<EquipeModel>>

    @GET("/equipegamer/existe/{idEquipe}/{idGamer}")
    fun isUserOnTeam(@Path("idEquipe") idEquipe:Int, @Path("idGamer") idGamer:Int ) : Call<Void>
}