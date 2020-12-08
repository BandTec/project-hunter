package com.example.projecthunter.models

data class GamerInfoEquipeModel(
    val idEquipeInfo: Int,
    val idEquipe:EquipeModel,
    val idGamer: UserModel,
    val idStatus: UserModel,
    val capitao:Boolean
)