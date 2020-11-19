package com.example.projecthunter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_new_match.*

class NewMatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_match)

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
            R.array.games_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner2.adapter = adapter
        }

    }
}