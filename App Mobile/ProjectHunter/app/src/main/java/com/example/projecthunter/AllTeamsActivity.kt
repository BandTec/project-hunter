package com.example.projecthunter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.example.projecthunter.models.EquipeGamerModel
import com.example.projecthunter.models.GamerInfoEquipeModel
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import com.example.projecthunter.utils.TeamByTeamAdapter
import com.example.projecthunter.utils.TeamSearchAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_all_teams.*
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.fragment_image_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllTeamsActivity : AppCompatActivity() {
    var idGamer:String? = null
    var usuario :String? = null
    var email:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_teams)
        idGamer = intent.extras?.getString("currentUser")
        usuario = intent.extras?.getString("usuario")
        usuario?.let { inicioComEmail(it) }
    }

    fun inicioComEmail(usuario:String){
        ApiConnectionUtils().configService().getUserData(usuario).enqueue(object:
            Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(this@AllTeamsActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {

                with (response.body()){
                    this?.email?.let { getTeams(it) }
                }

            }
        })
    }

    fun getTeams(email: String){
        ApiConnectionUtils().profileService().findUserTeams(email).enqueue(object:
            Callback<List<GamerInfoEquipeModel>> {
            override fun onFailure(call: Call<List<GamerInfoEquipeModel>>, t: Throwable) {
                Toast.makeText(this@AllTeamsActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<GamerInfoEquipeModel>>, response: Response<List<GamerInfoEquipeModel>>) {

                if(response.code() == 200) {
                    response?.body()?.let {
                        idGamer?.toInt()?.let { it1 ->
                            criarCards(response.body() as MutableList<GamerInfoEquipeModel>,
                                it1
                            )
                        }
                    }
                }else if (response.code() == 204) {

                }else{
                    Toast.makeText(this@AllTeamsActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun criarCards(team:MutableList<GamerInfoEquipeModel>, id:Int){
        try{
            //rv_equipes_participantes.layoutManager = LinearLayoutManager(this@AllTeamsActivity, OrientationHelper.VERTICAL, false)
            rv_equipes_participantes.layoutManager = GridLayoutManager(this@AllTeamsActivity, 2, OrientationHelper.VERTICAL, false)

            rv_equipes_participantes.adapter = TeamByTeamAdapter(team, id, true)
        }catch (e: java.lang.Exception){
            Toast.makeText(this@AllTeamsActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}