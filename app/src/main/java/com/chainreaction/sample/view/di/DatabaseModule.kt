package com.chainreaction.sample.view.di

import androidx.room.Room
import com.chainreaction.sample.model.database.AppDatabase
import com.chainreaction.sample.model.database.InventoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(baseApp: AppApplication): AppDatabase =
        Room.databaseBuilder(baseApp, AppDatabase::class.java, "country_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): InventoryDao = database.inventoryDao()


}