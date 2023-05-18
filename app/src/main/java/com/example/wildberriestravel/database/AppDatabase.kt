package com.example.wildberriestravel.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wildberriestravel.dao.FlightDao
import com.example.wildberriestravel.entity.FlightEntity

@Database(entities = [FlightEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun flightDao(): FlightDao

}