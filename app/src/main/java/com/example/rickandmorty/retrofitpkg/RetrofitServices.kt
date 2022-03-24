package com.example.rickandmorty.retrofitpkg

import com.example.rickandmorty.CharacterData
import com.example.rickandmorty.EpisodeData
import com.example.rickandmorty.EpisodeResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {
    @GET("character?")
    fun getCharactersList(@Query("page")pageid: Int): Call<CharacterData>
}

interface RetrofitServicesEp {
    @GET("episode/{id}")
    fun getEpisodeList(@Path("id")epid: String): Call<EpisodeResult>
}