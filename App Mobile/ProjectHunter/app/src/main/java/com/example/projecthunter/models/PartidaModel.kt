package com.example.projecthunter.models

data class PartidaModel (
    val idPk:Integer,
    val idPartida:Integer,
    val idJogo:JogoModel,
    val idEquipe:Integer,
    val idGamer: UserModel,
    val idPosicao:PosicaoModel,
    val idInfracao:Integer,
    val data:String,
    val hora:String,
    val winner:Boolean
)