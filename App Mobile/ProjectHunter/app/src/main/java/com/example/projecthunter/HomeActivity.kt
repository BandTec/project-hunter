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
import com.example.projecthunter.models.*
import com.example.projecthunter.utils.ApiConnectionUtils
import com.example.projecthunter.utils.NewMatchAdapter
import com.example.projecthunter.utils.PostsAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_header.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class HomeActivity : AppCompatActivity(), PostsAdapter.ClickEventHandler {


    var posts2 = mutableListOf<PartidaModel>()
    var id: Int? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //finish();
        //startActivity(getIntent());
        setContentView(R.layout.activity_home)

        var idGamer = intent.extras?.getString("currentUser")

        if (idGamer != null) {
            partidas(idGamer.toInt())
            id = idGamer.toInt()
        }

        iv_busca.setOnClickListener{
            var idGamer = intent.extras?.getString("currentUser")
            var usuario = intent.extras?.getString("usuario")
            val telaBusca = Intent(this@HomeActivity, SearchActivity::class.java)
            telaBusca.putExtra("currentUser", idGamer)
            telaBusca.putExtra("usuario", usuario)
            startActivity(telaBusca)
        }

    }

    @SuppressLint("WrongConstant")
    fun criarCardsAlternativo(partida:List<PartidaModel>?, idGamer: Int){
        try{
            posts2 = partida as MutableList<PartidaModel>
            posts2.add(PartidaModel(0,1,
                JogoModel(1, "1", 1, "null"),
                EquipeModel(1, "null", "null"),
                UserModel(null, "null", "null", "null", "null",
                    "null", "null", "null", "null"),
                PosicaoModel(1, "null"),
                InfracaoModel(1,"null"), "1", "1", true))
            rv_partidas.layoutManager = LinearLayoutManager(this@HomeActivity, OrientationHelper.HORIZONTAL, false)
            rv_partidas.adapter = NewMatchAdapter()
            rv_partidas.adapter = PostsAdapter(posts2, idGamer)
        }catch (e:Exception){
            Toast.makeText(this@HomeActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun partidas(idGamer: Int){
        ApiConnectionUtils().matchesService().findMatches(idGamer).enqueue(object: Callback<List<PartidaModel>> {

            override fun onFailure(call: Call<List<PartidaModel>>, t: Throwable) {

                Toast.makeText(this@HomeActivity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<List<PartidaModel>>, response: Response<List<PartidaModel>>) {

                if(response.code() == 200) {
                    response?.body()?.let {
                        criarCardsAlternativo(response.body()
                        , idGamer)

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
        val telaConfig = Intent(this@HomeActivity, ConfigurationActivity::class.java)
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


    fun todasEquipes(componente:View){
        var idGamer = intent.extras?.getString("currentUser")
        var usuario = intent.extras?.getString("usuario")
        val telaAllTeams = Intent(this@HomeActivity, AllTeamsActivity::class.java)
        telaAllTeams.putExtra("currentUser", idGamer)
        telaAllTeams.putExtra("usuario", usuario)
        startActivity(telaAllTeams)
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

    fun reload(componente: View){
        id?.let { partidas(it) }
    }

    @SuppressLint("ResourceType")
    override fun forwardClick(holder: View) {
        holder.setOnClickListener{
            Toast.makeText(this@HomeActivity, "Teste", Toast.LENGTH_SHORT).show()
        }
    }
}
