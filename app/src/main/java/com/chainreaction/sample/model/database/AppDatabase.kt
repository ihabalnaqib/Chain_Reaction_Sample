package com.chainreaction.sample.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chainreaction.sample.model.model.InventoryModel

@TypeConverters(ManufacturerDatabaseConverter::class)
@Database(entities = [InventoryModel::class], version = 1 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun inventoryDao(): InventoryDao

}