package com.example.projecthunter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.projecthunter.models.LoginModel
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.services.LoginService
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
        doLogin(login, senha)
    }

}


    fun doLogin(login:String, senha:String) {


        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-52-0-122-72.compute-1.amazonaws.com:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val requests = retrofit.create(LoginService::class.java)

        val login = requests.users(login)

        

        login.enqueue(object : Callback<List<UserModel>> {

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {

                Toast.makeText(this@MainActivity, "Deu tudo errado", Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
                Toast.makeText(this@MainActivity, response.body().toString(), Toast.LENGTH_SHORT).show()

                }
        })


//            ApiConnectionUtils().loginService().login(loginModel.email, loginModel.senha).enqueue(object :
//                Callback<LoginModel?> {
//                //Essa função é acionada caso ocorra tudo certo com a requisição
//                override fun onResponse(
//                    call: Call<LoginModel?>?,
//                    response: Response<LoginModel?>?
//                ) {
//
//                    if (!response?.isSuccessful!!) {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Usuário ou senha incorretos",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    } else {
//                        response?.body()?.let {
//                            //it é o corpo de retorno da requisição
//                            val currentUser: LoginModel = it;
//                            println(currentUser);
//                            val telaHome = Intent(this@MainActivity, Home::class.java)
//                            startActivity(telaHome)
//                        }
//                    }
//                }
//
//                //Essa função é acionada caso ocorra dê algum erro durante a requisição
//                override fun onFailure(call: Call<LoginModel?>?, t: Throwable?) {
//                    Log.e("Erro na chamada: ", t?.message + "")
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Ocorreu um problema na comunicação com o servidor",
//                        Toast.LENGTH_LONG
//                    ).show()
//
//                }
//            })
        }

    }


