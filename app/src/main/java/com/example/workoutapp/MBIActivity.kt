package com.example.workoutapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
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

    fun isValid():Boolean
    {
        if(metricView?.visibility==View.VISIBLE && (heightTextM!!.text.isEmpty() || weightTextM!!.text.isEmpty()))
            return false
        else if(usView?.visibility==View.VISIBLE && (heightInFEET!!.text.isEmpty() || heightInINCH!!.text.isEmpty() || weightInLBS!!.text.isEmpty()))
            return false

        return true
    }

    fun onCalculate(view: View)
    {
        if(isValid())
        {
            val bmiValue = findViewById<TextView>(R.id.bmiValue)
            val bmiType = findViewById<TextView>(R.id.bmiType)
            var calculator: BMICalculator? = null

            if (metricView?.visibility == View.VISIBLE)
            {
                calculator = BMICalculator(heightTextM!!.text.toString().toDouble(), weightTextM!!.text.toString().toDouble(), "METRIC")
            }
            else
            {
                var heightValue = (heightInINCH!!.text.toString().toDouble()) + (heightInFEET!!.text.toString().toDouble()) * 12
                calculator = BMICalculator(heightValue, weightInLBS!!.text.toString().toDouble(), "US")
            }
            calculator.calculateBMI()
            bmiValue!!.text = calculator.getBMI()
            bmiType!!.text = calculator.state
            bmiResult!!.visibility = View.VISIBLE
        }
    }
}