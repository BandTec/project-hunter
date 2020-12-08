package com.example.projecthunter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.projecthunter.models.*
import com.example.projecthunter.utils.ApiConnectionUtils
import com.example.projecthunter.utils.NewMatchAdapter
import com.example.projecthunter.utils.PostsAdapter
import com.example.projecthunter.utils.ProfileAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_configuration.*
import kotlinx.android.synthetic.main.activity_configuration.bt_perfil
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.activity_perfil.rv_dados
import kotlinx.android.synthetic.main.fragment_image_profile.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilActivity : AppCompatActivity() {

    var posts = mutableListOf<List<GamerInfoJogoModel>>()
    var posts2 = mutableListOf<List<GamerInfoEquipeModel>>()
    var posts3 = mutableListOf<List<PartidaModel>>()
    var id: Int? = null

    var qtdPartidas:Int = 0
    var qtdVitorias:Int = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        bt_perfil.setColorFilter(getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN)
        var usuario:String? = ""
        usuario = intent.extras?.getString("usuario")


        if (usuario != null) {
            GlobalScope.launch {
                suspend {
                    inicioComImagem(usuario)
                    delay(3000)
                    withContext(Dispatchers.Main) {
                        id?.let { criarCards(it) }
                    }
                }.invoke()
            }
        }
    }

    @SuppressLint("WrongConstant")
    fun criarCards(id:Int){

        var rating:Double = ((qtdVitorias.toDouble()*5) / qtdPartidas.toDouble() +0.00)
        tv_rating.text = rating.toString()

        try{
            rv_dados.layoutManager = LinearLayoutManager(this@PerfilActivity, OrientationHelper.HORIZONTAL, false)
            rv_dados.adapter = NewMatchAdapter()
            rv_dados.adapter = ProfileAdapter(posts, posts2, posts3, id)
        }catch (e: java.lang.Exception){
            Toast.makeText(this@PerfilActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    fun inicioComImagem(usuario:String){
        ApiConnectionUtils().configService().getUserData(usuario).enqueue(object:
            Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(this@PerfilActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {

                with (response.body()){
                    if (this?.fotoGamer != "" || this?.fotoGamer != null ) {
                        try {
                            Picasso.get().load(this?.fotoGamer).into(ib_imagem_usuario)
                        }catch (e: Exception){
                            return
                        }
                    }

                    id = this?.idGamer?.toInt()
                    tv_id_nome.text = this?.nome
                    this?.email?.let { getGames(it) }
                    this?.email?.let { getTeams(it) }
                    this?.idGamer?.let { getHistory(it.toInt()) }

                }

            }
        })
    }


    fun getGames(email: String){
        ApiConnectionUtils().profileService().findUserGames(email).enqueue(object:
            Callback<List<GamerInfoJogoModel>> {
            override fun onFailure(call: Call<List<GamerInfoJogoModel>>, t: Throwable) {
                Toast.makeText(this@PerfilActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<GamerInfoJogoModel>>, response: Response<List<GamerInfoJogoModel>>) {

                if(response.code() == 200) {
                    response?.body()?.forEach() {
                        //getTeams(email, response.body(), id)
                        posts.add(response.body()!!)
                    }
                }else if (response.code() == 204) {
                    //getTeams(email, null, id)
                    posts.add(emptyList())
                }else{
                    Toast.makeText(this@PerfilActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun getTeams(email: String){
        ApiConnectionUtils().profileService().findUserTeams(email).enqueue(object:
            Callback<List<GamerInfoEquipeModel>> {
            override fun onFailure(call: Call<List<GamerInfoEquipeModel>>, t: Throwable) {
                Toast.makeText(this@PerfilActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<GamerInfoEquipeModel>>, response: Response<List<GamerInfoEquipeModel>>) {

                if(response.code() == 200) {
                    response?.body()?.let {
                        //getHistory(id, response.body(), jogo)
                        posts2.add(response.body()!!)
                    }
                }else if (response.code() == 204) {
                    posts2.add(emptyList())
                }else{
                    Toast.makeText(this@PerfilActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    fun getHistory(id:Int){
        ApiConnectionUtils().profileService().findTeamHistory(id).enqueue(object:
            Callback<List<PartidaModel>> {
            override fun onFailure(call: Call<List<PartidaModel>>, t: Throwable) {
                Toast.makeText(this@PerfilActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<PartidaModel>>, response: Response<List<PartidaModel>>) {

                if(response.code() == 200) {
                    response?.body()?.forEach() {
                        qtdPartidas++
                        if(it.winner!!){
                            qtdVitorias++
                        }
                        posts3.add(response.body()!!)
                    }
                }else if (response.code() == 204) {
                    //criarCardsAlternativo(jogo, equipe, null)
                    posts3.add(emptyList())
                }else
                {
                    Toast.makeText(this@PerfilActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun config(componente: View){
        var idGamer = intent.extras?.getString("currentUser")
        var usuario = intent.extras?.getString("usuario")
        val telaConfig = Intent(this@PerfilActivity, ConfigurationActivity::class.java)
        telaConfig.putExtra("currentUser", idGamer)
        telaConfig.putExtra("usuario", usuario)
        startActivity(telaConfig)
    }

    fun logoff(componente: View){


        ApiConnectionUtils().logoffService().logoff().enqueue(object: Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(this@PerfilActivity, "Erro ao executar comando", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if(response.code() == 200) {
                    var preferencias = getPreferences(Context.MODE_PRIVATE)
                    preferencias.edit().remove("usuario").commit()
                    preferencias.edit().remove("login").commit()
                    preferencias.edit().remove("senha").commit()
                    preferencias.edit().remove("currentUser").commit()

                    val telaLogin = Intent(this@PerfilActivity, MainActivity::class.java)
                    startActivity(telaLogin)
                }else{
                    Toast.makeText(this@PerfilActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                }

            }
        })
    }
}