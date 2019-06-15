package org.mines_ales.opentrack

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import org.mines_ales.opentrack.model.OpenTrackData
import org.mines_ales.opentrack.model.TripAdapter

class TripHistoryActivity : AppCompatActivity() {

    private lateinit var historyRecyclerList : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_history)
        this.historyRecyclerList = findViewById(R.id.historyList)
        this.historyRecyclerList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val adapter = TripAdapter(OpenTrackData.tripHistory,this)

        this.historyRecyclerList.adapter = adapter

    }

}
