package com.example.rickandmorty

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.retrofitpkg.Common
import com.example.rickandmorty.retrofitpkg.RetrofitServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class charactersViewModel() : ViewModel() {
    var mService: RetrofitServices = Common.retrofitService
    val characterData: MutableLiveData<List<Result>> = MutableLiveData()
    val characterList: LiveData<List<Result>> get() = characterData
    lateinit var copyOfCharacters: List<Result>

    init {
        getCharacters()
    }

    fun getCharacters(){
        mService.getCharactersList(1).enqueue(object : Callback<CharacterData> {

            override fun onResponse(
                call: Call<CharacterData>,
                response: Response<CharacterData>
            ) {
                val abc = response.body() as CharacterData
                Log.d("tag", abc.results.toString())
                characterData.value = abc.results
                copyOfCharacters = abc.results

            }

            override fun onFailure(call: Call<CharacterData>, t: Throwable) {
                Log.d("фейл", t.message.toString())
                characterData.value = emptyList()
            }
        })
    }

    fun search(s: CharSequence)
    {
        var results = mutableListOf<Result>()
        if (s.isNotBlank()) {
            copyOfCharacters.forEach { character ->
                if (character.name.toLowerCase().contains(s))
                    results.add(character)

            }
        }
        else
            results.addAll(copyOfCharacters)

        characterData.value = results
    }
}