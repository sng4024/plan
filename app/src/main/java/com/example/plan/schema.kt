package com.example.plan

import androidx.room.*
import org.jetbrains.annotations.NotNull
import java.util.*
import java.util.Date

@Entity(tableName="habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = true)val habit_id:Int=0,
    val habit:String
)
@Entity(tableName="EachDay",
    primaryKeys = ["hid","did"],
    foreignKeys = [
        ForeignKey(entity =Habit::class,parentColumns = ["habit_id"],childColumns = ["hid"] ),
        ForeignKey(entity =Habit_Date::class,parentColumns = ["date_id"],childColumns = ["did"] )
    ]
)
data class EachDay(
    val hid:Int,
    val did:Int,
    var iscomplete:Boolean=false
)
@Entity(tableName = "Date_table")
data class Habit_Date(
    @PrimaryKey(autoGenerate = true)val date_id:Int=0,
    val ha_date:String

)
