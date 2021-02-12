package com.example.nasaapi.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NasaResponseDataModel(
    val collection: Collection
)

data class Collection(
    val href: String,
    val items: List<Item>,
    val links: List<LinkX>,
    val metadata: Metadata,
    val version: String
)

data class Item(
    @SerializedName("data")
    val data: List<Data>,
    val href: String,
    val links: List<Link>
) : Serializable

data class LinkX(
    val href: String,
    val prompt: String,
    val rel: String
)

data class Metadata(
    val total_hits: Int
)

data class Data(
    val album: List<String>,
    val center: String,
    val date_created: String,
    val description: String,
    val description_508: String,
    val keywords: List<String>,
    val location: String,
    val media_type: String,
    val nasa_id: String,
    val photographer: String,
    val secondary_creator: String,
    val title: String
)

data class Link(
    val href: String,
    val rel: String,
    val render: String
)