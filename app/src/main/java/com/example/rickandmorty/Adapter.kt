package com.example.rickandmorty

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.activities.MainActivity
import java.io.InputStream
import java.net.URL

class Adapter(private val cellClickListener: (AdapterView<*>) -> Unit) : ListAdapter<Result, ViewHolder>(CharacterItemDiffCallback()){

    private val adapterData = mutableListOf<CharacterItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = when (viewType) {
            TYPE_CHARACTER -> R.layout.r_view
            TYPE_BUTTON -> R.layout.load_more_button
            else -> throw IllegalArgumentException("Invalid type")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = currentList[position]

        holder.bindCharacter(data)

        var description: ArrayList<String> = arrayListOf()
        description.add(currentList[position].image)
        description.add(currentList[position].name)
        description.add(currentList[position].status)
        description.add(currentList[position].type)
        description.add(currentList[position].gender)
        description.add(currentList[position].species)
        description.add(currentList[position].origin.name)

        var episodes: ArrayList<String> = arrayListOf()
        currentList[position].episode.forEach { ep ->
            episodes.add(ep)
        }

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(description, episodes)
        }
    }

    override fun getItemCount(): Int = adapterData.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterData[position]){
            is DataModel.Char -> TYPE_CHARACTER
            is DataModel.NextPage -> TYPE_BUTTON
        }
    }

    fun setData(data: List<CharacterItem>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }

    companion object {
        private const val TYPE_CHARACTER = 0
        private const val TYPE_BUTTON = 1

    }
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val avatar: ImageView = view.findViewById(R.id.characterImage)
    val name: TextView = view.findViewById(R.id.nameAC)

    fun bindCharacter(item: CharacterItem.CharacterInfo){
        val i: InputStream = URL(item.character.results.).openStream()
        val png = BitmapFactory.decodeStream(i)
        avatar.setImageBitmap(png)
        name.text = item.character.name
    }

    fun bindButton(item: DataModel.NextPage){
        val nextpagelink = item.next
    }

    fun bind(dataModel: DataModel){
        when (dataModel) {
            is DataModel.Char -> bindCharacter(dataModel)
            is DataModel.NextPage -> bindButton(dataModel)
        }
    }
}


class CharacterItemDiffCallback : DiffUtil.ItemCallback<Result>(){
    override fun areItemsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem

}