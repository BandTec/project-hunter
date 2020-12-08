package com.example.projecthunter.models

data class EquipeGamerModel(
    val idEquipe: EquipeModel,
    val idGamer: NovoMembroGamer,
    val idStatus: StatusModel,
    val capitao: Boolean
)

data class NovoMembroEquipe(
    val idEquipe:Int
)

data class NovoMembroGamer(
    val idGamer:Int,
    val nome:String?,
    val fotoGamer:String?
)
