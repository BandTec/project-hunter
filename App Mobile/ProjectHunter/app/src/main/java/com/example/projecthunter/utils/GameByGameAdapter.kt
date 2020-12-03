package com.example.projecthunter.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projecthunter.HomeActivity
import com.example.projecthunter.NewMatchActivity
import com.example.projecthunter.R
import com.example.projecthunter.models.*
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GameByGameAdapter(var posts: MutableList<GamerInfoJogoModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        //val view2 : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)



        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.game_by_game_post, parent, false))



    }


    interface ClickEventHandler {
        fun forwardClick(holder: View)
    }

    override fun getItemCount() = posts.size


    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.nomeJogo.text = posts[position].idJogo.nomeJogo
        holder.nomeJogo.setTypeface(null, Typeface.BOLD)
        if (posts[position].idJogo.fotoJogo != null) {
            Picasso.get().load(posts[position].idJogo.fotoJogo).into(holder.fotoJogo)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeJogo: TextView = itemView.findViewById(R.id.tv_jogo)
        val fotoJogo: ImageView = itemView.findViewById(R.id.iv_jogo)

    }



}