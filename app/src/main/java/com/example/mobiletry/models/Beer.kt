package com.example.mobiletry.models;

import java.io.Serializable

data class Beer(
    val id: Int,
    val user: String,
    val brewery: String,
    val name: String,
    val style: String,
    val abv: Double,
    val volume: Double,
    val howMany: Int)
    :Serializable { constructor(user: String, brewery: String, name: String,
                                style: String, abv: Double, volume: Double,
                                howMany: Int) : this(-1, user, brewery, name, style, abv, volume, howMany)

    override fun toString(): String {
        return super.toString()
    }

}
