package com.chainreaction.sample.model.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class InventoryModel(

    @PrimaryKey(autoGenerate = true)
    var idPk: Int? = null,
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("releaseDate")
    val releaseDate: String? = "",
    @SerializedName("manufacturer")
    val manufacturerList: ManufacturerModel? = ManufacturerModel()

) : Parcelable {
    @Parcelize
    data class ManufacturerModel(
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("homePage")
        val homePage: String? = "",
        @SerializedName("phone")
        val phone: String? = ""
    ): Parcelable {
        constructor() : this(
            null
        )
    }
}