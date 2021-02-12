package com.example.nasaapi.ui.fragment.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasaapi.R
import com.example.nasaapi.base.BaseExtraData
import com.example.nasaapi.base.BaseState
import com.example.nasaapi.base.noInternetConnectivity
import com.example.nasaapi.databinding.FragmentListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.HttpException
import java.net.UnknownHostException

class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding

    val viewModel: ListViewModel by viewModels()

    lateinit var adapter: NasaListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentListBinding.inflate(inflater, container, false)

        viewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is BaseState.Error -> {
                    updateToErrorState(state.dataError)
                }
                is BaseState.Loading -> {
                    //state.dataLoading
                    updateToLoadingState(state.dataLoading)
                }
                is BaseState.Normal -> {
                    //state.data
                    updateToNormalState(state.data as ListState)
                }

            }

        })

        setupView()
        viewModel.requestInformation()
        return binding.root
    }

    /**
     * Setup View Elements
     */

    private fun setupView() {
        //Set Recycler View
        adapter = NasaListAdapter(listOf(), requireActivity()) {item ->
            //findNavController().navigate(R.id.action_listFragment_to_detailFragment)
            findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailFragment(item))
        }
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
    }

    /**
     * Normal state, everything works
     */

    private fun updateToNormalState(data: ListState) {
        binding.fragmentListProgressBar.visibility = View.GONE
        binding.fragmentListSwipeRefreshLayout.isRefreshing = false
        adapter.updateList((data).picturesList)
    }

    /**
     * Loading State, wait until results
     */

    private fun updateToLoadingState(dataLoading: BaseExtraData?) {
        binding.fragmentListProgressBar.visibility = View.VISIBLE
    }

    /**
     * Error state :(
     */

    private fun updateToErrorState(dataError: Throwable) {
        //state.dataError
        adapter.updateList(listOf())
        binding.fragmentListProgressBar.visibility = View.GONE
        val msg = when (dataError) {
            is HttpException -> "Fatal error: " + dataError.code().toString()
            is UnknownHostException -> "No tienes conexion a Internet"
            is noInternetConnectivity -> "Endienda la Wifi o los datos moviles e intente de nuevo"
            else -> "Error generico"
        }

        MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Error")
                .setMessage(msg)
                .setPositiveButton("Reintentar") { dialog, which ->
                    viewModel.requestInformation()
                }
                .show()


    }
}