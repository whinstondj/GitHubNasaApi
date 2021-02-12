package com.example.nasaapi.ui.fragment.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapi.R
import com.example.nasaapi.data.model.Item
import com.example.nasaapi.databinding.ItemNasaListBinding

class NasaListAdapter(private var dataSet: List<Item>, private val context: Context, private val listener: (item: Item) -> Unit) : RecyclerView.Adapter<NasaListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemNasaListBinding) : RecyclerView.ViewHolder(binding.root)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNasaListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.binding.apply {
            itemNasaListtextView.text = item.data.firstOrNull()?.title ?: "N/A"
            itemTextViewDescription.text = item.data.firstOrNull()?.description_508 ?: "N/A"

            Glide
                .with(context)
                .load(item.links.firstOrNull()?.href ?: "https://ryrsupport.net/wp-content/uploads/2018/05/que-significa-el-codigo-de-error-http-404-not-foun.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_navegador)
                .into(itemNasaListImageView);
        }
        viewHolder.itemView.setOnClickListener {
            listener.invoke(item)
        }
    }


    override fun getItemCount() = dataSet.size

    fun updateList(newList: List<Item>) {
        dataSet = newList
        notifyDataSetChanged()
    }

}