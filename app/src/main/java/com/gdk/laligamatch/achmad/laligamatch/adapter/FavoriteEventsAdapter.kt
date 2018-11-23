package com.gdk.laligamatch.achmad.laligamatch.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdk.laligamatch.achmad.laligamatch.R
import com.gdk.laligamatch.achmad.laligamatch.model.favorite.Favorite
import kotlinx.android.synthetic.main.item_list.view.*

class FavoriteEventsAdapter (private val context: Context, private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            FavoriteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))


    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}


class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){

    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
        itemView.date.text = favorite.date
        itemView.home_team_name.text = favorite.home
        itemView.away_team_name.text = favorite.away
        if(favorite.homeScore!=null){
            itemView.home_team_score.text = favorite.homeScore.toString()
            itemView.away_team_score.text = favorite.awayScore.toString()
        } else {
            itemView.home_team_score.text = ""
            itemView.away_team_score.text = ""
        }

        itemView.setOnClickListener { listener(favorite) }


    }
}