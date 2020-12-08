package com.example.projecthunter.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.projecthunter.R
import com.example.projecthunter.models.*
import com.squareup.picasso.Picasso



class HistByHistAdapter(var posts: MutableList<PartidaModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        //val view2 : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)




        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hist_by_hist_post, parent, false))


    }


    interface ClickEventHandler {
        fun forwardClick(holder: View)
    }

    override fun getItemCount() = posts.size


    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        holder.taxaJogo.text = posts[position].winner.toString()
        if(posts[position].winner!!){
            holder.taxaJogo.text = "Vitória"
            holder.taxaJogo.setTextColor(Color.parseColor("#00FF00"))
        }else{
            holder.taxaJogo.text = "Derrota"
            holder.taxaJogo.setTextColor(Color.parseColor("#B10000"))
        }
        holder.taxaJogo.setTypeface(null, Typeface.BOLD)
        if (posts[position].idJogo.fotoJogo != null) {
            Picasso.get().load(posts[position].idJogo.fotoJogo).into(holder.fotoJogo)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val taxaJogo: TextView = itemView.findViewById(R.id.tv_hist)
        val fotoJogo: ImageView = itemView.findViewById(R.id.iv_hist)

    }



}