package com.example.projecthunter.models

data class PartidaModel(
    val idPk:Integer,
    val idPartida:Integer,
    val idJogo:JogoModel,
    val idEquipe:EquipeModel,
    val idGamer: UserModel,
    val idPosicao:PosicaoModel,
    val idInfracao:InfracaoModel,
    val data:String,
    val hora:String,
    val winner:Boolean
)