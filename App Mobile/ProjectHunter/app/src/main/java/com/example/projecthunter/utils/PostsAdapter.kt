package com.example.projecthunter.utils

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.projecthunter.R
import com.example.projecthunter.models.PartidaModel

class PostsAdapter(val posts: List<PartidaModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val VIEW_TYPE_ONE = 1
    internal val VIEW_TYPE_TWO = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        //val view2 : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)

        return if (viewType == VIEW_TYPE_ONE) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false))
        }else {
            ViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false))
        }

    }

    override fun getItemCount() = posts.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(position < posts.size-1) {
            if (posts != null) {
                holder as ViewHolder
                holder.firstName.text = posts[position].idJogo.nomeJogo
                holder.firstName.setTypeface(null, Typeface.BOLD)
                if (posts[position].idPosicao.posicao != null) {
                    holder.funcao.text = "Papel: " + posts[position].idPosicao.posicao
                } else {
                    holder.funcao.text = "Papel: --"
                }
                holder.hour.text = "Horário: " + posts[position].hora
                holder.hour.setTypeface(null, Typeface.BOLD)
                holder.date.text = "Data: " + posts[position].data

            }
        }

        else{
            (holder as ViewHolder2).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < posts.size-1) {
            VIEW_TYPE_ONE
        } else VIEW_TYPE_TWO

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val firstName: TextView = itemView.findViewById(R.id.firstName)
        val date: TextView = itemView.findViewById(R.id.date)
        val funcao: TextView = itemView.findViewById(R.id.function)
        val hour: TextView = itemView.findViewById(R.id.hour)
    }

    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView){
        var organizacao: TextView = itemView.findViewById(R.id.firstName)
        var imagem: ImageButton = itemView.findViewById(R.id.image)

        internal fun bind(position: Int) {
            // This method will be called anytime a list item is created or update its data
            //Do your stuff here
            organizacao.setTypeface(null, Typeface.BOLD)
        }
    }


}