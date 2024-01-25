package com.example.earthquakeapplication.ui.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.earthquakeapplication.R
import com.example.earthquakeapplication.viewmodel.EarthquakeViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        // query parameters
        val format = "geojson"
        val startTime = "2023-01-01"
        val endTime = "2024-01-01"
        val minMagnitude = 7.0
    }

    private val earthquakeViewModel: EarthquakeViewModel by lazy {
        ViewModelProvider(this)[EarthquakeViewModel::class.java]
    }
    private lateinit var boardTitle: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var featureRecyclerView: RecyclerView
    private lateinit var featureAdapter: FeatureAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boardTitle = findViewById(R.id.boardTitle)
        progressBar = findViewById(R.id.progressBar)
        featureRecyclerView = findViewById(R.id.featureRecyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        featureRecyclerView.layoutManager = LinearLayoutManager(this)
        featureAdapter = FeatureAdapter()
        featureRecyclerView.adapter = featureAdapter

        // Observe LiveData changes
        earthquakeViewModel.earthquakeData.observe(this) { earthquakes ->
            featureAdapter.setData(earthquakes)
            hideProgressBar()
        }

        earthquakeViewModel.error.observe(this) { message ->
            // Handle error response
            boardTitle.text = message
            hideProgressBar()
        }

        swipeRefreshLayout.setOnRefreshListener {
            fetchData()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        earthquakeViewModel.getEarthquakeData(format, startTime, endTime, minMagnitude)
        showProgressBar()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun showProgressBar() {
        boardTitle.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        boardTitle.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
}
