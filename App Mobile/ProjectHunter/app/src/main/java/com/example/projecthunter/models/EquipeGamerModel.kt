package com.example.projecthunter.models

data class EquipeGamerModel(
    val idEquipe: NovoMembroEquipe,
    val idGamer: NovaPartidaGamerModel,
    val idStatus: NovoMembroIdStatus,
    val capitao: Boolean
)

data class NovoMembroIdStatus(
    val idStatus:Int
)

data class NovoMembroEquipe(
    val idEquipe:Int
)
