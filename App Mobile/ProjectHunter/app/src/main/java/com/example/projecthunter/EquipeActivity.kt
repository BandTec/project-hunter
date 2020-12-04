package com.example.projecthunter

import android.graphics.Color
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.projecthunter.models.EquipeModel
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
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
                    delay(2000)
                    withContext(Dispatchers.Main) {
                        idEquipe?.let { idGamer?.let { it1 -> isUserOnTeam(it, it1) } }
                    }
                }.invoke()
            }
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
                    idEquipe = it?.idEquipe
                    // this?.email?.let { getGames(it) }
                    // this?.email?.let { getTeams(it) }
                    // this?.idGamer?.let { getHistory(it.toInt()) }


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

                }else{
                    bt_juntarse.setOnClickListener{

                    }
                }
            }
        })
    }
}