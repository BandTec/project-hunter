package com.example.projecthunter.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projecthunter.EquipeActivity
import com.example.projecthunter.R
import com.example.projecthunter.models.EquipeGamerModel
import com.example.projecthunter.models.GamerInfoEquipeModel
import com.squareup.picasso.Picasso

class GamerByGamerAdapter (var posts: MutableList<EquipeGamerModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        //val view2 : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)



        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.player_by_player, parent, false))



    }


    interface ClickEventHandler {
        fun forwardClick(holder: View)
    }

    override fun getItemCount() = posts.size


    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.nomeGamer.text = posts[position].idGamer.nome
        holder.nomeGamer.setTypeface(null, Typeface.BOLD)
        if (posts[position].idGamer.fotoGamer != null) {
            Picasso.get().load(posts[position].idGamer.fotoGamer ).into(holder.fotoGamer)
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeGamer: TextView = itemView.findViewById(R.id.tv_player)
        val fotoGamer: ImageView = itemView.findViewById(R.id.iv_player)

    }



}