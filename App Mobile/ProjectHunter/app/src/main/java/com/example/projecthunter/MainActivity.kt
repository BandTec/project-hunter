package com.example.projecthunter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.view.View
import android.widget.Toast
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {

    var preferencias: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferencias = getPreferences(Context.MODE_PRIVATE)
        val usuario = preferencias?.getString("usuario", null)
        val senha = preferencias?.getString("senha", null)
        val currentUser = preferencias?.getString("currentUser", null)
        if (usuario != null && senha != null && currentUser != null){
            val telaHome = Intent(this@MainActivity, HomeActivity::class.java)
            telaHome.putExtra("currentUser", currentUser)
            telaHome.putExtra("usuario",usuario)
            startActivity(telaHome)
        }
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

    fun doCadastro(componente: View){
        val telaCad = Intent(this@MainActivity, Register::class.java)
        startActivity(telaCad)
    }

    fun doRecSenha(componente: View){
        val telaRecuperacaoSenha = Intent(this@MainActivity, RecuperacaoSenha::class.java)
        startActivity(telaRecuperacaoSenha)
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
                    var usuario:String = ""
                    var senha:String=""
                    response?.body()?.let {
                        //it é o corpo de retorno da requisição
                        currentUser  = it[0].idGamer!!;
                        usuario = it[0].usuario
                        senha = it[0].senha
                        println(currentUser);
                    }
                    telaHome.putExtra("currentUser", currentUser.toString())
                    telaHome.putExtra("usuario",usuario)
                    val editor = preferencias?.edit()
                    editor?.putString("usuario", usuario)
                    editor?.putString("currentUser", currentUser.toString())
                    editor?.putString("senha", senha)
                    editor?.commit()
                    startActivity(telaHome)
                    }else{
                        Toast.makeText(this@MainActivity, "Login ou senha Inválidos", Toast.LENGTH_SHORT).show()
                    }

                }
            })
        }
    }


