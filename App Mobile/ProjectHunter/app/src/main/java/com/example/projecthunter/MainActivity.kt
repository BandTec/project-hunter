package com.example.projecthunter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        doLogin(login, senha)
    }

}


    fun doLogin(login:String, senha:String) {

        ApiConnectionUtils().loginService().login(login, senha).enqueue(object: Callback<List<UserModel>> {

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Erro ao efetuar o login", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {

                if(response.code() == 200) {
                    val telaHome = Intent(this@MainActivity, HomeActivity::class.java)
                    var currentUser : Integer = Integer(0)
                    response?.body()?.let {
                        //it é o corpo de retorno da requisição
                        currentUser  = it[0].idGamer;
                        println(currentUser);
                    }
                    telaHome.putExtra("currentUser", currentUser.toString())
                    startActivity(telaHome)
                    }else{
                        Toast.makeText(this@MainActivity, "Login ou senha Inválidos", Toast.LENGTH_SHORT).show()
                    }

                }
            })
        }
    }


