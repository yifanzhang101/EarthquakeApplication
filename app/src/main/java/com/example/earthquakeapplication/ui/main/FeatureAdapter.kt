package com.example.earthquakeapplication.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquakeapplication.R
import com.example.earthquakeapplication.model.EarthquakeFeature
import com.example.earthquakeapplication.ui.detail.DetailActivity
import com.example.earthquakeapplication.util.*

class FeatureAdapter: RecyclerView.Adapter<FeatureAdapter.ViewHolder>() {

    private var featureList: List<EarthquakeFeature> = emptyList()

    fun setData(featureList: List<EarthquakeFeature>) {
        this.featureList = featureList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView
        private val time: TextView
        private val updated: TextView
        private val alertSign: ImageView
        private val magWarning: ImageView

        fun bindItems(feature: EarthquakeFeature) {
            title.text = feature.properties.title
            time.text = "Time: ${convertTimeToReadableFormat(feature.properties.time)}"
            updated.text = "Updated: ${convertTimeToReadableFormat(feature.properties.updated)}"
            alertSign.setColorFilter(getColorCode(feature.properties.alert))
            magWarning.visibility = if (feature.properties.mag >= 7.5) View.VISIBLE else View.GONE
        }

        init {
            title = view.findViewById(R.id.title)
            time = view.findViewById(R.id.time)
            updated = view.findViewById(R.id.updated)
            alertSign = view.findViewById(R.id.alertSign)
            magWarning = view.findViewById(R.id.magWarning)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feature_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val clickedItem = featureList[position]

                // Pass the EarthquakeGeometry to the next activity
                val intent = Intent(holder.itemView.context, DetailActivity::class.java)
                intent.putExtra("earthquakeGeometry", clickedItem.geometry)
                holder.itemView.context.startActivity(intent)
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return featureList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feature = featureList[position]
        holder.bindItems(feature)
    }
}
