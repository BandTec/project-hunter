package com.example.projecthunter.models

data class EquipeJogoModel (
    val idEquipeModel:Int,
    val idEquipe: NovoMembroEquipe,
    val idJogo: JogoEquipeModel,
)

data class JogoEquipeModel(
    val idJogo:Int,
    val nomeJogo:String,
    val fotoJogo:String
)