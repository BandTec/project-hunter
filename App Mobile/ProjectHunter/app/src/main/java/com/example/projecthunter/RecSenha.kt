package com.example.projecthunter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_recsenha.*

class RecSenha  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recsenha)
    }

    fun recuperarSenha(componente:View) {
        if (!et_email.text.toString().equals("")) {
            val email = et_email.text.toString()
            if (!email.all { it.isLetter() } || !email.equals(" "))
            {
                Toast.makeText(this, "Por favor preencha seu email corretamente!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Email de recuperação de senha enviado. Verifique seu email.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}