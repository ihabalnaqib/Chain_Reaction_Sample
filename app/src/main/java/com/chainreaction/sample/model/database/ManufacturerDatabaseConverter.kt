package com.chainreaction.sample.model.database

import androidx.room.TypeConverter
import com.chainreaction.sample.model.model.InventoryModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class ManufacturerDatabaseConverter {

    @TypeConverter
    fun fromManufacturers(manufacturer: InventoryModel.ManufacturerModel?): String? {
        if (manufacturer == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<InventoryModel.ManufacturerModel?>() {}.type
        return gson.toJson(manufacturer, type)
    }

    @TypeConverter
    fun toManufacturers(manufacturerString: String?): InventoryModel.ManufacturerModel? {
        if (manufacturerString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<InventoryModel.ManufacturerModel?>() {}.type
        return gson.fromJson<InventoryModel.ManufacturerModel?>(manufacturerString, type)
    }

    @TypeConverter
    fun fromBytes(manufacturerString: ByteArray?): String? {
        if (manufacturerString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ByteArray?>() {}.type
        return gson.toJson(manufacturerString, type)
    }

    @TypeConverter
    fun toBytes(manufacturerString: String?): ArrayList<ByteArray?>? {
        if (manufacturerString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<InventoryModel.ManufacturerModel?>() {}.type
        return gson.fromJson<ArrayList<ByteArray?>>(manufacturerString, type)
    }
  

}