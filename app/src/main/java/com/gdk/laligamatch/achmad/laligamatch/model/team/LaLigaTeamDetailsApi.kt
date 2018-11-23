package com.gdk.laligamatch.achmad.laligamatch.model.team

import android.net.Uri
import com.gdk.laligamatch.achmad.laligamatch.BuildConfig

object LaLigaTeamDetailsApi {
    fun getDetails(target: String?): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("lookupteam.php")
                .appendQueryParameter("id", target)
                .build()
                .toString()
    }
}