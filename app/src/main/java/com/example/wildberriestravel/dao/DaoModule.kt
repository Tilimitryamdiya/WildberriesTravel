package com.example.wildberriestravel.dao

import com.example.wildberriestravel.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DaoModule {

    @Provides
    fun provideFlightDao(db: AppDatabase): FlightDao = db.flightDao()

}