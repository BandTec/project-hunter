package com.example.projecthunter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.projecthunter.models.*
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_new_match.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewMatchActivity : AppCompatActivity() {
    var usuario:String = ""
    private lateinit var loadingView: AlertDialog
    var id:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_match)

        var preferencias : SharedPreferences? = null
        preferencias = getPreferences(Context.MODE_PRIVATE)
        var username = preferencias?.getString("usuario", null)
        id = intent.extras?.getInt("idGamer")

        if(username != null){
            usuario = username as String
        }

        val spinner: Spinner = sp_escolha_jogo
        ArrayAdapter.createFromResource(
            this@NewMatchActivity,
            R.array.games_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val spinner2: Spinner = sp_escolha_posicao
        ArrayAdapter.createFromResource(
            this@NewMatchActivity,
            R.array.positions_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }


//        ApiConnectionUtils().configService().getUserData(usuario).enqueue(object:
//            Callback<UserModel> {
//            override fun onFailure(call: Call<UserModel>, t: Throwable) {
//                Toast.makeText(
//                    this@NewMatchActivity,
//                    "Erro ao receber os dados $t",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            override fun onResponse(
//                call: Call<UserModel>,
//                response: Response<UserModel>
//            ) {
//                Toast.makeText(this@NewMatchActivity, "Usuário Encontrado", Toast.LENGTH_SHORT).show()
//                with (response.body()){
//
//                    id = this?.idGamer!!
//                }
//            }
//        })

    }


    fun newMatch(componente:View){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.loading_dialog)
        loadingView = builder.create()

        if(et_ano.text.toString().equals("") || et_dia.text.toString().equals("") ||
            et_mes.text.toString().equals("") || et_hora.text.toString().equals("") ||
            et_minuto.text.toString().equals("") || sp_escolha_jogo.selectedItemPosition == 0 ||
            sp_escolha_posicao.selectedItemPosition == 0
             ){
            
            Toast.makeText(this, "Por favor preencha seus dados corretamente!", Toast.LENGTH_SHORT)
                .show()
        }else{
            loadingView.show()


                    var jogo = NovaPartidaJogoModel(sp_escolha_jogo.selectedItemPosition)
                    var posicao = NovaPartidaPosicaoModel(sp_escolha_posicao.selectedItemPosition)
                    var gamer = NovaPartidaGamerModel(id)

                        var data =  et_ano.text.toString() +"-"+ et_mes.text.toString() +"-"+ et_dia.text.toString()
                    var hora = et_hora.text.toString() +":"+ et_minuto.text.toString() + ":00"
                    var partida = NovaPartidaModel(jogo, gamer, posicao, data, hora, null)
                    ApiConnectionUtils().matchesService().createMatch(partida).enqueue(object:
                        Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(this@NewMatchActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            loadingView.dismiss()
                            if(response.code() == 201) {

                                //val telaHome = Intent(this@NewMatchActivity, HomeActivity::class.java)
                                Toast.makeText(this@NewMatchActivity, "Partida Criada", Toast.LENGTH_SHORT).show()
                                //telaHome.putExtra("currentUser", id)
                                //startActivity(telaHome)
                                onBackPressed()
                            }else{
                                Toast.makeText(this@NewMatchActivity, "Erro ao criar nova partida! ${response.message()} ", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                    loadingView.dismiss()

                }
        }

}


