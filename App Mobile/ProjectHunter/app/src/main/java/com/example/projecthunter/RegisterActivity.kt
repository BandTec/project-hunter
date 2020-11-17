package com.example.projecthunter

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.drawToBitmap
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import com.example.projecthunter.utils.ImageUploader
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_image_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var loadingView: AlertDialog
    private lateinit var selectedImage: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        ib_imagem_usuario.setOnClickListener{
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, RegisterActivity.PERMISSION_CODE);
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


    }

    fun cadastrar(componente: View) {
        if (et_usuario.text.toString().equals("") && et_email.text.toString()
                .equals("") && et_cpf.text.toString().equals("") && et_telefone.text.toString()
                .equals("") && et_senha.text.toString().equals("") && et_confSenha.text.toString()
                .equals("")
        ) {

            Toast.makeText(this, "Por favor preencha seus dados corretamente!", Toast.LENGTH_SHORT)
                .show()

        } else {
            val nome = et_nome.text.toString()
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
                val builder = AlertDialog.Builder(this)
                builder.setCancelable(false)
                builder.setView(R.layout.loading_dialog)
                loadingView = builder.create()


                doRegister(nome,usuario,email,cpf,telefone,senha)

            }
        }
    }

    private fun doRegister(nome:String, usuario:String, email:String, cpf:String, telefone:String, senha:String) {
        loadingView.show()
        val envioImagem: ImageUploader? = null
        var linkImagem : String? = ""
        linkImagem = envioImagem?.uploadImageToImgur(selectedImage)
        val userModel = UserModel(null, nome, cpf, email, senha, telefone,linkImagem, usuario, email)
        ApiConnectionUtils().cadastroService().cadastro(userModel).enqueue(object:
            Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Erro ao efetuar o cadastro", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if(response.code() == 201) {
                    loadingView.dismiss()
                    Toast.makeText(this@RegisterActivity, "Usuário Criado com sucesso!", Toast.LENGTH_SHORT).show()
                    telaHome(usuario)
                }else{
                    Toast.makeText(this@RegisterActivity, "Erro no cadastro, tente novamente!", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    fun telaHome(usuario: String){
        ApiConnectionUtils().cadastroService().getNovoUsuario(usuario).enqueue(object:
            Callback<List<UserModel>> {

            override fun onFailure(call: Call<List<UserModel>>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Cadastro efetuado, mas com erro ao enviar a tela Home $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<UserModel>>, response: Response<List<UserModel>>) {

                if(response.code() == 200) {

                    var currentUser:Integer = Integer(0)
                    response.body()?.forEach{
                        if (it != null) {
                            currentUser = it.idGamer!!
                        }
                    }
                    val telaHome = Intent(this@RegisterActivity, HomeActivity::class.java)
                    telaHome.putExtra("currentUser", currentUser)
                    startActivity(telaHome)
                }else{
                    Toast.makeText(this@RegisterActivity, "Erro no cadastro, tente novamente!", Toast.LENGTH_SHORT).show()
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