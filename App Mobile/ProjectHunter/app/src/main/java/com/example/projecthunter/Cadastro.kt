package com.example.projecthunter

import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_recsenha.*
import kotlinx.android.synthetic.main.activity_recsenha.et_email

class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
    }

    fun cadastrar(componente: View) {
        if (!et_usuario.text.toString().equals("") && !et_email.text.toString().equals("") && !et_cpf.text.toString().equals("") && !et_telefone.text.toString().equals("") && !et_senha.text.toString().equals("") && !et_confSenha.text.toString().equals("")) {

            val usuario = et_usuario.text.toString()
            val email = et_email.text.toString()
            val cpf = et_cpf.text.toString().toInt()
            val telefone = et_telefone.text.toString().toInt()
            val senha = et_senha.text.toString()
            val confSenha = et_confSenha.text.toString()

            if (!email.all { it.isLetter() } || !email.equals(" "))
            {
                Toast.makeText(this, "Por favor preencha seu email corretamente!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Preencha seus dados corretamente!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}