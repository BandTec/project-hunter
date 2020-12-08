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

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.projecthunter.NewMatchActivity
import com.example.projecthunter.R
import com.example.projecthunter.models.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_perfil.*
import kotlinx.android.synthetic.main.fragment_image_profile.*
import java.util.*


class ProfileAdapter(val posts: MutableList<List<GamerInfoJogoModel>>, val posts2: MutableList<List<GamerInfoEquipeModel>>, val posts3: MutableList<List<PartidaModel>>, val id:Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    internal val VIEW_TYPE_ONE = 1
    internal val VIEW_TYPE_TWO = 2
    internal val VIEW_TYPE_THREE = 3

    var listaFinal = listOf(0, 1, 2)


    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        //val view2 : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)



        return if (viewType == VIEW_TYPE_ONE) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.games_post, parent, false))
        }else if (viewType == VIEW_TYPE_TWO) {
            ViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.equipes_post, parent, false))
        }else{
            ViewHolder3(LayoutInflater.from(parent.context).inflate(R.layout.hist_vitorias_post, parent, false))
        }

    }


    interface ClickEventHandler {
        fun forwardClick(holder: View)
    }

    override fun getItemCount() = listaFinal.size


    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(position == 0) {
            posts.forEach {
                holder as ViewHolder

                holder.rv.layoutManager = LinearLayoutManager(null, OrientationHelper.VERTICAL, false)
                holder.rv.adapter = GameByGameAdapter(it as MutableList<GamerInfoJogoModel>)
            }
        }
       else if(position == 1) {
            posts2.forEach {
                holder as ViewHolder2

                holder.rv.layoutManager = LinearLayoutManager(null, OrientationHelper.VERTICAL, false)
                holder.rv.adapter = TeamByTeamAdapter(it as MutableList<GamerInfoEquipeModel>, id, false)
            }
        }else{
            posts3.forEach {
                holder as ViewHolder3

                holder.rv.layoutManager = LinearLayoutManager(null, OrientationHelper.VERTICAL, false)
                holder.rv.adapter = HistByHistAdapter(it as MutableList<PartidaModel>)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position < 1) {
            return VIEW_TYPE_ONE
        } else if(position < 2){
            return VIEW_TYPE_TWO
        }else{
            return VIEW_TYPE_THREE
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val rv: RecyclerView = itemView.findViewById(R.id.rv_jogos)

    }

    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView){
        val rv: RecyclerView = itemView.findViewById(R.id.rv_equipes)
    }

    class ViewHolder3(itemView: View) : RecyclerView.ViewHolder(itemView){
        val rv: RecyclerView = itemView.findViewById(R.id.rv_historicos)

    }


}