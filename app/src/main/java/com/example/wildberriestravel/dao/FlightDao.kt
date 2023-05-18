package com.example.wildberriestravel.dao

import androidx.room.*
import com.example.wildberriestravel.entity.FlightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {

    @Query("SELECT * FROM FlightEntity")
    fun getAll(): Flow<List<FlightEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(flights: List<FlightEntity>)

    @Query(
        """
           UPDATE FlightEntity SET
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE searchToken = :searchToken;
        """
    )
    suspend fun likeById(searchToken: String)

    @Query("DELETE FROM FlightEntity")
    suspend fun removeAll()
}