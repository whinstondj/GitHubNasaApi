package com.example.nasaapi.ui.fragment.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapi.R
import com.example.nasaapi.data.model.Item
import com.example.nasaapi.databinding.ItemKeywordListBinding
import com.example.nasaapi.databinding.ItemNasaListBinding

class DetailKeywordAdapter(private var dataSet: List<String>) : RecyclerView.Adapter<DetailKeywordAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemKeywordListBinding) : RecyclerView.ViewHolder(binding.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemKeywordListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]

        viewHolder.binding.apply {
            itemNasaListtextViewTitle.text = item.toString()
        }
    }


    override fun getItemCount() = dataSet.size

}