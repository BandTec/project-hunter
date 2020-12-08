package com.example.projecthunter.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TeamByTeamAdapter(var posts: MutableList<GamerInfoEquipeModel>, var id:Int, var deleteTeam:Boolean): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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
        holder.excluir.visibility = View.INVISIBLE
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

        if(deleteTeam){
            holder.excluir.visibility = View.VISIBLE
            holder.excluir.setOnClickListener {
                holder.popUpExcluir(context)
                if (holder.excluirEquipe) {
                    ApiConnectionUtils().teamService()
                        .deleteGamerOnTeam(posts[position].idEquipe.idEquipe, id)
                        .enqueue(object : Callback<Void> {
                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(
                                    context,
                                    "Erro ao excluir a equipe",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.code() == 200) {
                                    Toast.makeText(
                                        context,
                                        "Equipe excluída com sucesso!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Erro ao excluir a equipe",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }

                        })
                }
                holder.excluirEquipe = false
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nomeEquipe: TextView = itemView.findViewById(R.id.tv_equipe)
        val fotoEquipe: ImageView = itemView.findViewById(R.id.iv_equipe)
        val excluir: ImageButton = itemView.findViewById(R.id.ib_excluir_equipes)
        var excluirEquipe:Boolean = false

        fun popUpExcluir(context:Context){
            val inflater:LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.pop_up_delete,null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }


            // If API level 23 or higher then execute the code
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut

            }

            // Get the widgets reference from custom view
            val buttonYesPopup = view.findViewById<Button>(R.id.button_yes_popup)
            val buttonNoPopup = view.findViewById<Button>(R.id.button_no_popup)

            // Set click listener for popup window's text view

            // Set a click listener for popup's button widget
            buttonNoPopup.setOnClickListener{
                // Dismiss the popup window
                popupWindow.dismiss()
            }
            buttonYesPopup.setOnClickListener{
                // Dismiss the popup window
                excluirEquipe = true
                popupWindow.dismiss()
            }




            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(R.id.root_layout as ViewGroup)
            popupWindow.showAtLocation(
                R.id.root_layout as ViewGroup, // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
            )
        }
        }

    }


