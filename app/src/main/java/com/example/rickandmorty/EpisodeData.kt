package com.example.rickandmorty

data class EpisodeData(
    val info: EpisodeInfo,
    val results: List<EpisodeResult>
)

data class EpisodeInfo(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: Any
)

data class EpisodeResult(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)