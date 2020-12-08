package com.example.projecthunter.services

import com.example.projecthunter.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProfileInfoService {

    @GET("gamerinfo/gamer/{email}")
    fun findUserGames(@Path("email") email: String) : Call<List<GamerInfoJogoModel>>

    @GET("/equipegamer/gamer/{email}")
    fun findUserTeams(@Path("email") email: String) : Call<List<GamerInfoEquipeModel>>

    @GET("partida/gamer/antes/{id}")
    fun findTeamHistory(@Path("id") id: Int) : Call<List<PartidaModel>>




}