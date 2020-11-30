package com.example.plan

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter{
    @TypeConverter
    fun fromString(value: String?): Date? {

        return SimpleDateFormat("yyyy/MM/dd").parse(value)
    }

    @TypeConverter
    fun dateToString(date: Date?): String? {
        return SimpleDateFormat("yyyy/MM/dd").format(date)
    }
}
