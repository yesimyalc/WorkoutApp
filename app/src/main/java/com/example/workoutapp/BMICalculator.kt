package com.example.workoutapp

import android.util.Log
import java.lang.IllegalArgumentException

class BMICalculator(height: Double, weight: Double, type: String)
{
    val height=height
    val weight=weight
    var type=type
        private set
    var bmi:Double?=null
        private set
    var state:String?=null
        private set

    init
    {
        if(height<0 || weight<0)
            throw IllegalArgumentException()

        if(type!="US" && type!="METRIC")
            this.type="METRIC"
    }

    fun calculateBMI()
    {
        if(this.type=="METRIC")
            bmi=(weight/height/height)*10000
        else
            bmi=(weight/height/height)*703

        setState()
    }

    fun setState()
    {
        if(bmi!!<18.5)
            state="Underweight"
        else if(bmi!!>=18.5 && bmi!!<=24.9)
            state="Normal"
        else if(bmi!!>24.9 && bmi!!<=29.9)
            state="Overweight"
        else if(bmi!!>29.9 && bmi!!<=34.9)
            state="Obese"
        else if(bmi!!>34.9)
            state="Extremely Obese"
    }

    fun getBMI():String
    {
        Log.i("MyMessage", "dfs"+bmi)
        return String.format("%.2f", bmi)
    }
}