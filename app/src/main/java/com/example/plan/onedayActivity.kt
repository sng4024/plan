package com.example.plan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plan.databinding.ActivityOnedayBinding
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class onedayActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnedayBinding
    lateinit var myDao:MyDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOnedayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.textView.text="습관"
        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recyclerMain.layoutManager= LinearLayoutManager(this)
        binding.recyclerSub.layoutManager= LinearLayoutManager(this)

        myDao=MyDatabase.getDatabase(this).getMyDao()

        var timeex:String
        if(intent.hasExtra("date")){
            timeex=intent.getStringExtra("date").toString()

        }else timeex=SimpleDateFormat("yyyy/MM/dd").format(LocalDate.now())


       showCalendar(timeex)

        binding.arrow1.setOnClickListener {
            var pretime =Calendar.getInstance()
            pretime.time=SimpleDateFormat("yyyy/MM/dd").parse(timeex)
            pretime.add(Calendar.DAY_OF_YEAR,-1)
            timeex=SimpleDateFormat("yyyy/MM/dd").format(pretime.time)
            showCalendar(timeex)
        }

        binding.arrow2.setOnClickListener {
            var pretime =Calendar.getInstance()
            pretime.time=SimpleDateFormat("yyyy/MM/dd").parse(timeex)
            pretime.add(Calendar.DAY_OF_YEAR,1)
            timeex=SimpleDateFormat("yyyy/MM/dd").format(pretime.time)
            showCalendar(timeex)
        }


    }
    private fun showCalendar(time:String){
        binding.today.text=time
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

        var allInCompHabits:LiveData<List<Habit>>
        allInCompHabits=myDao.getInCompleteHabitByDate(time)
        allInCompHabits.observe(this){
            var myadapter= MyAdapter(this, emptyList())
            myadapter?.setOnItemClickListener(OnClick())
            binding.recyclerMain.adapter=myadapter
            myadapter.updateList(it)
        }

        var allCompHabits:LiveData<List<Habit>>
        allCompHabits=myDao.getCompleteHabitByDate(time)
        allCompHabits.observe(this){
            var myadapter= MyCompAdapter(this, emptyList())
            myadapter?.setOnItemClickListener(OnClick2())
            binding.recyclerSub.adapter=myadapter
            myadapter.updateList(it)
        }

        binding.floatingActionButton3.setOnClickListener{
            startActivity(Intent(this,AddHabitActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
                putExtra("date",time)
            })
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }
}