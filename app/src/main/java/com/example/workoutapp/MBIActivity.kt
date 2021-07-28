package com.example.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import org.w3c.dom.Text

class MBIActivity : AppCompatActivity()
{
    private var metricView:LinearLayout?=null
    private var usView:LinearLayout?=null
    private var weightTextM:EditText?=null
    private var heightTextM:EditText?=null
    private var weightInLBS:EditText?=null
    private var heightInINCH:EditText?=null
    private var heightInFEET:EditText?=null
    private var bmiResult:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mbiactivity)

        metricView=findViewById(R.id.metricView)
        usView=findViewById(R.id.usView)
        weightTextM=findViewById(R.id.weightTextM)
        heightTextM=findViewById(R.id.heightTextM)
        weightInLBS=findViewById(R.id.weightInLBS)
        heightInINCH=findViewById(R.id.heightInINCH)
        heightInFEET=findViewById(R.id.heightInFEET)
        bmiResult=findViewById(R.id.bmiResult)

        val exerciseActionBar=findViewById<androidx.appcompat.widget.Toolbar>(R.id.exerciseActionBar)
        setSupportActionBar(exerciseActionBar)
        exerciseActionBar!!.setNavigationOnClickListener { onBackPressed() }
        val actionBar=supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun onMetrics(view: View)
    {
        metricView!!.visibility=View.VISIBLE
        usView!!.visibility=View.GONE

        weightTextM!!.text.clear()
        heightTextM!!.text.clear()

        bmiResult!!.visibility=View.GONE
    }

    fun onUS(view: View)
    {
        usView!!.visibility=View.VISIBLE
        metricView!!.visibility=View.GONE

        weightInLBS!!.text.clear()
        heightInINCH!!.text.clear()
        heightInFEET!!.text.clear()

        bmiResult!!.visibility=View.GONE
    }

    fun onCalculate(view: View)
    {
        val bmiValue=findViewById<TextView>(R.id.bmiValue)
        val bmiType=findViewById<TextView>(R.id.bmiType)

        if(metricView?.visibility==View.VISIBLE)
        {
            val calculator=BMICalculator(heightTextM!!.text.toString().toDouble() ,weightTextM!!.text.toString().toDouble(), "METRIC")
            calculator.calculateBMI()
            bmiValue!!.text=calculator.getBMI().toString()
            bmiType!!.text=calculator.state
        }
        else
        {

        }
        bmiResult!!.visibility=View.VISIBLE
    }
}