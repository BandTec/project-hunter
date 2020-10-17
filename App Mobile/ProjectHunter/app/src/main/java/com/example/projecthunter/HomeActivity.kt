package com.example.projecthunter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.projecthunter.models.PartidaModel
import com.example.projecthunter.utils.ApiConnectionUtils
import com.example.projecthunter.utils.PostsAdapter
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {

    var nomeJogo = mutableListOf<String>()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val posts: ArrayList<String> = ArrayList()



        var idGamer = intent.extras?.getString("currentUser")
        if (idGamer != null) {
            partidas(idGamer.toInt())
        }

        for (i in 1..nomeJogo.size){
            posts.add("Jogo: ${nomeJogo[i]}")
        }

        rv_partidas.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        rv_partidas.adapter = PostsAdapter(posts)
    }

    fun partidas(idGamer: Int){
        ApiConnectionUtils().matchService().findMatches(idGamer).enqueue(object: Callback<List<PartidaModel>> {

            override fun onFailure(call: Call<List<PartidaModel>>, t: Throwable) {

                Toast.makeText(this@HomeActivity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<PartidaModel>>, response: Response<List<PartidaModel>>) {

                if(response.code() == 200) {
                    var numeroPartidas : Integer = Integer(0)

                    response?.body()?.let {
                        //it é o corpo de retorno da requisição
                        for (partida in it){
                            nomeJogo.add(partida.idJogo.nomeJogo)
                        }

                        println(numeroPartidas);
                    }
                }else{
                    Toast.makeText(this@HomeActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                }

            }
        })
    }



    fun logoff(componente: View){

        ApiConnectionUtils().logoffService().logoff().enqueue(object: Callback<JsonObject> {

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                Toast.makeText(this@HomeActivity, "Erro ao executar comando", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                if(response.code() == 200) {
                    val telaLogin = Intent(this@HomeActivity, MainActivity::class.java)
                    startActivity(telaLogin)
                }else{
                    Toast.makeText(this@HomeActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                }

            }
        })
    }
}
