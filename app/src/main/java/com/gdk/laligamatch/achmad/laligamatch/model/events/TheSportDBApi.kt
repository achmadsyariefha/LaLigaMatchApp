package com.gdk.laligamatch.achmad.laligamatch.model.events

import com.gdk.laligamatch.achmad.laligamatch.BuildConfig

object TheSportDBApi {
    fun getSchedule(event: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/" + event + "?id=4335"
    }
}