package com.labedaj.smartalarm.data.models

import androidx.room.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


@Entity(tableName = "alarms")
@TypeConverters(StringArrayConverter::class)
data class Alarm(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val sound: Int,
    val vibration: Boolean,
    val volume: Float,
    @ColumnInfo(name = "repeat_days") val repeatDays: List<String>,
    val min: Int,
    val hour: Int,
    @ColumnInfo(name = "is_am") val isAM: Boolean,
    val disabled: Boolean
) {
    fun formattedTime(): String {
        return hour.toString().padStart(2, '0') + ":" + min.toString().padStart(2, '0')
    }

    fun formattedRepeatDays(): String {
        if (repeatDays.isEmpty()) {
            return "No repeats"
        }
        return repeatDays.joinToString(", ")
    }
}

val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

object StringArrayConverter {
    @ExperimentalStdlibApi
    @TypeConverter
    fun fromString(daysString: String): List<String> {
        val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter()
        return requireNotNull(jsonAdapter.fromJson(daysString))
    }

    @ExperimentalStdlibApi
    @TypeConverter
    fun toString(list: List<String>): String {
        val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter()
        return jsonAdapter.toJson(list)
    }
}
