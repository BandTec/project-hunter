package com.example.projecthunter

import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_configuration.*
import kotlinx.android.synthetic.main.activity_configuration.bt_perfil
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.fragment_image_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        bt_perfil.setColorFilter(getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN)
        var usuario:String? = ""
        usuario = intent.extras?.getString("usuario")
        tv_id_nome.text = usuario
        if (usuario != null) {
            inicioComImagem(usuario)
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

                }
            }
        })
    }
}