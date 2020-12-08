package com.example.projecthunter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.drawToBitmap
import com.example.projecthunter.models.GamerInfoJogoModel
import com.example.projecthunter.models.JogoModel
import com.example.projecthunter.models.PosicaoModel
import com.example.projecthunter.models.UserModel
import com.example.projecthunter.utils.ApiConnectionUtils
import kotlinx.android.synthetic.main.activity_configuration.*
import kotlinx.android.synthetic.main.activity_configuration.et_confSenha
import kotlinx.android.synthetic.main.activity_configuration.et_cpf
import kotlinx.android.synthetic.main.activity_configuration.et_email
import kotlinx.android.synthetic.main.activity_configuration.et_nome
import kotlinx.android.synthetic.main.activity_configuration.et_senha
import kotlinx.android.synthetic.main.activity_configuration.et_telefone
import kotlinx.android.synthetic.main.activity_configuration.et_usuario
import kotlinx.android.synthetic.main.activity_configuration.sp_jogo
import kotlinx.android.synthetic.main.activity_configuration.sp_posicao
import kotlinx.android.synthetic.main.activity_new_match.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_image_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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
            usuario = username
        }

        val spinner: Spinner = sp_jogo
        ArrayAdapter.createFromResource(
            this@ConfigurationActivity,
            R.array.games_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val spinner2: Spinner = sp_posicao
        ArrayAdapter.createFromResource(
            this@ConfigurationActivity,
            R.array.positions_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
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
                        idGamer = this?.idGamer?.let { Integer(it) }

                }
            }
        })
    }

    fun atualizar(componente: View) {

        if (et_usuario.text.toString().equals("") || et_email.text.toString()
                .equals("") || et_cpf.text.toString().equals("") || et_telefone.text.toString()
                .equals("") || et_senha.text.toString().equals("") || et_confSenha.text.toString()
                .equals("") || sp_jogo.selectedItemPosition ==0 || sp_posicao.selectedItemPosition ==0
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
                loadingView.show()
                if (selectedImage != null) {
                    uploadImageToImgur(selectedImage, nome, usuario, email, cpf, telefone, senha)
                }else{
                    doUpdate(nome, usuario, email, cpf, telefone, senha)
                }
            }
        }
    }

    fun doUpdate(nome:String, usuario:String, email:String, cpf:String, telefone:String, senha:String) {
        var linkImagem : String? = ""
        if(imgurUrl != null) {
            linkImagem = imgurUrl
        }
        val userModel = UserModel(idGamer?.toInt(), nome, cpf, email, senha, telefone, linkImagem, usuario, email)
        ApiConnectionUtils().configService().atualizar(idGamer, userModel).enqueue( object : Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ConfigurationActivity, "Erro ao efetuar a atualização $t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@ConfigurationActivity, "Usuário Atualizado", Toast.LENGTH_SHORT).show()
                if(response.code() == 200) {
                    loadingView.dismiss()
                    val gamerInfo = GamerInfoJogoModel(null, null,
                        JogoModel(sp_jogo.selectedItemPosition, null, null, null),
                        PosicaoModel(sp_posicao.selectedItemPosition, null)
                    )
                    doUpdateGame(email, gamerInfo)
                    val telaHome = Intent(this@ConfigurationActivity, HomeActivity::class.java)
                    Toast.makeText(this@ConfigurationActivity, "Usuário Atualizado", Toast.LENGTH_SHORT).show()
                    telaHome.putExtra("currentUser", idGamer.toString())
                    startActivity(telaHome)
                }else{
                    Toast.makeText(this@ConfigurationActivity, "Erro ao efetuar a atualização!", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }

    private fun doUpdateGame(email:String,gamerInfo:GamerInfoJogoModel){
        ApiConnectionUtils().cadastroService().postGameUser(email, gamerInfo).enqueue(object:
            Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ConfigurationActivity, "Erro ao efetuar a atualização", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if(response.code() == 201) {
                    loadingView.dismiss()
                    Toast.makeText(this@ConfigurationActivity, "Usuário Atualizado com sucesso!", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this@ConfigurationActivity, "Erro na atualizzção, tente novamente!", Toast.LENGTH_SHORT).show()
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

    private val CLIENT_ID = "63fff1e83ba6f56"
    private var imgurUrl: String = ""


    fun uploadImageToImgur(image: Bitmap, nome:String, usuario:String, email:String, cpf:String, telefone:String, senha:String){

        getBase64Image(image, complete = { base64Image ->
            GlobalScope.launch(Dispatchers.Default) {
                val url = URL("https://api.imgur.com/3/image")

                val boundary = "Boundary-${System.currentTimeMillis()}"

                val httpsURLConnection =
                    withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
                httpsURLConnection.setRequestProperty("Authorization", "Client-ID $CLIENT_ID")
                httpsURLConnection.setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=$boundary"
                )

                httpsURLConnection.requestMethod = "POST"
                httpsURLConnection.doInput = true
                httpsURLConnection.doOutput = true

                var body = ""
                body += "--$boundary\r\n"
                body += "Content-Disposition:form-data; name=\"image\""
                body += "\r\n\r\n$base64Image\r\n"
                body += "--$boundary--\r\n"


                val outputStreamWriter = OutputStreamWriter(httpsURLConnection.outputStream)
                withContext(Dispatchers.IO) {
                    outputStreamWriter.write(body)
                    outputStreamWriter.flush()
                }
                val response = httpsURLConnection.inputStream.bufferedReader()
                    .use { it.readText() }  // defaults to UTF-8
                val jsonObject = JSONTokener(response).nextValue() as JSONObject
                val data = jsonObject.getJSONObject("data")

                Log.d("TAG", "Link is : ${data.getString("link")}")
                imgurUrl = data.getString("link")
                doUpdate(nome,usuario,email,cpf,telefone,senha)

            }
        })

    }




    private fun getBase64Image(image: Bitmap, complete: (String) -> Unit) {
        GlobalScope.launch {
            val outputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val b = outputStream.toByteArray()
            complete(Base64.encodeToString(b, Base64.DEFAULT))
        }
    }

    fun perfil(componente: View){
        var idGamer = intent.extras?.getString("currentUser")
        var usuario = intent.extras?.getString("usuario")
        val telaPerfil = Intent(this@ConfigurationActivity, PerfilActivity::class.java)
        telaPerfil.putExtra("currentUser", idGamer)
        telaPerfil.putExtra("usuario", usuario)
        startActivity(telaPerfil)
    }

    fun logoff(componente: View){


        ApiConnectionUtils().logoffService().logoff().enqueue(object: Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(this@ConfigurationActivity, "Erro ao executar comando", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if(response.code() == 200) {
                    var preferencias = getPreferences(Context.MODE_PRIVATE)
                    preferencias.edit().remove("usuario").commit()
                    preferencias.edit().remove("login").commit()
                    preferencias.edit().remove("senha").commit()
                    preferencias.edit().remove("currentUser").commit()

                    val telaLogin = Intent(this@ConfigurationActivity, MainActivity::class.java)
                    startActivity(telaLogin)
                }else{
                    Toast.makeText(this@ConfigurationActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                }

            }
        })
    }


}