package com.example.projecthunter.models


data class NovaPartidaModel(
    val idJogo: NovaPartidaJogoModel,
    val idGamer: NovaPartidaGamerModel?,
    val idPosicao:NovaPartidaPosicaoModel,
    val data:String,
    val hora:String,
)