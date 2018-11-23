package com.gdk.laligamatch.achmad.laligamatch.presenter

import com.gdk.laligamatch.achmad.laligamatch.coroutines.CoroutineContextProvider
import com.gdk.laligamatch.achmad.laligamatch.model.ApiRepository
import com.gdk.laligamatch.achmad.laligamatch.model.events.ScheduleResponse
import com.gdk.laligamatch.achmad.laligamatch.model.events.TheSportDBApi
import com.gdk.laligamatch.achmad.laligamatch.view.MainView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getSchedule(event: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getSchedule(event)).await(),
                        ScheduleResponse::class.java)


            view.hideLoading()
            view.showSchedule(data.events)

        }
    }
}