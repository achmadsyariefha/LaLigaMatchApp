package com.gdk.laligamatch.achmad.laligamatch.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdk.laligamatch.achmad.laligamatch.model.events.Schedule
import com.gdk.laligamatch.achmad.laligamatch.R
import kotlinx.android.synthetic.main.item_list.view.*
import java.text.SimpleDateFormat

class MainAdapter (private val context: Context, private val events: List<Schedule>, private val listener: (Schedule) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))


    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size

}


class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){

    @SuppressLint("SimpleDateFormat")
    fun bindItem(events: Schedule, listener: (Schedule) -> Unit) {
        val dateEvent = SimpleDateFormat("EEE, dd MMM yyyy").format(events.date).toString()
        itemView.date.text = dateEvent
        itemView.home_team_name.text = events.home
        itemView.away_team_name.text = events.away
        if(events.homeScore!=null){
            itemView.home_team_score.text = events.homeScore.toString()
            itemView.away_team_score.text = events.awayScore.toString()
        } else {
            itemView.home_team_score.text = ""
            itemView.away_team_score.text = ""
        }

        itemView.setOnClickListener { listener(events) }


    }
}
