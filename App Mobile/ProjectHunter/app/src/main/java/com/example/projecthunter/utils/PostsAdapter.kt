package com.example.projecthunter.utils

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projecthunter.R
import com.example.projecthunter.models.PartidaModel

class PostsAdapter(val posts: List<PartidaModel>): RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        return ViewHolder(view)
        NewMatchAdapter()
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (posts != null) {
            holder.firstName.text = posts[position].idJogo.nomeJogo
            holder.firstName.setTypeface(null, Typeface.BOLD)
            if(posts[position].idPosicao.posicao != null) {
                holder.funcao.text = "Papel: " + posts[position].idPosicao.posicao
            }else{
                holder.funcao.text = "Papel: --"
            }
            holder.hour.text = "Horário: "+posts[position].hora
            holder.hour.setTypeface(null, Typeface.BOLD)
            holder.date.text = "Data: "+posts[position].data

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val firstName: TextView = itemView.findViewById(R.id.firstName)
        val date: TextView = itemView.findViewById(R.id.date)
        val funcao: TextView = itemView.findViewById(R.id.function)
        val hour: TextView = itemView.findViewById(R.id.hour)

    }



}