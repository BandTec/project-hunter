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
            val cpf = et_cpf.text.toString()
            val telefone = et_telefone.text.toString()
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

    fun doRegister(nome:String, usuario:String, email:String, cpf:String, telefone:String, senha:String) {

        val userModel = UserModel(null, nome, cpf, email, senha, telefone,null, usuario, email)
        ApiConnectionUtils().cadastroService().cadastro(userModel).enqueue(object:
            Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@Register, "Erro ao efetuar o cadastro", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if(response.code() == 201) {
                    Toast.makeText(this@Register, "Usuário Criado com sucesso!", Toast.LENGTH_SHORT).show()
                    telaHome(usuario)
                }else{
                    Toast.makeText(this@Register, "Erro no cadastro, tente novamente!", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    fun telaHome(usuario: String){
        ApiConnectionUtils().cadastroService().getNovoUsuario(usuario).enqueue(object:
            Callback<List<UserModel>> {

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Toast.makeText(this@Register, "Cadastro efetuado, mas com erro ao enviar a tela Home $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {

                if(response.code() == 200) {

                    var currentUser:Integer = Integer(0)
                    response.body()?.forEach{
                        if (it != null) {
                            currentUser = it.idGamer!!
                        }
                    }
                    val telaHome = Intent(this@Register, HomeActivity::class.java)
                    telaHome.putExtra("currentUser", currentUser)
                    startActivity(telaHome)
                }else{
                    Toast.makeText(this@Register, "Erro no cadastro, tente novamente!", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

}