package com.example.projecthunter.services

import com.example.projecthunter.models.NovaPartidaModel
import com.example.projecthunter.models.PartidaModel
import retrofit2.Call
import retrofit2.http.*


interface MatchesService {
    @GET("/partida/gamer/{id}")
    fun findMatches(@Path("id") id: Int) : Call<List<PartidaModel>>

    @DELETE("/partida/partida/{id}")
    fun deleteMatch(@Path("id") id: Int) : Call<Void>

    @POST("/partida")
    fun createMatch(@Body partida:NovaPartidaModel) : Call<Void>

}