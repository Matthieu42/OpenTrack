package org.mines_ales.opentrack.model;


import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import org.mines_ales.opentrack.R
import org.mines_ales.opentrack.StatActivity
import org.mines_ales.opentrack.ViewTripActivity

class TripAdapter(val tripHistory: TripHistory, val context: Context): RecyclerView.Adapter<TripAdapter.ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                holder.txtName.text = tripHistory.getTrip(position).getStartDate()
                holder.txtTitle.text = tripHistory.getTrip(position).getTotalTime()
                holder.mapButton.setOnClickListener {
                        val intent = Intent(context, ViewTripActivity::class.java)
                        intent.putExtra("id",position)
                        context.startActivity(intent) }
                holder.statButton.setOnClickListener {
                        val intent = Intent(context, StatActivity::class.java)
                        intent.putExtra("id",position)
                        context.startActivity(intent) }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(v)
        }

        override fun getItemCount(): Int {
                return tripHistory.getTrips().size
        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
                val txtName: TextView = itemView.findViewById(R.id.txtName)
                val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
                val mapButton: Button = itemView.findViewById(R.id.openMapActivity)
                val statButton: Button = itemView.findViewById(R.id.openStatActivity)

        }

        }