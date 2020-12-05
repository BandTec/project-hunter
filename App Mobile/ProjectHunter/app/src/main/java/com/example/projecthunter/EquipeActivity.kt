package com.example.projecthunter

import android.annotation.SuppressLint
import android.graphics.Color
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.projecthunter.models.*
import com.example.projecthunter.utils.ApiConnectionUtils
import com.example.projecthunter.utils.NewMatchAdapter
import com.example.projecthunter.utils.ProfileAdapter
import com.example.projecthunter.utils.TeamAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_equipe.*
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.fragment_image_profile.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EquipeActivity : AppCompatActivity() {
    var idGamer:Int? = null
    var idEquipe:Int? = null

    var listaEquipe: EquipeModel? = null
    var nomeEquipe:String? = null

    var posts = mutableListOf<List<EquipeJogoModel>>()
    var posts2 = mutableListOf<List<EquipeGamerModel>>()
    var posts3 = mutableListOf<List<PartidaModel>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipe)
        bt_juntarse.visibility = View.INVISIBLE

        var equipe:String? = ""
        idGamer = intent.extras?.getInt("idGamer")
        equipe = intent.extras?.getString("equipe")

        if (equipe != null) {
            GlobalScope.launch {
                suspend {
                    inicioComImagem(equipe)
                    delay(3000)
                    withContext(Dispatchers.Main) {
                        idEquipe?.let { idGamer?.let { it1 -> isUserOnTeam(it, it1) } }
                        criarCards()
                    }

                }.invoke()
            }
        }

    }

    @SuppressLint("WrongConstant")
    fun criarCards(){
        try{
            rv_dados_equipe.layoutManager = LinearLayoutManager(this@EquipeActivity, OrientationHelper.HORIZONTAL, false)
            rv_dados_equipe.adapter = TeamAdapter(posts, posts2, posts3)
        }catch (e: java.lang.Exception){
            Toast.makeText(this@EquipeActivity, e.toString()+ posts3.toString() , Toast.LENGTH_SHORT).show()
        }
    }

    fun inicioComImagem(equipe:String){
        ApiConnectionUtils().teamService().getTeamData(equipe).enqueue(object:
            Callback<List<EquipeModel>> {
            override fun onFailure(call: Call<List<EquipeModel>>, t: Throwable) {
                Toast.makeText(this@EquipeActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<EquipeModel>>, response: Response<List<EquipeModel>>) {

                response.body()?.forEach(){
                    if (it?.fotoEquipe != "" || it?.fotoEquipe != null ) {
                        try {
                            Picasso.get().load(it?.fotoEquipe).into(ib_imagem_usuario)
                        }catch (e: Exception){
                            return
                        }
                    }

                    tv_id_nome_equipe.text = it?.nomeEquipe
                    nomeEquipe = it?.nomeEquipe

                    idEquipe = it?.idEquipe

                    getGames(idEquipe!!)
                    getPlayers()
                    idGamer?.let { it1 -> getHistory(it1) }

                    listaEquipe = it
                }

            }
        })
    }

    fun isUserOnTeam(idEquipe:Int,idGamer:Int){
        ApiConnectionUtils().teamService().isUserOnTeam(idEquipe,idGamer).enqueue(object:
            Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EquipeActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }


            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200){
                    bt_juntarse.background = ContextCompat.getDrawable(this@EquipeActivity, R.drawable.botao_desabilitado)
                    bt_juntarse.isEnabled = false
                    bt_juntarse.visibility = View.VISIBLE

                }
            }
        })
    }

    fun juntarSe(componente:View){

            val solicitacao =
                listaEquipe?.idEquipe?.let { EquipeModel(it, listaEquipe!!.nomeEquipe, listaEquipe!!.fotoEquipe) }
                    ?.let {
                        EquipeGamerModel(
                            it,
                            NovoMembroGamer(idGamer!!, null, null), StatusModel(3, null), false)
                    }
        if (solicitacao != null) {
            ApiConnectionUtils().teamService().enterTeam(solicitacao).enqueue(object:
                Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@EquipeActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                    if (response.code() == 201){
                        Toast.makeText(this@EquipeActivity, "Pedido enviado ao capitão da equipe", Toast.LENGTH_LONG).show()
                        bt_juntarse.background = ContextCompat.getDrawable(this@EquipeActivity, R.drawable.botao_desabilitado)
                        bt_juntarse.isEnabled = false
                        bt_juntarse.visibility = View.VISIBLE

                    }else{
                        Toast.makeText(this@EquipeActivity, "Erro ao enviar solicitação", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    fun getGames(idEquipe: Int){
        ApiConnectionUtils().teamService().getTeamGames(idEquipe).enqueue(object:
            Callback<List<EquipeJogoModel>> {
            override fun onFailure(call: Call<List<EquipeJogoModel>>, t: Throwable) {
                Toast.makeText(this@EquipeActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<EquipeJogoModel>>, response: Response<List<EquipeJogoModel>>) {

                if(response.code() == 200) {
                    response?.body()?.forEach() {
                        //getTeams(email, response.body(), id)
                        posts.add(response.body()!!)
                    }
                }else if (response.code() == 204) {

                    posts.add(emptyList())
                }else{
                    Toast.makeText(this@EquipeActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun getPlayers(){
        nomeEquipe?.let {
            ApiConnectionUtils().teamService().getTeamPlayers(it).enqueue(object:
                Callback<List<EquipeGamerModel>> {
                override fun onFailure(call: Call<List<EquipeGamerModel>>, t: Throwable) {
                    Toast.makeText(this@EquipeActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<List<EquipeGamerModel>>, response: Response<List<EquipeGamerModel>>) {

                    if(response.code() == 200) {
                        response?.body()?.forEach() {
                            //getTeams(email, response.body(), id)
                            posts2.add(response.body()!!)
                        }
                    }else if (response.code() == 204) {
                        //getTeams(email, null, id)
                        posts2.add(emptyList())
                    }else{
                        Toast.makeText(this@EquipeActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
    }

    fun getHistory(id:Int){
        ApiConnectionUtils().profileService().findTeamHistory(id).enqueue(object:
            Callback<List<PartidaModel>> {
            override fun onFailure(call: Call<List<PartidaModel>>, t: Throwable) {
                Toast.makeText(this@EquipeActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<PartidaModel>>, response: Response<List<PartidaModel>>) {

                if(response.code() == 200) {
                    response?.body()?.forEach() {

                        posts3.add(response.body()!!)
                    }
                }else if (response.code() == 204) {
                    //criarCardsAlternativo(jogo, equipe, null)
                    posts3.add(emptyList())
                }else
                {
                    Toast.makeText(this@EquipeActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}