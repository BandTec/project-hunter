package com.example.projecthunter.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projecthunter.HomeActivity
import com.example.projecthunter.NewMatchActivity
import com.example.projecthunter.R
import com.example.projecthunter.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor


class PostsAdapter(var posts: MutableList<PartidaModel>, val idGamer:Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    internal val VIEW_TYPE_ONE = 1
    internal val VIEW_TYPE_TWO = 2



    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        //val view2 : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)



        return if (viewType == VIEW_TYPE_ONE) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false))
        }else {
            ViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false))

        }

    }


    interface ClickEventHandler {
        fun forwardClick(holder: View)
    }

    override fun getItemCount() = posts.size


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceType")
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
                holder.hour.text = "Horário: " + posts[position].hora.toString()
                holder.hour.setTypeface(null, Typeface.BOLD)
                holder.date.text = "Data: " + posts[position].data.toString()


                holder.delete.setOnClickListener{
                    context = holder.itemView.context
                    ApiConnectionUtils().matchesService().deleteMatch(posts[position].idPartida).enqueue(object:
                        Callback<Void> {
                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "Erro ao apagar partida $t", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if(response.code() == 200) {
                                Toast.makeText(context, "Partida Apagada", Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, HomeActivity::class.java)
                                intent.putExtra("currentUser", idGamer.toString())
                                ContextCompat.startActivity(context, intent, null)

                            }else{
                                Toast.makeText(context, "Erro ao apagar partida ${response.code()} ", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }

                val dataHoje: LocalDate = LocalDate.now()
                val horaHoje: LocalTime = LocalTime.now()

                val dataPt: LocalDate = LocalDate.parse(posts[position].data)
                val horaPt: LocalTime = LocalTime.parse(posts[position].hora)

                val dt:LocalDateTime = LocalDateTime.of(dataPt, horaPt)
                val dtNow:LocalDateTime = LocalDateTime.now()
                context = holder.itemView.context

                if (dtNow.isAfter(dt)){
                    holder.hour.text = "Dia passou"
                    //if(horaHoje.isAfter(horaPt)){
                        if(posts[position].winner!!){

                            holder.cardView.setCardBackgroundColor(Color.parseColor("#C4C4C4"))
                            holder.hour.text = "Venceu"
                            holder.hour.setTypeface(null, Typeface.BOLD)
                            holder.hour.setTextColor(Color.parseColor("#005000"))
                            holder.btPerdeu.visibility = View.INVISIBLE
                            holder.btVenceu.visibility = View.INVISIBLE
                            holder.delete.visibility = View.INVISIBLE

                        }else{
                            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                            holder.date.visibility = View.INVISIBLE
                            holder.hour.text = "Partida Vencida?"
                            holder.hour.setTypeface(null, Typeface.BOLD)
                            holder.hour.setTextColor(Color.parseColor("#000000"))
                            holder.btVenceu.visibility = View.VISIBLE
                            holder.btPerdeu.visibility = View.VISIBLE
                            holder.delete.visibility = View.INVISIBLE

                        }

                        holder.btVenceu.setOnClickListener {
                            holder.date.visibility = View.VISIBLE
                            holder.delete.visibility = View.INVISIBLE
                            holder.cardView.setCardBackgroundColor(Color.parseColor("#C4C4C4"))
                            holder.btPerdeu.visibility = View.INVISIBLE
                            holder.btVenceu.visibility = View.INVISIBLE
                            holder.hour.text = "Venceu"
                            holder.hour.setTypeface(null, Typeface.BOLD)
                            holder.hour.setTextColor(Color.parseColor("#005000"))
                            val atualizacaoPt = NovaPartidaModel(
                                NovaPartidaJogoModel(posts[position].idJogo.idJogo),
                                NovaPartidaGamerModel(posts[position].idGamer.idGamer),
                                NovaPartidaPosicaoModel(posts[position].idPosicao.idPosicao),
                                posts[position].data,
                                posts[position].hora,
                                true
                            )
                            if (posts[position].idPk == 0) {
                                Toast.makeText(context, "idPk = 0 ???", Toast.LENGTH_SHORT).show()
                            } else {
                                holder.updateMatch(posts[position].idPk, atualizacaoPt, context)
                            }
                        }
                            holder.btPerdeu.setOnClickListener {
                            holder.date.visibility = View.VISIBLE
                            holder.delete.visibility = View.INVISIBLE
                            holder.cardView.setCardBackgroundColor(Color.parseColor("#C4C4C4"))
                            holder.btPerdeu.visibility = View.INVISIBLE
                            holder.btVenceu.visibility = View.INVISIBLE
                            holder.hour.text = "Perdeu"
                            holder.hour.setTypeface(null, Typeface.BOLD)
                            holder.hour.setTextColor(Color.parseColor("#B10000"))
                            val atualizacaoPt = NovaPartidaModel(
                                NovaPartidaJogoModel(posts[position].idJogo.idJogo),
                                NovaPartidaGamerModel(posts[position].idGamer.idGamer),
                                NovaPartidaPosicaoModel(posts[position].idPosicao.idPosicao),
                                posts[position].data,
                                posts[position].hora,
                                false
                            )

                            holder.updateMatch(posts[position].idPk, atualizacaoPt, context)


                    }
                }

            }



        }

        else{

            (holder as ViewHolder2).bind(position)
            context = holder.itemView.context

            holder.imagem.setOnClickListener { v ->

                val intent = Intent(this.context, NewMatchActivity::class.java)
                intent.putExtra("idGamer", idGamer)
                ContextCompat.startActivity(this.context, intent, null)
            }


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
        val delete: ImageButton = itemView.findViewById(R.id.ib_excluir)
        val btVenceu: Button = itemView.findViewById(R.id.button_yes_pt)
        val btPerdeu: Button = itemView.findViewById(R.id.button_no_pt)
        val cardView:CardView = itemView.findViewById(R.id.cardview_post)

        fun updateMatch(idPartida:Int, atualizacaoPt:NovaPartidaModel, context:Context){
            ApiConnectionUtils().matchesService()
                .updateMatch(idPartida, atualizacaoPt)
                .enqueue(object: Callback<Void>{
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(context, "Erro ao atualizar status da partida $t", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if(response.code()==200){
                            Toast.makeText(context, "Partida Atualizada", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "Erro ao atualizar status da partida "+ idPartida.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                })
        }
    }

    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView){
        var organizacao: TextView = itemView.findViewById(R.id.firstName)
        var imagem: ImageButton = itemView.findViewById(R.id.image)
        var cardview : CardView = itemView.findViewById(R.id.cardview)


        internal fun bind(position: Int) {
            // This method will be called anytime a list item is created or update its data
            //Do your stuff here
            organizacao.setTypeface(null, Typeface.BOLD)
        }
    }


}