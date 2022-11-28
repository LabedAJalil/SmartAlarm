package com.labedaj.smartalarm.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.labedaj.smartalarm.data.models.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {
    @Insert
    suspend fun insert(alarm: Alarm): Long

    @Delete
    suspend fun delete(alarm: Alarm)

    @Update
    suspend fun update(alarm: Alarm)

    @Query("SELECT * FROM alarms")
    fun getAlarms(): Flow<List<Alarm>>

    @Query("SELECT*FROM alarms WHERE id=:id LIMIT 1")
    fun getAlarm(id: Long): Alarm?
}