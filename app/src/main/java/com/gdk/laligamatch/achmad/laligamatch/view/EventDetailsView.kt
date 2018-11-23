package com.gdk.laligamatch.achmad.laligamatch.view

import com.gdk.laligamatch.achmad.laligamatch.model.team.Team

interface EventDetailsView {
    fun showTeamDetails(data: List<Team>, name: String)
    fun hideLoading()
}