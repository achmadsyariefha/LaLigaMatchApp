package com.gdk.laligamatch.achmad.laligamatch.model.events

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class Schedule(

    @SerializedName ("strHomeTeam")
    val home: String? = null,

    @SerializedName ("strAwayTeam")
    val away: String? = null,

    @SerializedName ("intHomeScore")
    val homeScore: Int? = null,

    @SerializedName ("intAwayScore")
    val awayScore: Int? = null,

    @SerializedName ("dateEvent")
    val date: Date? = null,

    @SerializedName ("strHomeGoalDetails")
    val homeGoalDetails: String? = null,

    @SerializedName ("strAwayGoalDetails")
    val awayGoalDetails: String? = null,

    @SerializedName ("intHomeShots")
    val homeShots: Int? = null,

    @SerializedName ("intAwayShots")
    val awayShots: Int? = null,

    @SerializedName ("strHomeLineupGoalkeeper")
    val homeLineupGoalkeeper: String? = null,

    @SerializedName ("strHomeLineupDefense")
    val homeLineupDefense: String? = null,

    @SerializedName ("strHomeLineupMidfield")
    val homeLineupMidfield: String? = null,

    @SerializedName ("strHomeLineupForward")
    val homeLineupForward: String? = null,

    @SerializedName ("strHomeLineupSubstitutes")
    val homeLineupSubstitutes: String? = null,

    @SerializedName ("strAwayLineupGoalkeeper")
    val awayLineupGoalkeeper: String? = null,

    @SerializedName ("strAwayLineupDefense")
    val awayLineupDefense: String? = null,

    @SerializedName ("strAwayLineupMidfield")
    val awayLineupMidfield: String? = null,

    @SerializedName ("strAwayLineupForward")
    val awayLineupForward: String? = null,

    @SerializedName ("strAwayLineupSubstitutes")
    val awayLineupSubstitutes: String? = null,

    @SerializedName ("idHomeTeam")
    val idHome: String? = null,

    @SerializedName ("idAwayTeam")
    val idAway: String? = null

) : Parcelable


