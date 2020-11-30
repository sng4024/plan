package com.example.plan

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan.databinding.ActivityMainBinding
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myDao: MyDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.textView.text="습관"
        val toobar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toobar)
        supportActionBar?.title=""

        val appWidgetManager=AppWidgetManager.getInstance(this)
        var appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(this,NewAppWidget::class.java))
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.listview)

        val mFormat=SimpleDateFormat("yyyy/MM/dd")
        var time=mFormat.format(Date(System.currentTimeMillis()))
        var i:Int=0
        var now=Calendar.getInstance()
        myDao = MyDatabase.getDatabase(this).getMyDao()

        time=mFormat.format(Date(System.currentTimeMillis()))
        binding.today.text=time
        binding.recyclerMain.layoutManager=LinearLayoutManager(this)
        binding.recyclerSub.layoutManager=LinearLayoutManager(this)
        class OnClick():MyAdapter.OnItemClickListener{
            override fun onClick(habit_id: Int) {
                runBlocking {
                    val did=myDao.getDateIdByDate(time)
                    if(myDao.iscompleteByIDandDate(habit_id,time))
                        myDao.updateComp(did=did,hid=habit_id,comp = false)
                    else
                        myDao.updateComp(did=did,hid=habit_id,comp = true)
                }
            }
        }
        class OnClick2():MyCompAdapter.OnItemClickListener{
            override fun onClick(habit_id: Int) {
                runBlocking {
                    val did=myDao.getDateIdByDate(time)
                    if(myDao.iscompleteByIDandDate(habit_id,time))
                        myDao.updateComp(did=did,hid=habit_id,comp = false)
                    else
                        myDao.updateComp(did=did,hid=habit_id,comp = true)
                }
            }
        }
         //   myadapter?.setOnItemClickListener{}

        val allInCompHabits=myDao.getInCompleteHabitByDate(time);
        allInCompHabits.observe(this){
            val myadapter= MyAdapter(this, emptyList())
            myadapter?.setOnItemClickListener(OnClick())
            binding.recyclerMain.adapter=myadapter
            myadapter.updateList(it)
        }
        val allCompHabits=myDao.getCompleteHabitByDate(time);
        allCompHabits.observe(this){
            val myadapter= MyCompAdapter(this, emptyList())
            myadapter?.setOnItemClickListener(OnClick2())
            binding.recyclerSub.adapter=myadapter
            myadapter.updateList(it)
        }
        binding.floatingActionButton3.setOnClickListener{
            startActivity(Intent(this,AddHabitActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_alarm->startActivity(Intent(this,AlarmActivity::class.java).apply {
                setFlags((Intent.FLAG_ACTIVITY_NEW_TASK))
            })
            R.id.menu_cal->startActivity(Intent(this,CalenderActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
            R.id.menu_chart->startActivity(Intent(this,BarChartActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })

        }
        return super.onOptionsItemSelected(item)
    }
}