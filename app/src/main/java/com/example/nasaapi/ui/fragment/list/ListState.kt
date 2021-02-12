package com.example.nasaapi.ui.fragment.list

import com.example.nasaapi.data.model.Item
import java.io.Serializable

data class ListState(
        val picturesList: List<Item> = listOf()
) : Serializable
