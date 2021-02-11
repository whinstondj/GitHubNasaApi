package com.example.nasaapi.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapi.base.BaseState
import com.example.nasaapi.data.NasaRepository
import com.example.nasaapi.data.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import java.net.UnknownHostException

class ListViewModel: ViewModel() {

    private val state = MutableLiveData<BaseState>()
    fun getState(): LiveData<BaseState> = state


    fun requestInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.postValue(BaseState.Loading())
                val items = NasaRepository().requestNasaPictures("sun")
                state.postValue(BaseState.Normal(ListState(items)))
            } catch(e:Exception) {
                state.postValue(BaseState.Error(e))
            }
        }
    }

}

/**
when (e){
is HttpException -> {
error.postValue(e.code().toString())
}
is UnknownHostException -> {
error.postValue("No Tienes conexion a Internet")
}
}
 */