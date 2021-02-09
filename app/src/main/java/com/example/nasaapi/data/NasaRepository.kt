package com.example.nasaapi.data

import com.example.nasaapi.data.model.Item
import com.example.nasaapi.data.network.NasaNetwork

class NasaRepository {

    fun requestNasaPictures(pictureType : String): List <Item> {
        return NasaNetwork().requestNasaImages(pictureType).collection.items
    }

}