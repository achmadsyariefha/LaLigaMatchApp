package com.gdk.laligamatch.achmad.laligamatch.presenter

import com.gdk.laligamatch.achmad.laligamatch.coroutines.CoroutineContextProvider
import com.gdk.laligamatch.achmad.laligamatch.model.ApiRepository
import com.gdk.laligamatch.achmad.laligamatch.model.team.LaLigaTeamDetailsApi
import com.gdk.laligamatch.achmad.laligamatch.model.team.TeamResponse
import com.gdk.laligamatch.achmad.laligamatch.view.EventDetailsView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventDetailsPresenter (private val view: EventDetailsView,
                             private val apiRepository: ApiRepository,
                             private val gson: Gson,
                             private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getDetails(team: String) {

        GlobalScope.launch(contextPool.main) {
            val data = gson.fromJson(apiRepository
                        .doRequest(LaLigaTeamDetailsApi.getDetails(team)).await(),
                        TeamResponse::class.java)


                    view.showTeamDetails(data.teams, team)

        }
    }
}