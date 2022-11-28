package com.labedaj.smartalarm.data

import com.labedaj.smartalarm.data.models.Alarm
import javax.inject.Inject

class AlarmRepository @Inject constructor(
    private val dao: AlarmDao
) {

    fun getAllAlarms() = dao.getAlarms()

    fun getAllAlarmsById(id: Long) = dao.getAlarm(id)

    suspend fun insert(alarm: Alarm): Long {
        return dao.insert(alarm)
    }

    suspend fun update(alarm: Alarm) {
        dao.update(alarm)
    }

}