package com.example.plan

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.plan.databinding.ActivityAddBinding
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class AddHabitActivity:AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var myDao:MyDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDao=MyDatabase.getDatabase(this).getMyDao()

        val mFormat= SimpleDateFormat("yyyy/MM/dd")

       binding.toolbar.textView.text="습관 추가"
        val toolbar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var time:String
        if(intent.hasExtra("date")){
            time=intent.getStringExtra("date").toString()

        }else time=SimpleDateFormat("yyyy/MM/dd").format(Date(System.currentTimeMillis()))

        binding.addHabit.setOnClickListener {
            val habit=binding.editTextTextPersonName.text.toString()
            runBlocking {
                myDao.insertHabit(Habit(habit = habit))
                if(myDao.getDateIdByDate(time)==null) {
                    myDao.insertDate(Habit_Date(ha_date = time))
                    myDao.insertEachday(EachDay(myDao.getHabitIdByHabit(habit),myDao.getDateIdByDate(time)))
                }
                else
                    myDao.insertEachday(EachDay(myDao.getHabitIdByHabit(habit),myDao.getDateIdByDate(time)))

            }
            finish()
        }
        binding.cancleHabit.setOnClickListener {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
          android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }
}