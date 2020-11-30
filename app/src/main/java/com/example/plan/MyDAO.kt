package com.example.plan

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface MyDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit:Habit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDate(date: Habit_Date)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEachday(day:EachDay)

    @Query("SELECT iscomplete FROM EachDay,date_table WHERE hid=:id and did=date_id and ha_date=:date")
    suspend fun iscompleteByIDandDate(id:Int,date:String):Boolean

    @Query("UPDATE EachDay SET iscomplete=:comp WHERE did=:did and hid=:hid")
    suspend fun updateComp(did:Int,hid:Int,comp:Boolean)

    @Query("SELECT date_id FROM date_table WHERE ha_date=:date")
    suspend fun getDateIdByDate(date:String):Int

    @Query("SELECT habit_id FROM habit_table WHERE habit=:habit")
    suspend fun getHabitIdByHabit(habit:String):Int

    @Query("SELECT * FROM habit_table,EachDay,Date_table WHERE hid=habit_id and did=date_id and ha_date=:date")
    fun getHabitByDate(date:String): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table,EachDay,Date_table WHERE hid=habit_id and did=date_id and ha_date=:date and iscomplete=1" )
    fun getCompleteHabitByDate(date:String): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table,EachDay,Date_table WHERE hid=habit_id and did=date_id and ha_date=:date and iscomplete=0" )
    fun getInCompleteHabitByDate(date:String): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table,EachDay,Date_table WHERE hid=habit_id and did=date_id and ha_date=:date and iscomplete=0" )
    suspend fun getInCompleteHabitListByDate(date:String): List<Habit>

    @Query("SELECT habit FROM habit_table WHERE habit_id=:id")
    suspend fun getHabitByID(id:Int):String

    @Query("SELECT COUNT(*) FROM habit_table,EachDay,Date_table WHERE hid=habit_id and did=date_id and ha_date=:date")
    suspend fun countHabitByDate(date:String):Int

    @Query("SELECT COUNT(*) FROM habit_table,EachDay,Date_table WHERE hid=habit_id and did=date_id and ha_date=:date and iscomplete=1")
    suspend fun countCompHabitByDate(date:String):Int

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Delete
    suspend fun deleteEachday(eachDay: EachDay)



}