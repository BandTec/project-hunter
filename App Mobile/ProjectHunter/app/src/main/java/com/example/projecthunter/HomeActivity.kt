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
import java.lang.Exception


class HomeActivity : AppCompatActivity() {

    var nomeJogo = mutableListOf<String>()
    var papel = mutableListOf<String>()
    var dataJogo = mutableListOf<String>()
    var horaJogo = mutableListOf<String>()
    val posts: ArrayList<String> = ArrayList()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)




        var idGamer = intent.extras?.getString("currentUser")
        if (idGamer != null) {
            partidas(idGamer.toInt())


        }

//        for (i in 1..5){
//            val jogo = "nomeJogo[i]"
//            posts.add("Jogo: $jogo")
//        }




        rv_partidas.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        rv_partidas.adapter = PostsAdapter(posts)
    }

    @SuppressLint("WrongConstant")
    fun criarCards(nomeJogo:String?, papel:String?, data:String?, hora:String?){
        try{
            posts.add("Jogo: $nomeJogo \tHora: $hora \tPapel: $papel \tData: $data")
            
            rv_partidas.layoutManager = LinearLayoutManager(this@HomeActivity, OrientationHelper.HORIZONTAL, false)
            rv_partidas.adapter = PostsAdapter(posts)

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
                        //it é o corpo de retorno da requisição
                        //nomeJogo.add(it[0].idJogo.nomeJogo)

                        for (partida in it){
                            nomeJogo.add(partida.idJogo.nomeJogo)
                            papel.add(partida.idPosicao.posicao)
                            dataJogo.add(partida.data)
                            horaJogo.add(partida.hora)

                        }

                        Toast.makeText(this@HomeActivity, nomeJogo.toString(), Toast.LENGTH_SHORT).show()
                        for(i in 1..nomeJogo.size){
                            criarCards(nomeJogo[i-1], papel[i-1], dataJogo[i-1], horaJogo[i-1])
                        }
                    }
                }else{
                    Toast.makeText(this@HomeActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }

            }
        })
    }



    fun logoff(componente: View){

        ApiConnectionUtils().logoffService().logoff().enqueue(object: Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(this@HomeActivity, "Erro ao executar comando", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

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
