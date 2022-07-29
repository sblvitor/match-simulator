package com.lira.matchsimulator.data

import com.lira.matchsimulator.domain.Match
import retrofit2.Call
import retrofit2.http.GET

interface MatchesAPI {

    @GET("matches.json")
    fun getMatches(): Call<List<Match>>

}