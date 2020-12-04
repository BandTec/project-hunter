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
import com.example.projecthunter.EquipeActivity
import com.example.projecthunter.HomeActivity
import com.example.projecthunter.NewMatchActivity
import com.example.projecthunter.R
import com.example.projecthunter.models.*
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamByTeamAdapter(var posts: MutableList<GamerInfoEquipeModel>, var id:Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        //val view2 : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)



        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.team_by_team_post, parent, false))



    }


    interface ClickEventHandler {
        fun forwardClick(holder: View)
    }

    override fun getItemCount() = posts.size


    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.nomeEquipe.text = posts[position].idEquipe.nomeEquipe
        holder.nomeEquipe.setTypeface(null, Typeface.BOLD)
        if (posts[position].idEquipe.fotoEquipe != null) {
            Picasso.get().load(posts[position].idEquipe.fotoEquipe).into(holder.fotoEquipe)
        }

        context = holder.itemView.context

        holder.fotoEquipe.setOnClickListener { v ->

            val intent = Intent(this.context, EquipeActivity::class.java)
            intent.putExtra("equipe", posts[position].idEquipe.nomeEquipe)
            intent.putExtra("idGamer", id)
            ContextCompat.startActivity(this.context, intent, null)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeEquipe: TextView = itemView.findViewById(R.id.tv_equipe)
        val fotoEquipe: ImageView = itemView.findViewById(R.id.iv_equipe)

    }



}