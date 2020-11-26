package com.example.projecthunter.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.projecthunter.NewMatchActivity
import com.example.projecthunter.R
import com.example.projecthunter.models.PartidaModel


class ProfileAdapter(val posts: List<PartidaModel>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    internal val VIEW_TYPE_ONE = 1
    internal val VIEW_TYPE_TWO = 2
    internal val VIEW_TYPE_THREE = 3

    private lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //val view : View = LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false)
        //val view2 : View = LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false)

        return if (viewType == VIEW_TYPE_ONE) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_post, parent, false))
        }else if (viewType == VIEW_TYPE_TWO) {
            ViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false))
        }else{
            ViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.new_match_post, parent, false))
        }

    }


    interface ClickEventHandler {
        fun forwardClick(holder: View)
    }

    override fun getItemCount() = posts.size


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

            }
        }

        else{
            (holder as ViewHolder2).bind(position)


            context = holder.itemView.context

            holder.imagem.setOnClickListener { v ->

                val intent = Intent(this.context, NewMatchActivity::class.java)
                ContextCompat.startActivity(this.context, intent, null)
//                val activity=v!!.context as AppCompatActivity
//                val newMatchFragment = NewMatchFragment()
//                activity.supportFragmentManager.beginTransaction().replace(R.id.cardview,
//                    newMatchFragment).addToBackStack(null).commit()
//                holder.imagem.visibility = View.INVISIBLE
//                holder.organizacao.visibility = View.INVISIBLE
//
//                holder.cardview.layoutParams.height =800
//                holder.cardview.layoutParams.width =950
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