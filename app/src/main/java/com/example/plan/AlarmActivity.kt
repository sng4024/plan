package com.example.plan

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.plan.databinding.ActivityAlarmBinding
import java.text.SimpleDateFormat
import java.util.*


class AlarmActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlarmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.textView.text="알람"
        val toolbar:Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title=""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.timePicker.setIs24HourView(true)

        val sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE)
        val millis =
            sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis())

        val nextNotifyTime: Calendar = GregorianCalendar()
        nextNotifyTime.setTimeInMillis(millis)

        val nextDate: Date = nextNotifyTime.getTime()
        val date_text: String =
            SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate)
        Toast.makeText(
            applicationContext,
            "[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!",
            Toast.LENGTH_SHORT
        ).show()

        val currentTime = nextNotifyTime.time
        val HourFormat = SimpleDateFormat("kk", Locale.getDefault())
        val MinuteFormat = SimpleDateFormat("mm", Locale.getDefault())

        val pre_hour = HourFormat.format(currentTime).toInt()
        val pre_minute = MinuteFormat.format(currentTime).toInt()


        if (Build.VERSION.SDK_INT >= 23) {
            binding.timePicker.setHour(pre_hour)
            binding.timePicker.setMinute(pre_minute)
        } else {
            binding.timePicker.setCurrentHour(pre_hour)
            binding.timePicker.setCurrentMinute(pre_minute)
        }
        binding.alarmBtn.setOnClickListener {
            var hour:Int
            var hour_24:Int
            var minute:Int
            var am_pm:String
                hour_24=binding.timePicker.hour
                minute=binding.timePicker.minute
            if(hour_24>12){
                am_pm="PM"
                hour=hour_24-12
            }else{
                hour=hour_24
                am_pm="AM"
            }
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar[Calendar.HOUR_OF_DAY] = hour_24
            calendar[Calendar.MINUTE] = minute
            calendar[Calendar.SECOND] = 0
            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1);
            }
            val currentDateTime = calendar.time
            val date_text =
                SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(
                    currentDateTime
                )
            Toast.makeText(applicationContext, date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT)
                .show()
            val editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit()
            editor.putLong("nextNotifyTime", calendar.timeInMillis)
            editor.apply()


            diaryNotification(calendar)

        }

    }
   fun diaryNotification(calendar: Calendar){
       val dailyNotify = true
       val pm = this.packageManager
       val receiver = ComponentName(this, DeviceBootReceiver::class.java)
       val alarmIntent = Intent(this, AlarmReceiver::class.java)
       val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)
       val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
       if (alarmManager != null) {

           alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
               AlarmManager.INTERVAL_DAY, pendingIntent);

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               alarmManager.setExactAndAllowWhileIdle(
                   AlarmManager.RTC_WAKEUP,
                   calendar.getTimeInMillis(),
                   pendingIntent
               );
           }}
       pm.setComponentEnabledSetting(receiver,
           PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
           PackageManager.DONT_KILL_APP);
   }
}