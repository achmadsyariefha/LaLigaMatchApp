package com.gdk.laligamatch.achmad.laligamatch

import com.gdk.laligamatch.achmad.laligamatch.coroutines.TestContextProvider
import com.gdk.laligamatch.achmad.laligamatch.model.ApiRepository
import com.gdk.laligamatch.achmad.laligamatch.model.events.Schedule
import com.gdk.laligamatch.achmad.laligamatch.model.events.ScheduleResponse
import com.gdk.laligamatch.achmad.laligamatch.model.events.TheSportDBApi
import com.gdk.laligamatch.achmad.laligamatch.presenter.MainPresenter
import com.gdk.laligamatch.achmad.laligamatch.view.MainView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MainPresenterTest {
    @Test
    fun testGetSchedule(){
        val events: MutableList<Schedule> = mutableListOf()
        val response = ScheduleResponse(events)
        val event = "eventspastleague.php"

        GlobalScope.launch {
            `when`(gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getSchedule(event)).await(),
                    ScheduleResponse::class.java
            )).thenReturn(response)

            mainPresenter.getSchedule(event)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showSchedule(events)
            Mockito.verify(view).hideLoading()
        }

    }

    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var mainPresenter: MainPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        mainPresenter = MainPresenter(view, apiRepository, gson, TestContextProvider())
    }
}