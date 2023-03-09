package com.chainreaction.sample.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chainreaction.sample.model.model.InventoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addInventories(inventories: List<InventoryModel>)

    @Query("select * from inventorymodel")
    fun getInventories(): Flow<List<InventoryModel>?>

}