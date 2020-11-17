package com.github.fatihsokmen.breakingbad.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.fatihsokmen.breakingbad.R
import com.github.fatihsokmen.breakingbad.data.CharacterModel
import com.github.fatihsokmen.breakingbad.databinding.ViewCharacterBinding

class CharacterAdapter(
    private val handler: CharacterSelectionHandler
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val data = arrayListOf<CharacterModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = DataBindingUtil.inflate<ViewCharacterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.view_character,
            parent,
            false
        )
        return CharacterViewHolder(binding, handler)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int =
        data.size

    fun setData(characters: List<CharacterModel>) {
        data.clear()
        data.addAll(characters)
        notifyDataSetChanged()
    }

    class CharacterViewHolder(
        private val binding: ViewCharacterBinding,
        private val handler: CharacterSelectionHandler
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterModel) {
            binding.model = character
            binding.handler = handler
            binding.executePendingBindings()
        }
    }
}