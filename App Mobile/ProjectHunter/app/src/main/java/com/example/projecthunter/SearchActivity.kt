package com.example.projecthunter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.projecthunter.models.EquipeGamerModel
import com.example.projecthunter.models.GamerInfoEquipeModel
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_busca.*
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.fragment_image_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    var idGamer:String? = null
    var usuario :String? = null
    var email:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca)
        idGamer = intent.extras?.getString("currentUser")
        usuario = intent.extras?.getString("usuario")

    }

    fun find(componente:View){
        if (et_busca.text != null || et_busca.text.equals("")){
            getTeams(et_busca.text.toString())
        }else{
            Toast.makeText(this, "Preencha o campo de busca", Toast.LENGTH_SHORT).show()
        }
    }

    fun getTeams(pesquisa: String){
        ApiConnectionUtils().teamService().getTeamsBySearchName(pesquisa).enqueue(object:
            Callback<List<EquipeGamerModel>> {
            override fun onFailure(call: Call<List<EquipeGamerModel>>, t: Throwable) {
                Toast.makeText(this@SearchActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<EquipeGamerModel>>, response: Response<List<EquipeGamerModel>>) {
                Toast.makeText(this@SearchActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                if(response.code() == 200) {
                    response?.body()?.let {
                        idGamer?.toInt()?.let { it1 ->
                            criarCards(response.body() as MutableList<EquipeGamerModel>,
                                it1
                            )
                        }
                    }
                }else if (response.code() == 204) {
                    Toast.makeText(this@SearchActivity, "Nenhuma Equipe Encontrada", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@SearchActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
        })

        ApiConnectionUtils().teamService().getTeamsBySearchGame(pesquisa).enqueue(object:
            Callback<List<EquipeGamerModel>> {
            override fun onFailure(call: Call<List<EquipeGamerModel>>, t: Throwable) {
                Toast.makeText(this@SearchActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<EquipeGamerModel>>, response: Response<List<EquipeGamerModel>>) {
                Toast.makeText(this@SearchActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                if(response.code() == 200) {
                    response?.body()?.let {
                        idGamer?.toInt()?.let { it1 ->
                            criarCards(response.body() as MutableList<EquipeGamerModel>,
                                it1
                            )
                        }
                    }
                }else if (response.code() == 204) {
                    Toast.makeText(this@SearchActivity, "Nenhuma Equipe Encontrada", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@SearchActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun criarCards(team:MutableList<EquipeGamerModel>, id:Int){
        try{
            rv_busca.layoutManager = LinearLayoutManager(this@SearchActivity, OrientationHelper.VERTICAL, false)
            rv_busca.adapter = TeamSearchAdapter(team, id)
        }catch (e: java.lang.Exception){
            Toast.makeText(this@SearchActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}