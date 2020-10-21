package com.example.projecthunter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun cadastrar(componente: View) {
        if (et_usuario.text.toString().equals("") && et_email.text.toString()
                .equals("") && et_cpf.text.toString().equals("") && et_telefone.text.toString()
                .equals("") && et_senha.text.toString().equals("") && et_confSenha.text.toString()
                .equals("")
        ) {

            Toast.makeText(this, "Por favor preencha seus dados corretamente!", Toast.LENGTH_SHORT)
                .show()

        } else {
            val nome = et_nome.text.toString()
            val usuario = et_usuario.text.toString()
            val email = et_email.text.toString()
            val cpf = et_cpf.text.toString().toInt()
            val telefone = et_telefone.text.toString().toInt()
            val senha = et_senha.text.toString()
            val confSenha = et_confSenha.text.toString()

            if (senha != confSenha)
            {
                Toast.makeText(this, "Por favor, digite sua senha corretamente!", Toast.LENGTH_SHORT).show()
            }
            else{
                doRegister(nome,usuario,email,cpf,telefone,senha)
            }
        }
    }

    fun doRegister(nome:String, usuario:String, email:String, cpf:Int, telefone:Int, senha:String) {

//        ApiConnectionUtils().cadastroService().cadastro(nome,usuario,email,cpf,telefone,senha).enqueue(object:
//            Callback<List<UserModel>> {
//
//            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
//                Toast.makeText(this@Register, "Erro ao efetuar o cadastro", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
//
//                if(response.code() == 201) {
//                    val telaLogin = Intent(this@Register, MainActivity::class.java)
//                    var currentUser : Integer = Integer(0)
//                    response?.body()?.let {
//                        //it é o corpo de retorno da requisição
//                        currentUser  = it[0].idGamer;
//                        println(currentUser);
//                    }
//                    telaLogin.putExtra("currentUser", currentUser.toString())
//                    startActivity(telaLogin)
//                }else{
//                    Toast.makeText(this@Register, "Erro no cadastro, tente novamente!", Toast.LENGTH_SHORT).show()
//                }
//
//            }
//        })

    }

}