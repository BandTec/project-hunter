package com.example.projecthunter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import kotlinx.android.synthetic.main.activity_home.*


class MainActivity : AppCompatActivity() {
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val posts: ArrayList<String> = ArrayList()

        for (i in 1..5){
            posts.add("Partida $i : League of Legends")
        }

        rv_partidas.layoutManager = LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false)
        rv_partidas.adapter = PostsAdapter(posts)




    }
}