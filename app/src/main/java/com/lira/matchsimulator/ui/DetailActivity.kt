package com.lira.matchsimulator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.lira.matchsimulator.databinding.ActivityDetailBinding
import com.lira.matchsimulator.domain.Match

class DetailActivity : AppCompatActivity() {

    object Extras {
        const val  MATCH = "EXTRA_MATCH"
    }

    private lateinit var binding: ActivityDetailBinding

    private lateinit var match: Match

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()

    }

    private fun loadMatchFromExtra(){
        intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let {
            Glide
                .with(this)
                .load(it.place.image)
                .centerCrop()
                .into(binding.ivPlace)

            supportActionBar?.title = it.place.name
            binding.tvDescription.text = it.description

            Glide
                .with(this)
                .load(it.homeTeam.image)
                .centerCrop()
                .into(binding.ivHomeTeam)

            Glide
                .with(this)
                .load(it.awayTeam.image)
                .centerCrop()
                .into(binding.ivAwayTeam)

            if(it.homeTeam.score != null)
                binding.tvHomeTeamScore.text = it.homeTeam.score.toString()
            if(it.homeTeam.score != null)
                binding.tvAwayTeamScore.text = it.awayTeam.score.toString()

            binding.tvHomeTeamName.text = it.homeTeam.name
            binding.tvAwayTeamName.text = it.awayTeam.name

            binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()
            binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()
        }
    }
}