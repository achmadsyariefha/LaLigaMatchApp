package com.gdk.laligamatch.achmad.laligamatch.model.favorite

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Favorite(val id: Long?, val idHome: String?, val home: String?, val homeScore: String?,
                    val idAway: String?, val away: String?, val awayScore: String?, val date: String?,
                    val homeGoalDetails: String?, val awayGoalDetails: String?, val homeShots: String?,
                    val awayShots: String?, val homeLineupGoalkeeper: String?, val homeLineupDefense: String?, val homeLineupMidfield: String?,val homeLineupForward: String?, val homeLineupSubstitutes: String?,
                    val awayLineupGoalkeeper: String?, val awayLineupDefense: String?, val awayLineupMidfield: String?, val awayLineupForward: String?,val awayLineupSubstitutes: String?) : Parcelable {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val HOME_ID: String = "HOME_ID"
        const val HOME_NAME: String = "HOME_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_ID: String = "AWAY_ID"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val AWAY_SCORE: String = "AWAY_SCORE"
        const val DATE: String = "DATE"
        const val HOME_GOAL_DETAIL: String = "HOME_GOAL_DETAIL"
        const val AWAY_GOAL_DETAIL: String = "AWAY_GOAL_DETAIL"
        const val HOME_SHOT: String = "HOME_SHOT"
        const val AWAY_SHOT: String = "AWAY_SHOT"
        const val HOME_GOAL_KEEPER: String = "HOME_GOAL_KEEPER"
        const val HOME_DEFENSE: String = "HOME_DEFENSE"
        const val HOME_MIDFIELD: String = "HOME_MIDFIELD"
        const val HOME_FORWARD: String = "HOME_FORWARD"
        const val HOME_SUBSTITUTES: String = "HOME_SUBSTITUTES"
        const val AWAY_GOAL_KEEPER: String = "AWAY_GOAL_KEEPER"
        const val AWAY_DEFENSE: String = "AWAY_DEFENSE"
        const val AWAY_MIDFIELD: String = "AWAY_MIDFIELD"
        const val AWAY_FORWARD: String = "AWAY_FORWARD"
        const val AWAY_SUBSTITUTES: String = "AWAY_SUBSTITUTES"
    }
}