package com.example.projecthunter.models

import android.icu.number.IntegerWidth
import java.math.BigInteger

data class UserModel (
    val idGamer:Int?,
    val nome:String,
    val cpf:String,
    val email:String,
    val senha:String,
    val telefone:String,
    val fotoGamer:String?,
    val usuario:String,
    val username:String,
    )