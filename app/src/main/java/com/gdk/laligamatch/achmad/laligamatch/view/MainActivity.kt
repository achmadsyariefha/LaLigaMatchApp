package com.gdk.laligamatch.achmad.laligamatch.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gdk.laligamatch.achmad.laligamatch.*
import com.gdk.laligamatch.achmad.laligamatch.adapter.FavoriteEventsAdapter
import com.gdk.laligamatch.achmad.laligamatch.adapter.MainAdapter
import com.gdk.laligamatch.achmad.laligamatch.database.database
import com.gdk.laligamatch.achmad.laligamatch.model.ApiRepository
import com.gdk.laligamatch.achmad.laligamatch.model.events.Schedule
import com.gdk.laligamatch.achmad.laligamatch.model.favorite.Favorite
import com.gdk.laligamatch.achmad.laligamatch.presenter.MainPresenter
import com.gdk.laligamatch.achmad.laligamatch.utils.invisible
import com.gdk.laligamatch.achmad.laligamatch.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh

class MainActivity : AppCompatActivity(), MainView {

    private var events: MutableList<Schedule> = mutableListOf()
    private var favorites: MutableList<Favorite> = mutableListOf()

    private var checkFavorite = false

    private lateinit var presenter: MainPresenter
    private lateinit var adapter : MainAdapter
    private lateinit var adapterFavorite: FavoriteEventsAdapter
    private lateinit var presenterEvents: String
    private val nextEvents = "eventsnextleague.php"
    private val pastEvents = "eventspastleague.php"

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeRefreshLayout = findViewById(R.id.main_swipe)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)



        adapter = MainAdapter(this, events) {
            startActivity(intentFor<EventDetailsActivity>(
                    "laliga" to it, "boolean" to true))
            intent.clearTask()
        }

        adapterFavorite = FavoriteEventsAdapter(this, favorites) {
            startActivity(intentFor<EventDetailsActivity>(
                    "laliga_favorite" to it, "boolean" to false))
            intent.clearTask()
        }

        recyclerView.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)
        presenterEvents = pastEvents
        presenter.getSchedule(presenterEvents)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.navigation_view)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        swipeRefreshLayout.onRefresh {
            if(checkFavorite){
                recyclerView.adapter = adapterFavorite
                favorites.clear()
                showFavorite()
            } else {
                presenter.getSchedule(presenterEvents)
            }
        }

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.prev_match -> {
                presenterEvents = pastEvents
                recyclerView.adapter = adapter
                checkFavorite = false
                presenter.getSchedule(presenterEvents)
                return@OnNavigationItemSelectedListener true
            }
            R.id.next_match -> {
                presenterEvents = nextEvents
                recyclerView.adapter = adapter
                checkFavorite = false
                presenter.getSchedule(presenterEvents)
                return@OnNavigationItemSelectedListener true
            }
            R.id.favorites -> {
                recyclerView.adapter = adapterFavorite
                checkFavorite = true
                favorites.clear()
                showFavorite()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    override fun showLoading() {
        progress_bar.visible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showSchedule(data: List<Schedule>) {
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()

    }

    private fun showFavorite(){
        database.use {
            swipeRefreshLayout.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}
