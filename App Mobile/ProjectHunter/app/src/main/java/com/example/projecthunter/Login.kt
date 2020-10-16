package com.example.projecthunter

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projecthunter.models.LoginModel
import com.example.projecthunter.services.LoginService
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


    }

//    fun login(loginModel: LoginModel, v:View){
//
//
//        if (et_usuario.text.isBlank()) {
//            et_usuario.error = "Informe seu email"
//            et_usuario.requestFocus()
//        } else if(et_senha.text.isBlank()){
//            et_senha.error = "Informe sua senha";
//            et_senha.requestFocus()
//        }else {
//
//        }
//            val login = et_usuario.text.toString()
//            val senha = et_senha.text.toString()
//        ApiConnectionUtils().loginService().login(login, senha).enqueue(object: Callback<LoginModel?> {
//            //Essa função é acionada caso ocorra tudo certo com a requisição
//            override fun onResponse(call: Call<LoginModel?>?, response: Response<LoginModel?>?) {
//
//                if(!response?.isSuccessful!!) {
//                    Toast.makeText(applicationContext, "Usuário ou senha incorretos", Toast.LENGTH_LONG).show()
//                } else {
//                    response?.body()?.let {
//                        //it é o corpo de retorno da requisição
//                        val currentUser : LoginModel = it;
//                        println(currentUser);
//                    }
//                }
//            }
//
//            //Essa função é acionada caso ocorra dê algum erro durante a requisição
//            override fun onFailure(call: Call<LoginModel?>?, t: Throwable?) {
//                Log.e("Erro na chamada: ", t?.message + "")
//                Toast.makeText(applicationContext, "Ocorreu um problema na comunicação com o servidor", Toast.LENGTH_LONG).show()
//
//            }
//        })
//    }
}