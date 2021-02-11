package com.example.nasaapi.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapi.data.NasaRepository
import com.example.nasaapi.data.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

class ListViewModel: ViewModel() {

    private val response = MutableLiveData<List<Item>>()
    fun getResponse(): LiveData<List<Item>> = response

    private val error = MutableLiveData<String>()
    fun getError(): LiveData<String> = error

    private val loading = MutableLiveData<Boolean>()
    fun isLoading(): LiveData<Boolean> = loading


    fun requestInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loading.postValue(true)
                val items = NasaRepository().requestNasaPictures("sun")
                response.postValue(items)
                loading.postValue(false)
            } catch(e:Exception) {
                loading.postValue(false)
                when (e){
                    is HttpException -> {
                        error.postValue(e.code().toString())
                    }
                    is UnknownHostException -> {
                        error.postValue("No Tienes conexion a Internet")
                    }
                }
            }
        }
    }

}