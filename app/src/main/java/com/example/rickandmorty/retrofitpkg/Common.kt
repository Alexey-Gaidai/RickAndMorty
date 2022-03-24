package com.example.rickandmorty.retrofitpkg

object Common {
    private val BASE_URL = "https://rickandmortyapi.com/api/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
    val retrofitServiceEp: RetrofitServicesEp
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServicesEp::class.java)
}