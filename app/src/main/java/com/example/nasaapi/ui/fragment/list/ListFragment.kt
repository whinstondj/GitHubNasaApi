package com.example.nasaapi.ui.fragment.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasaapi.R
import com.example.nasaapi.databinding.FragmentListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding

    val viewModel: ListViewModel by viewModels()

    lateinit var adapter: NasaListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentListBinding.inflate(inflater, container, false)

        adapter = NasaListAdapter(listOf(), requireActivity())

        //Set Recycler View
        binding.fragmentListRecyclerView.apply {
            adapter = this@ListFragment.adapter
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        //Set swipe refresh gesture
        binding.fragmentListSwipeRefreshLayout.setOnRefreshListener {
            adapter.updateList(listOf())
            viewModel.requestInformation()
        }

        // Observers
        viewModel.getResponse().observe(viewLifecycleOwner, {response ->
            binding.fragmentListSwipeRefreshLayout.isRefreshing = false
                adapter.updateList(response)
        })

        viewModel.getError().observe(viewLifecycleOwner, {error ->
            adapter.updateList(listOf())
            MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("Error")
                    .setMessage(error)
                    .setPositiveButton("Retry") { dialog, which ->
                        viewModel.requestInformation()
                    }
                    .show()
        })
        viewModel.isLoading().observe(viewLifecycleOwner, { loading ->
            binding.fragmentListProgressBar.visibility = if (loading) View.VISIBLE else View.GONE
        })


        viewModel.requestInformation()

        return binding.root
    }
}