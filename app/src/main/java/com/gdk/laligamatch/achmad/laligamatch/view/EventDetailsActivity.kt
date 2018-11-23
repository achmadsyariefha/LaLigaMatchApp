package com.gdk.laligamatch.achmad.laligamatch.view

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.gdk.laligamatch.achmad.laligamatch.R
import com.gdk.laligamatch.achmad.laligamatch.database.database
import com.gdk.laligamatch.achmad.laligamatch.model.ApiRepository
import com.gdk.laligamatch.achmad.laligamatch.model.events.Schedule
import com.gdk.laligamatch.achmad.laligamatch.model.favorite.Favorite
import com.gdk.laligamatch.achmad.laligamatch.model.team.Team
import com.gdk.laligamatch.achmad.laligamatch.presenter.EventDetailsPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_details.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat

class EventDetailsActivity : AppCompatActivity(), EventDetailsView {

    private var teams: MutableList<Team> = mutableListOf()

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var presenter: EventDetailsPresenter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var mHomeId: String = ""
    private var mAwayId: String = ""

    private var check = true

    private lateinit var itemCache: Schedule
    private lateinit var itemCacheFavorite: Favorite



    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var item: Schedule
        lateinit var itemFavorite: Favorite

        check = intent.getBooleanExtra("boolean", true)

        if(check){
            item = intent.getParcelableExtra("laliga")
            itemCache = item

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val date = SimpleDateFormat("EEE, dd MMM yyyy").format(item.date).toString()

            mHomeId = item.idHome.toString()
            mAwayId = item.idAway.toString()

            setContentView(R.layout.activity_event_details)

            swipeRefreshLayout = details_swipe

            details_date.text = date

            home_name.text = item.home
            away_name.text = item.away

            if (item.homeScore!= null){
                home_score.text = item.homeScore.toString()+""
                home_goals.text = item.homeGoalDetails
                home_shots.text = item.homeShots.toString()+""
                home_gk.text = item.homeLineupGoalkeeper
                home_def.text = item.homeLineupDefense
                home_mid.text = item.homeLineupMidfield
                home_fwd.text = item.homeLineupForward
                home_sub.text = item.homeLineupSubstitutes

                away_score.text = item.awayScore.toString()
                away_goals.text = item.awayGoalDetails
                away_shots.text = item.awayShots.toString()
                away_gk.text = item.awayLineupGoalkeeper
                away_def.text = item.awayLineupDefense
                away_mid.text = item.awayLineupMidfield
                away_fwd.text = item.awayLineupForward
                away_sub.text = item.awayLineupSubstitutes

            }

            val request = ApiRepository()
            val gson = Gson()
            presenter = EventDetailsPresenter(this, request, gson)
            presenter.getDetails(mHomeId)

            presenter.getDetails(mAwayId)

            swipeRefreshLayout.onRefresh {
                presenter.getDetails(mHomeId)
                presenter.getDetails(mAwayId)
            }

        } else {
            itemFavorite = intent.getParcelableExtra("laliga_favorite")
            itemCacheFavorite = itemFavorite

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            mHomeId = itemFavorite.idHome.toString()
            mAwayId = itemFavorite.idAway.toString()

            setContentView(R.layout.activity_event_details)

            swipeRefreshLayout = details_swipe

            details_date.text = itemFavorite.date

            home_name.text = itemFavorite.home
            away_name.text = itemFavorite.away

            if (itemFavorite.homeScore!="null"){
                home_score.text = itemFavorite.homeScore.toString()
                home_goals.text = itemFavorite.homeGoalDetails
                home_shots.text = itemFavorite.homeShots.toString()
                home_gk.text = itemFavorite.homeLineupGoalkeeper
                home_def.text = itemFavorite.homeLineupDefense
                home_mid.text = itemFavorite.homeLineupMidfield
                home_fwd.text = itemFavorite.homeLineupForward
                home_sub.text = itemFavorite.homeLineupSubstitutes

                away_score.text = itemFavorite.awayScore.toString()
                away_goals.text = itemFavorite.awayGoalDetails
                away_shots.text = itemFavorite.awayShots.toString()
                away_gk.text = itemFavorite.awayLineupGoalkeeper
                away_def.text = itemFavorite.awayLineupDefense
                away_mid.text = itemFavorite.awayLineupMidfield
                away_fwd.text = itemFavorite.awayLineupForward
                away_sub.text = itemFavorite.awayLineupSubstitutes

            }

            favoriteState()

            val request = ApiRepository()
            val gson = Gson()
            presenter = EventDetailsPresenter(this, request, gson)
            presenter.getDetails(mHomeId)

            presenter.getDetails(mAwayId)

            swipeRefreshLayout.onRefresh {
                presenter.getDetails(mHomeId)
                presenter.getDetails(mAwayId)
            }
        }

    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(HOME_ID = {id})",
                            "id" to mHomeId)
            val favorite = result.parseList(classParser<Favorite>())
            if(!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorite_star, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if(isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun addToFavorite(){
        try {
            if(check){
                database.use { insert(Favorite.TABLE_FAVORITE,
                        Favorite.HOME_ID to itemCache.idHome.toString(),
                        Favorite.HOME_NAME to itemCache.home.toString(),
                        Favorite.HOME_SCORE to itemCache.homeScore.toString(),
                        Favorite.AWAY_ID to itemCache.idAway.toString(),
                        Favorite.AWAY_NAME to itemCache.away.toString(),
                        Favorite.AWAY_SCORE to itemCache.awayScore.toString(),
                        Favorite.DATE to SimpleDateFormat("EEE, dd MMM yyyy").format(itemCache.date).toString(),
                        Favorite.HOME_GOAL_DETAIL to itemCache.homeGoalDetails.toString(),
                        Favorite.AWAY_GOAL_DETAIL to itemCache.awayGoalDetails.toString(),
                        Favorite.HOME_SHOT to itemCache.homeShots.toString(),
                        Favorite.AWAY_SHOT to itemCache.awayShots.toString(),
                        Favorite.HOME_GOAL_KEEPER to itemCache.homeLineupGoalkeeper.toString(),
                        Favorite.HOME_DEFENSE to itemCache.homeLineupDefense.toString(),
                        Favorite.HOME_MIDFIELD to itemCache.homeLineupMidfield.toString(),
                        Favorite.HOME_FORWARD to itemCache.homeLineupForward.toString(),
                        Favorite.HOME_SUBSTITUTES to itemCache.homeLineupSubstitutes.toString(),
                        Favorite.AWAY_GOAL_KEEPER to itemCache.awayLineupGoalkeeper.toString(),
                        Favorite.AWAY_DEFENSE to itemCache.awayLineupDefense.toString(),
                        Favorite.AWAY_MIDFIELD to itemCache.awayLineupMidfield.toString(),
                        Favorite.AWAY_FORWARD to itemCache.awayLineupForward.toString(),
                        Favorite.AWAY_SUBSTITUTES to itemCache.awayLineupSubstitutes.toString()) }
            }
            else { database.use { insert(Favorite.TABLE_FAVORITE,
                    Favorite.HOME_ID to itemCacheFavorite.idHome.toString(),
                    Favorite.HOME_NAME to itemCacheFavorite.home.toString(),
                    Favorite.HOME_SCORE to itemCacheFavorite.homeScore.toString(),
                    Favorite.AWAY_ID to itemCacheFavorite.idAway.toString(),
                    Favorite.AWAY_NAME to itemCacheFavorite.away.toString(),
                    Favorite.AWAY_SCORE to itemCacheFavorite.awayScore.toString(),
                    Favorite.DATE to SimpleDateFormat("EEE, dd MMM yyyy").format(itemCacheFavorite.date).toString(),
                    Favorite.HOME_GOAL_DETAIL to itemCacheFavorite.homeGoalDetails.toString(),
                    Favorite.AWAY_GOAL_DETAIL to itemCacheFavorite.awayGoalDetails.toString(),
                    Favorite.HOME_SHOT to itemCacheFavorite.homeShots.toString(),
                    Favorite.AWAY_SHOT to itemCacheFavorite.awayShots.toString(),
                    Favorite.HOME_GOAL_KEEPER to itemCacheFavorite.homeLineupGoalkeeper.toString(),
                    Favorite.HOME_DEFENSE to itemCacheFavorite.homeLineupDefense.toString(),
                    Favorite.HOME_MIDFIELD to itemCacheFavorite.homeLineupMidfield.toString(),
                    Favorite.HOME_FORWARD to itemCacheFavorite.homeLineupForward.toString(),
                    Favorite.HOME_SUBSTITUTES to itemCacheFavorite.homeLineupSubstitutes.toString(),
                    Favorite.AWAY_GOAL_KEEPER to itemCacheFavorite.awayLineupGoalkeeper.toString(),
                    Favorite.AWAY_DEFENSE to itemCacheFavorite.awayLineupDefense.toString(),
                    Favorite.AWAY_MIDFIELD to itemCacheFavorite.awayLineupMidfield.toString(),
                    Favorite.AWAY_FORWARD to itemCacheFavorite.awayLineupForward.toString(),
                    Favorite.AWAY_SUBSTITUTES to itemCacheFavorite.awayLineupSubstitutes.toString())

            }
        }
            snackbar(swipeRefreshLayout, "Added to Favorites").show()
    } catch (e: SQLiteConstraintException){
            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE,
                        "(HOME_ID = {id})",
                        "id" to mHomeId)
            }
            snackbar(swipeRefreshLayout, "Removed to Favorites").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefreshLayout, e.localizedMessage).show()
        }
    }

    private fun setFavorite(){
        if(isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun loadBadge(team: Team, teamBadge: ImageView){
        Picasso.get().load(team.teamBadge).into(teamBadge)
    }

    override fun showTeamDetails(data: List<Team>, name: String) {
        teams.clear()
        teams.addAll(data)
        if(name==mHomeId){
            loadBadge(teams[0], home_image)
        } else {
            loadBadge(teams[0], away_image)
        }
    }
}
