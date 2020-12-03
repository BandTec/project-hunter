package com.example.projecthunter.models

data class GamerInfoJogoModel(
    val idGamerInfo: Int,
    val idGamer:UserModel,
    val idJogo: JogoModel,
    val idPosicao: PosicaoModel
)