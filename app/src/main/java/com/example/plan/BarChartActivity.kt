package com.example.plan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.size
import com.example.plan.databinding.ActivityChartBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class BarChartActivity : AppCompatActivity() {
    lateinit var binding: ActivityChartBinding
    lateinit var myDao:MyDAO
    lateinit var chart: BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)


        myDao=MyDatabase.getDatabase(this).getMyDao()
binding.toolbar.textView.text="습관 통계"
        val toolbar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var now =Calendar.getInstance()
        now.add(Calendar.DAY_OF_YEAR,-29)
        val mFormat=SimpleDateFormat("yyyy/MM/dd")
        var time =mFormat.format(now.time)

        chart=binding.barChart

//        chart.setOnChartValueSelectedListener()

        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.description.isEnabled=false
        chart.setMaxVisibleValueCount(10)
        chart.setPinchZoom(false)
        chart.setDrawGridBackground(false)

       // chart.setVisibleYRange(0F,1F,YAxis.AxisDependency.LEFT)
        var entries:MutableList<BarEntry>
        entries= mutableListOf()
        var i:Float= 0F
        var total:Float=0F
        var count1:Float
        var count2:Float
        var result:Float
        var count3:Float=0F
        while(i<30) {
            runBlocking {
                count2 = myDao.countCompHabitByDate(time).toFloat()
                count1 = myDao.countHabitByDate(time).toFloat()

                    if(count1==0F || count2==0F) result=0F
                else{ result = (count2 / count1)
                count3++
                }
                entries.add(BarEntry(i, result))
                now.add(Calendar.DAY_OF_YEAR,1)
                time=mFormat.format(now.time)
                total+=result
                i+=1
            }
        }
        var bardataSet=BarDataSet(entries,"habits")
        var barData=BarData(bardataSet)

        chart.data=barData
        chart.setVisibleYRange(0F,1.2F,YAxis.AxisDependency.LEFT)
        chart.setFitBars(false)
        chart.animateXY(1000,1000)
        chart.invalidate()
        var totalper=total/count3*100
        binding.progressBar.max=100
        binding.progressBar.setProgress(totalper.toInt(),true)
        binding.textView2.text="성취도 "+Math.round(totalper*10)/10F+"%"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->finish()
        }
        return super.onOptionsItemSelected(item)
    }
}