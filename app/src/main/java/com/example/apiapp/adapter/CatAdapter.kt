package com.example.apiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apiapp.databinding.RvCatItemsBinding
import com.example.apiapp.model.NetCat


class CatAdapter : RecyclerView.Adapter<CatAdapter.ViewHolder>() {
    private val data: MutableList<NetCat> = mutableListOf()

    fun submitList(cats: List<NetCat>) {
        data.clear()
        data.addAll(cats)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val catItemsBinding: RvCatItemsBinding) :
        RecyclerView.ViewHolder(catItemsBinding.root) {

        fun bind(catProperties: NetCat) {
            catItemsBinding.tvTitle.text = catProperties.id
            Glide.with(catItemsBinding.ivCats.context).load(catProperties.url).centerCrop()
                .into(catItemsBinding.ivCats)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val catItemsBinding = RvCatItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )

        // Return a new holder instance
        return ViewHolder(catItemsBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}
