package com.example.projecthunter.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projecthunter.EquipeActivity
import com.example.projecthunter.HomeActivity
import com.example.projecthunter.NewMatchActivity
import com.example.projecthunter.R
import com.example.projecthunter.models.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_equipe.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamSearchAdapter(var posts: MutableList<EquipeGamerModel>, var id:Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        //val view2 : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)



        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.team_search_post, parent, false))



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

        ApiConnectionUtils().teamService().getTeamQtd(posts[position].idEquipe.nomeEquipe).enqueue(object:
        Callback<Int>{
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(context, "Erro ao receber a quantidade de usuários $t", Toast.LENGTH_LONG).show()
            }


            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.code() == 200) {
                    holder.tamanhoEquipe.text = response.body().toString() +"/50"
                }else{
                    Toast.makeText(context, "Erro ao receber a quantidade de usuários", Toast.LENGTH_LONG).show()
                }
            }
        })

        ApiConnectionUtils().teamService().isUserOnTeam(posts[position].idEquipe.idEquipe,id).enqueue(object:
            Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
            }


            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 200){
                    holder.btJuntarSe.background = ContextCompat.getDrawable(context, R.drawable.botao_desabilitado)
                    holder.btJuntarSe.isEnabled = false
                    holder.btJuntarSe.visibility = View.VISIBLE

                }else{
                    holder.btJuntarSe.setOnClickListener{
                        val solicitacao = EquipeGamerModel(EquipeModel(posts[position].idEquipe.idEquipe,
                            posts[position].idEquipe.nomeEquipe, posts[position].idEquipe.fotoEquipe),
                            NovoMembroGamer(id, null, null),
                            StatusModel(3, null), false)

                        ApiConnectionUtils().teamService().enterTeam(solicitacao).enqueue(object:
                            Callback<Void> {
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(context, "Erro ao receber os dados $t", Toast.LENGTH_SHORT).show()
                            }

                            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                                if (response.code() == 201){
                                    Toast.makeText(context, "Pedido enviado ao capitão da equipe", Toast.LENGTH_LONG).show()
                                    holder.btJuntarSe.background = ContextCompat.getDrawable(context, R.drawable.botao_desabilitado)
                                    holder.btJuntarSe.isEnabled = false
                                    holder.btJuntarSe.visibility = View.VISIBLE

                                }else{
                                    Toast.makeText(context, "Erro ao enviar solicitação", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    }
                }
            }
        })

        holder.fotoEquipe.setOnClickListener { v ->

            val intent = Intent(this.context, EquipeActivity::class.java)
            intent.putExtra("equipe", posts[position].idEquipe.nomeEquipe)
            intent.putExtra("idGamer", id)
            ContextCompat.startActivity(this.context, intent, null)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeEquipe: TextView = itemView.findViewById(R.id.tv_equipe_busca)
        val tamanhoEquipe: TextView = itemView.findViewById(R.id.tv_tamanho_equipe_busca)
        val fotoEquipe: ImageView = itemView.findViewById(R.id.iv_equipe_busca)
        val btJuntarSe: Button = itemView.findViewById(R.id.bt_juntarse_busca)

    }



}