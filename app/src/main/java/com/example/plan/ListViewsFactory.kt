package com.example.plan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
const val EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM"

class ListViewsFactory(context: Context) :RemoteViewsService.RemoteViewsFactory {
    val context:Context=context
    var myDAO=MyDatabase.getDatabase(context).getMyDao()
    lateinit var list:List<Habit>
    fun setData(){
        var time= SimpleDateFormat("yyyy/MM/dd").format(Date(System.currentTimeMillis()))
        runBlocking {
            list = myDAO.getInCompleteHabitListByDate(time)
        }


    }
    override fun onCreate() {
        setData()
    }

    override fun onDataSetChanged() {
        setData()
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int {
       return list.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val listviewWidget=RemoteViews(context.packageName,R.layout.item)
        listviewWidget.setTextViewText(R.id.habit_name,list.get(position).habit)
  //      val extras=Bundle()
//        extras.putInt(EXTRA_ITEM,position)
        var intent=Intent()
        intent.putExtra(EXTRA_ITEM,position)
        //intent.putExtra("item_data",list.get(position).habit)
        listviewWidget.setOnClickFillInIntent(R.id.habit_name,intent)

        return listviewWidget
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName,R.layout.new_app_widget)
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
       return false
    }
}