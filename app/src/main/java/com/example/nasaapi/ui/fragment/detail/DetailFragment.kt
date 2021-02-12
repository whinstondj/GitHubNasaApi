package com.example.nasaapi.ui.fragment.detail

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nasaapi.R
import com.example.nasaapi.data.model.Item
import com.example.nasaapi.databinding.FragmentDetailBinding
import com.example.nasaapi.ui.fragment.list.DetailKeywordAdapter

class DetailFragment : Fragment() {
    lateinit var binding: FragmentDetailBinding

    val viewModel: DetailViewModel by viewModels()

    val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentDetailBinding.inflate(inflater, container,false)
        setupView (args.item)


        Log.e("Mis argumentos", "son: ${args.item}")

        return binding.root
    }

    private fun setupView(item: Item) {
        binding.fragmentDetailtextViewTitle.text = item.data.firstOrNull()?.title ?: ""
        binding.fragmentDetailtextViewDate.text = item.data.firstOrNull()?.date_created ?: "Fecha Desconocida"

        Glide
            .with(requireActivity())
            .load(item.links.firstOrNull()?.href ?: "https://ryrsupport.net/wp-content/uploads/2018/05/que-significa-el-codigo-de-error-http-404-not-foun.jpg")
            .centerCrop()
            .placeholder(R.drawable.ic_navegador)
            .into(binding.fragmentDetailImageViewNasa)

        binding.fragmentDetailButtonShowURL.setOnClickListener {
            searchWeb(item.href)
        }

        val description = when {
            !item.data.firstOrNull()?.description.isNullOrEmpty() -> item.data.firstOrNull()?.description
            !item.data.firstOrNull()?.description_508.isNullOrEmpty() -> item.data.firstOrNull()?.description_508
            else -> "No hay Descripcion"
        }
        binding.fragmentDetailtextViewDescription.text = description

        binding.fragmentDetailRecyclerViewKeywords.apply {
            adapter = DetailKeywordAdapter(item.data.firstOrNull()?.keywords ?: listOf())
            layoutManager = LinearLayoutManager(requireActivity())
            itemAnimator = DefaultItemAnimator()
        }


    }

    private fun searchWeb(query: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, query)
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        }
    }
}