package com.lira.matchsimulator.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lira.matchsimulator.R
import com.lira.matchsimulator.data.MatchesAPI
import com.lira.matchsimulator.databinding.ActivityMainBinding
import com.lira.matchsimulator.domain.Match
import com.lira.matchsimulator.ui.adapter.MatchesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var matchesApi: MatchesAPI

    private var adapter: MatchesAdapter = MatchesAdapter(Collections.emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupHttpClient()
        setupMatchesList()
        setupMatchesRefresh()
        setupFloatingActionButton()

    }

    private fun setupHttpClient() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://sblvitor.github.io/matches-simulator-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        matchesApi = retrofit.create(MatchesAPI::class.java)
    }

    private fun setupMatchesList(){
        binding.rvMatches.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvMatches.setHasFixedSize(true)
        binding.rvMatches.adapter = adapter
        findMatchesFromApi()
    }


    private fun setupMatchesRefresh() {
        binding.srlMatches.setOnRefreshListener(this::findMatchesFromApi)
    }

    private fun setupFloatingActionButton() {
        binding.fabSimulate.setOnClickListener { view ->
            view.animate().rotationBy(360F).setDuration(500).setListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    val random = Random()
                    for (i in 0 until adapter.itemCount){
                        val match: Match = adapter.getMatches()[i]
                        match.homeTeam.score = random.nextInt(match.homeTeam.stars + 1)
                        match.awayTeam.score = random.nextInt(match.awayTeam.stars + 1)
                        adapter.notifyItemChanged(i)
                    }
                }
            })
        }
    }

    private fun findMatchesFromApi() {
        binding.srlMatches.isRefreshing = true
        matchesApi.getMatches().enqueue(object : Callback<List<Match>> {
            override fun onResponse(call: Call<List<Match>>, response: Response<List<Match>>) {
                if (response.isSuccessful) {
                    val matches: List<Match> = response.body()!!
                    adapter = MatchesAdapter(matches)
                    binding.rvMatches.adapter = adapter
                } else {
                    showErrorMessage()
                }
                binding.srlMatches.isRefreshing = false
            }

            override fun onFailure(call: Call<List<Match>>, t: Throwable) {
                showErrorMessage()
                binding.srlMatches.isRefreshing = false
            }

        })
    }

    private fun showErrorMessage(){
        Snackbar.make(binding.fabSimulate, R.string.error_api, Snackbar.LENGTH_LONG).show()
    }

}