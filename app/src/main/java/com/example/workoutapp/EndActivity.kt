package com.example.workoutapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class EndActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        val player = MediaPlayer.create(applicationContext, R.raw.press_start)
        player.isLooping=false
        player.start()
    }

    fun onFinish(view: View)
    {
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}