package com.example.projecthunter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_recuperacao_senha.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecuperacaoSenha : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperacao_senha)
    }

    fun recuperarSenha(componente:View) {
        if (et_email.text.isBlank()) {
            et_email.error = "Informe seu email"
            et_email.requestFocus()
        }else {
            val email = et_email.text.toString()
            doRecSenha(email)
        }
    }

    fun doRecSenha(email:String) {

        ApiConnectionUtils().recSenha().recSenha(email).enqueue(object:
            Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@RecuperacaoSenha, "Erro ao enviar email de recuperação de senha", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if(response.code() == 204) {
                    Toast.makeText(this@RecuperacaoSenha, "As instruções de recuperação de senha enviadas para seu email!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@RecuperacaoSenha, "Email inválido ${response.code()}", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

}