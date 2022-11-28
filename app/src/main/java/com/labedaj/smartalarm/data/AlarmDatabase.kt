package com.labedaj.smartalarm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.labedaj.smartalarm.data.models.Alarm

@Database(
    entities = [Alarm::class],
    version = 1,
    exportSchema = true
)
abstract class AlarmDatabase : RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object {
        const val DB_DATABASE: String = "alarm_database"
    }
}