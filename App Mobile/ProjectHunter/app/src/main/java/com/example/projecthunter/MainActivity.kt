package com.example.projecthunter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.projecthunter.models.LoginModel
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

fun login(componente: View){

    if (et_usuario.text.isBlank()) {
        et_usuario.error = "Informe seu email"
        et_usuario.requestFocus()
    } else if (et_senha.text.isBlank()) {
        et_senha.error = "Informe sua senha";
        et_senha.requestFocus()
    } else {
        val login = et_usuario.text.toString()
        val senha = et_senha.text.toString()


        val loginModel = LoginModel(login, senha)
        doLogin(loginModel)
    }

}


    fun doLogin(loginModel: LoginModel) {


            ApiConnectionUtils().loginService().login(loginModel.email, loginModel.senha).enqueue(object :
                Callback<LoginModel?> {
                //Essa função é acionada caso ocorra tudo certo com a requisição
                override fun onResponse(
                    call: Call<LoginModel?>?,
                    response: Response<LoginModel?>?
                ) {

                    if (!response?.isSuccessful!!) {
                        Toast.makeText(
                            this@MainActivity,
                            "Usuário ou senha incorretos",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        response?.body()?.let {
                            //it é o corpo de retorno da requisição
                            val currentUser: LoginModel = it;
                            println(currentUser);
                            val telaHome = Intent(this@MainActivity, Home::class.java)
                            startActivity(telaHome)
                        }
                    }
                }

                //Essa função é acionada caso ocorra dê algum erro durante a requisição
                override fun onFailure(call: Call<LoginModel?>?, t: Throwable?) {
                    Log.e("Erro na chamada: ", t?.message + "")
                    Toast.makeText(
                        this@MainActivity,
                        "Ocorreu um problema na comunicação com o servidor",
                        Toast.LENGTH_LONG
                    ).show()

                }
            })
        }


    }


