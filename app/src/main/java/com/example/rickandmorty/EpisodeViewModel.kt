package com.example.rickandmorty

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.retrofitpkg.Common
import com.example.rickandmorty.retrofitpkg.RetrofitServices
import com.example.rickandmorty.retrofitpkg.RetrofitServicesEp
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread


class EpisodeViewModel(epis: ArrayList<String>): ViewModel() {
    private var mService: RetrofitServicesEp = Common.retrofitServiceEp
    val episodesData: MutableLiveData<List<EpisodeResult>> = MutableLiveData()
    val episodeList: LiveData<List<EpisodeResult>> get() = episodesData
    var list: ArrayList<String> = epis
    var copy: ArrayList<EpisodeResult> = arrayListOf()

    init {
        getInfo()
    }

    fun getInfo(){
            var list2: ArrayList<String> = arrayListOf()
            for (i in list.indices) {
                list2.add(list[i].substring(40))
            }
            Log.d("list2", list2.toString())
            list2.forEach { ep ->
                mService.getEpisodeList(ep).enqueue(object : Callback<EpisodeResult> {
                    override fun onResponse(
                        call: Call<EpisodeResult>,
                        response: Response<EpisodeResult>
                    ) {
                        val abc = response.body() as EpisodeResult

                        copy.add(abc)

                        Log.d("EPEP", abc.episode)

                    }

                    override fun onFailure(call: Call<EpisodeResult>, t: Throwable) {
                        Log.d("фейл", t.message.toString())
                        episodesData.value = emptyList()
                    }
                })

            }
        Thread.sleep(1000)
        episodesData.value = copy
    }

}