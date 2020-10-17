package com.example.projecthunter.services

import com.example.projecthunter.models.PartidaModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface MatchesService {
    @GET("/partida/gamer/{id}")
    fun findMatches(@Path("id") id: Int) : Call<List<PartidaModel>>

}