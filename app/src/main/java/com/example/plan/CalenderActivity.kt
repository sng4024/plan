package com.example.plan

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.example.plan.databinding.ActivityCalenderBinding
import java.text.SimpleDateFormat


class CalenderActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalenderBinding
    lateinit var myDAO: MyDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myDAO=MyDatabase.getDatabase(this).getMyDao()

        binding.toolBar.textView.text="습관 달력"
        val toobar:Toolbar=findViewById(R.id.toolBar)
        setSupportActionBar(toobar)
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.calendarView.selectionManager= SingleSelectionManager(OnDaySelectedListener {
            val mFormat=SimpleDateFormat("yyyy/MM/dd")

            startActivity(Intent(this,onedayActivity::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("date", mFormat.format(binding.calendarView.selectedDays.get(0).calendar.time))
        })})


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }
}