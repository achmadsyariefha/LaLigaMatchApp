package com.gdk.laligamatch.achmad.laligamatch.view

import com.gdk.laligamatch.achmad.laligamatch.model.events.Schedule

interface MainView{
    fun showLoading()
    fun hideLoading()
    fun showSchedule(data: List<Schedule>)
}