package com.example.rickandmorty.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.Adapter
import com.example.rickandmorty.R
import com.example.rickandmorty.charactersViewModel

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: GridLayoutManager
    private val adapter by lazy { Adapter(this) }
    private val model: charactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        setContentView(R.layout.activity_main)

        title = ""

        val recyclerView: RecyclerView = findViewById(R.id.rView)
        layoutManager = GridLayoutManager(this,2)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        observe()

        val searchET: EditText = findViewById(R.id.search_edit_text)
        searchET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                model.search(s)
            }
        })
    }

    fun onCellClickListener(data: ArrayList<String>, ep: ArrayList<String>){
        intent = Intent(this, CharacterActivity::class.java)
        intent.putExtra("CharacterData", data)
        intent.putExtra("CharacterEpis", ep)
        startActivity(intent)
    }

    fun observe(){
        model.characterList.observe(this, Observer{
            adapter.submitList(it)
        })
    }
}