package com.example.mobiletry.models;

import java.io.Serializable

data class Beer(
    val id: Int,
    val user: String,
    val brewery: String,
    val name: String,
    val style: String,
    val abv: Double,
    val volume: Int,
    val pictureURL: String,
    val howMany: Int)
    :Serializable { constructor(user: String, brewery: String, name: String,
                                style: String, abv: Double, volume: Int, pictureURL: String,
                                howMany: Int) : this(-1, user, brewery, name, style, abv, volume, pictureURL, howMany)

    override fun toString(): String {
        return super.toString()
    }


}
