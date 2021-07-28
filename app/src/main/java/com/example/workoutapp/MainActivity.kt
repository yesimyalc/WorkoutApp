package com.example.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onStartButton(view: View)
    {
        val intent= Intent(this, SetupActivity::class.java)
        startActivity(intent)
    }

    fun onButtonOne(view: View)
    {
        val intent= Intent(this, MBIActivity::class.java)
        startActivity(intent)
    }

    fun onButtonTwo(view: View)
    {

    }
}