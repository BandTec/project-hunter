package com.example.projecthunter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.projecthunter.models.PartidaModel
import com.example.projecthunter.utils.ApiConnectionUtils
import com.example.projecthunter.utils.NewMatchAdapter
import com.example.projecthunter.utils.PostsAdapter
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class HomeActivity : AppCompatActivity() {

    var nomeJogo = mutableListOf<String>()
    var papel = mutableListOf<String>()
    var dataJogo = mutableListOf<String>()
    var horaJogo = mutableListOf<String>()
    val posts: ArrayList<String> = ArrayList()
    var posts2 = mutableListOf<PartidaModel>()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var idGamer = intent.extras?.getString("currentUser")

        if (idGamer != null) {
            partidas(idGamer.toInt())



        }

    }

    @SuppressLint("WrongConstant")
    fun criarCardsAlternativo(partida:List<PartidaModel>?){
        try{



            posts2 = partida as MutableList<PartidaModel>
            rv_partidas.layoutManager = LinearLayoutManager(this@HomeActivity, OrientationHelper.HORIZONTAL, false)
            rv_partidas.adapter = NewMatchAdapter()
            rv_partidas.adapter = PostsAdapter(posts2)
        }catch (e:Exception){
            Toast.makeText(this@HomeActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun partidas(idGamer: Int){
        ApiConnectionUtils().matchService().findMatches(idGamer).enqueue(object: Callback<List<PartidaModel>> {

            override fun onFailure(call: Call<List<PartidaModel>>, t: Throwable) {

                Toast.makeText(this@HomeActivity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<PartidaModel>>, response: Response<List<PartidaModel>>) {

                if(response.code() == 200) {
                    response?.body()?.let {
                        criarCardsAlternativo(response.body())
                    }
                }else{
                    Toast.makeText(this@HomeActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }

            }
        })
    }



    fun config(componente:View){
        var idGamer = intent.extras?.getString("currentUser")
        var usuario = intent.extras?.getString("usuario")
        val telaConfig = Intent(this@HomeActivity, Configuration::class.java)
        telaConfig.putExtra("currentUser", idGamer)
        telaConfig.putExtra("usuario", usuario)
        startActivity(telaConfig)
    }
    fun perfil(componente:View){
        var idGamer = intent.extras?.getString("currentUser")
        var usuario = intent.extras?.getString("usuario")
        val telaPerfil = Intent(this@HomeActivity, PerfilActivity::class.java)
        telaPerfil.putExtra("currentUser", idGamer)
        telaPerfil.putExtra("usuario", usuario)
        startActivity(telaPerfil)
    }

    fun logoff(componente: View){


        ApiConnectionUtils().logoffService().logoff().enqueue(object: Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(this@HomeActivity, "Erro ao executar comando", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if(response.code() == 200) {
                    var preferencias = getPreferences(Context.MODE_PRIVATE)
                    preferencias.edit().remove("usuario").commit()
                    preferencias.edit().remove("login").commit()
                    preferencias.edit().remove("senha").commit()
                    preferencias.edit().remove("currentUser").commit()

                    val telaLogin = Intent(this@HomeActivity, MainActivity::class.java)
                    startActivity(telaLogin)
                }else{
                    Toast.makeText(this@HomeActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                }

            }
        })
    }
}
