package com.example.plan

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.plan.databinding.ActivitySplashBinding
import java.lang.Exception

class SplashActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ani=AnimationUtils.loadAnimation(this,R.anim.fadein)
        ani.duration=2000
        binding.firTitle.startAnimation(ani)
       val mHandler=Handler().postDelayed({
           startActivity(Intent(this,MainActivity::class.java).apply {
               setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
           })
           finish()
       },3000)

    }
}