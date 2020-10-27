package com.example.projecthunter.utils

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projecthunter.R
import com.example.projecthunter.models.PartidaModel


class NewMatchAdapter(): RecyclerView.Adapter<NewMatchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.organizacao.setTypeface(null, Typeface.BOLD)
        }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var organizacao: TextView = itemView.findViewById(R.id.firstName)
        var imagem: ImageButton = itemView.findViewById(R.id.image)
    }



}