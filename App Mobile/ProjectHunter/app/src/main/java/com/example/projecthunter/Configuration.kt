package com.example.projecthunter

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_configuration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Configuration : AppCompatActivity() {


    var idGamer: Integer? = null
    var usuario:String = ""


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)
        var id = intent.extras?.getString("currentUser")
        var username = intent.extras?.getString("usuario")

        if(id != null){
            idGamer = Integer(id)
        }
        if(username != null){
            usuario = username as String
        }

        bt_config.setColorFilter(getColor(android.R.color.holo_green_dark), PorterDuff.Mode.SRC_IN)

        //inicioComDados(nome)

    }

//    fun inicioComDados(nome:EditText){
//        ApiConnectionUtils().configService().getUserData(usuario).enqueue(object: Callback<List<UserModel>> {
//            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
//                Toast.makeText(this@Configuration, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {
//                response.body()?.forEach{
//                    if (it != null) {
//
////
////                        et_nome.setText(it.nome)
////                        et_senha.setText(it.senha)
////                        et_confSenha.setText(it.senha)
////                        et_telefone.setText(it.telefone)
////                        et_email.setText(it.email)
////                        et_cpf.setText(it.cpf)
////                        et_usuario.setText(it.usuario)
//                    }
//
//                }
//            }
//        })
//    }

    fun atualizar(componente: View) {
        if (et_usuario.text.toString().equals("") && et_email.text.toString()
                .equals("") && et_cpf.text.toString().equals("") && et_telefone.text.toString()
                .equals("") && et_senha.text.toString().equals("") && et_confSenha.text.toString()
                .equals("")
        ) {

            Toast.makeText(this, "Por favor preencha seus dados corretamente!", Toast.LENGTH_SHORT)
                .show()

        } else {
            val nome = et_usuario.text.toString()
            val usuario = et_usuario.text.toString()
            val email = et_email.text.toString()
            val cpf = et_cpf.text.toString()
            val telefone = et_telefone.text.toString()
            val senha = et_senha.text.toString()
            val confSenha = et_confSenha.text.toString()

            if (senha != confSenha)
            {
                Toast.makeText(this, "Por favor, digite sua senha corretamente!", Toast.LENGTH_SHORT).show()
            }
            else{
                doUpdate(nome,usuario,email,cpf,telefone,senha)
            }
        }
    }

    fun doUpdate(nome:String, usuario:String, email:String, cpf:String, telefone:String, senha:String) {

        val userModel = UserModel(idGamer, nome, cpf, email, senha, telefone,null, usuario, email)
        ApiConnectionUtils().configService().atualizar(idGamer, userModel).enqueue( object : Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@Configuration, "Erro ao efetuar a atualização $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@Configuration, "Usuário Atualizado", Toast.LENGTH_SHORT).show()
                if(response.code() == 200) {
                    val telaHome = Intent(this@Configuration, HomeActivity::class.java)
                    Toast.makeText(this@Configuration, "Usuário Atualizado", Toast.LENGTH_SHORT).show()
                    telaHome.putExtra("currentUser", idGamer)
                    startActivity(telaHome)
                }else{
                    Toast.makeText(this@Configuration, "Erro ao efetuar a atualização!", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }

}