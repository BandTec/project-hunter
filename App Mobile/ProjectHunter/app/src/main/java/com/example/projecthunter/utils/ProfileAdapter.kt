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


class ProfileAdapter(val posts: MutableList<List<GamerInfoJogoModel>>, val posts2: MutableList<List<GamerInfoEquipeModel>>, val posts3: MutableList<List<PartidaModel>>, val taxa1:Double, val taxa2:Double): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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

//        if(position == 0) {
//            if (posts != null) {
//                holder as ViewHolder
//                holder.jogoUm.text = posts[position].idJogo.nomeJogo
//                holder.jogoUm.setTypeface(null, Typeface.BOLD)
//                if (posts[position].idJogo.fotoJogo != null) {
//                    Picasso.get().load(posts[position].idJogo.fotoJogo).into(holder.jogoUmImg)
//                }
//            }
//        }else if (position == 1) {
//            if (posts != null) {
//                holder as ViewHolder
//                holder.jogoDois.text = posts[position].idJogo.nomeJogo
//                holder.jogoDois.setTypeface(null, Typeface.BOLD)
//                if (posts[position].idJogo.fotoJogo != null) {
//                    Picasso.get().load(posts[position].idJogo.fotoJogo).into(holder.jogoDoisImg)
//                }
//            }
//        }else if (position == 1) {
//            if (posts2 != null) {
//                holder as ViewHolder2
//                holder.equipeUm.text = posts2[position].idEquipe.nomeEquipe
//                holder.equipeUm.setTypeface(null, Typeface.BOLD)
//                if (posts2[position].idEquipe.fotoEquipe != null) {
//                    Picasso.get().load(posts2[position].idEquipe.fotoEquipe).into(holder.equipeUmImg)
//                }
//            }
//        }else if (position == 3) {
//            if (posts2 != null) {
//                holder as ViewHolder2
//                holder.equipeDois.text = posts2[position].idEquipe.nomeEquipe
//                holder.equipeDois.setTypeface(null, Typeface.BOLD)
//                if (posts2[position].idEquipe.fotoEquipe != null) {
//                    Picasso.get().load(posts2[position].idEquipe.fotoEquipe).into(holder.equipeDoisImg)
//                }
//            }
//        }else if (position == 4) {
//            if (posts3 != null) {
//                holder as ViewHolder3
//                holder.taxaJogoUm.text = taxa1.toString()+ "%"
//                holder.taxaJogoUm.setTypeface(null, Typeface.BOLD)
//                if (posts3[position].idJogo.fotoJogo != null) {
//                    Picasso.get().load(posts3[position].idJogo.fotoJogo).into(holder.taxaUmImg)
//                }
//            }
//        }else {
//            if (posts3 != null) {
//                holder as ViewHolder3
//                holder.taxaJogoDois.text = taxa2.toString()+ "%"
//                holder.taxaJogoDois.setTypeface(null, Typeface.BOLD)
//                if (posts3[position].idJogo.fotoJogo != null) {
//                    Picasso.get().load(posts3[position].idJogo.fotoJogo).into(holder.taxaDoisImg)
//                }
//            }
//       }
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
        val equipeUm: TextView = itemView.findViewById(R.id.tv_equipe_um)
        val equipeDois: TextView = itemView.findViewById(R.id.tv_equipe_dois)
        val equipeUmImg : ImageView = itemView.findViewById(R.id.iv_equipe_um)
        val equipeDoisImg : ImageView = itemView.findViewById(R.id.iv_equipe_dois)
    }

    class ViewHolder3(itemView: View) : RecyclerView.ViewHolder(itemView){
        var taxaJogoUm: TextView = itemView.findViewById(R.id.tv_taxa_um)
        var taxaJogoDois: TextView = itemView.findViewById(R.id.tv_taxa_dois)
        val taxaUmImg : ImageView = itemView.findViewById(R.id.iv_jogo_taxa_um)
        val taxaDoisImg : ImageView = itemView.findViewById(R.id.iv_jogo_taxa_dois)

    }


}