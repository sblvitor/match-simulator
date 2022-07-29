package com.lira.matchsimulator.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lira.matchsimulator.R
import com.lira.matchsimulator.databinding.MatchItemBinding
import com.lira.matchsimulator.domain.Match
import com.lira.matchsimulator.ui.DetailActivity

class MatchesAdapter(private val items: List<Match>): RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    fun getMatches(): List<Match>{
        return items
    }

    class ViewHolder(binding: MatchItemBinding): RecyclerView.ViewHolder(binding.root){
        val ivHomeTeam = binding.ivHomeTeam
        val tvHomeTeamName = binding.tvHomeTeamName
        val tvHomeTeamScore = binding.tvHomeTeamScore
        val ivAwayTeam = binding.ivAwayTeam
        val tvAwayTeamName = binding.tvAwayTeamName
        val tvAwayTeamScore = binding.tvAwayTeamScore
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val context: Context = holder.itemView.context

        Glide
            .with(context)
            .load(item.homeTeam.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.ivHomeTeam)

        Glide
            .with(context)
            .load(item.awayTeam.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.ivAwayTeam)

        holder.tvHomeTeamName.text = item.homeTeam.name
        holder.tvAwayTeamName.text = item.awayTeam.name

        if(item.homeTeam.score != null)
            holder.tvHomeTeamScore.text = item.homeTeam.score.toString()
        if(item.awayTeam.score != null)
            holder.tvAwayTeamScore.text = item.awayTeam.score.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.Extras.MATCH, item)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}