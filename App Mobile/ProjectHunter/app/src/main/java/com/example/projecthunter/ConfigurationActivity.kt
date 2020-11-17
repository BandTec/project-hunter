package com.example.projecthunter

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.drawToBitmap
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import com.example.projecthunter.utils.ImageUploader
import kotlinx.android.synthetic.main.activity_configuration.*
import kotlinx.android.synthetic.main.fragment_image_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfigurationActivity : AppCompatActivity() {


    private lateinit var selectedImage: Bitmap

    private lateinit var loadingView: AlertDialog
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

        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(R.layout.loading_dialog)
        loadingView = builder.create()

        ib_imagem_usuario.setOnClickListener{
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        inicioComDados()

    }

    fun inicioComDados(){



        ApiConnectionUtils().configService().getUserData(usuario).enqueue(object: Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(this@ConfigurationActivity, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {

                    with (response.body()){

                        et_nome.setText(this?.nome)
                        et_senha.setText(this?.senha)
                        et_confSenha.setText(this?.senha)
                        et_telefone.setText(this?.telefone)
                        et_email.setText(this?.email)
                        et_cpf.setText(this?.cpf)
                        et_usuario.setText(this?.usuario)

                }
            }
        })
    }

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

        loadingView.show()
        val envioImagem: ImageUploader? = null
        var linkImagem : String? = ""
        linkImagem = envioImagem?.uploadImageToImgur(selectedImage)

        val userModel = UserModel(idGamer, nome, cpf, email, senha, telefone,linkImagem, usuario, email)
        ApiConnectionUtils().configService().atualizar(idGamer, userModel).enqueue( object : Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ConfigurationActivity, "Erro ao efetuar a atualização $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@ConfigurationActivity, "Usuário Atualizado", Toast.LENGTH_SHORT).show()
                if(response.code() == 200) {
                    loadingView.dismiss()
                    val telaHome = Intent(this@ConfigurationActivity, HomeActivity::class.java)
                    Toast.makeText(this@ConfigurationActivity, "Usuário Atualizado", Toast.LENGTH_SHORT).show()
                    telaHome.putExtra("currentUser", idGamer)
                    startActivity(telaHome)
                }else{
                    Toast.makeText(this@ConfigurationActivity, "Erro ao efetuar a atualização!", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }


    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
//        intent.putExtra("crop", "true")
//        intent.putExtra("scale", true)
//        intent.putExtra("aspectX", 16)
//        intent.putExtra("aspectY", 9)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            ib_imagem_usuario.setImageURI(data?.data)
            selectedImage = ib_imagem_usuario.drawToBitmap()
        }
    }



}