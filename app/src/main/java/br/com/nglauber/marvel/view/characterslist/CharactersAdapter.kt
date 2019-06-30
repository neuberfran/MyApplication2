package br.com.nglauber.marvel.view.characterslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.nglauber.marvel.R
import br.com.nglauber.marvel.extensions.load
import br.com.nglauber.marvel.model.api.entity.Character
import kotlinx.android.synthetic.main.item_character.view.*


class CharactersAdapter() : RecyclerView.Adapter<CharactersAdapter.VH>() {

    private val items = mutableListOf<Character>()

    fun onCreateViewHolder(parent: ViewGroup? , viewType: Int): VH {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_character , parent , false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH , position: Int) {
        val character = items[position]
        holder.txtName.text = character.name
        holder.imgThumbnail.load("${character.thumbnail.path}/standard_medium.${character.thumbnail.extension}")
    }

    override fun getItemCount(): Int = items.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgThumbnail = itemView.imgThumbnail
        val txtName = itemView.txtName
    }

    fun setItems(characters: List<Character>) {
        items.clear()
        items.addAll(characters)
        notifyDataSetChanged()
    }

}