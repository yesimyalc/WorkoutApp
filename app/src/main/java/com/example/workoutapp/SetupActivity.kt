package com.example.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class SetupActivity : AppCompatActivity()
{
    private var exerciseActionBar:androidx.appcompat.widget.Toolbar?=null
    private var rMins:TextView?=null
    private var rSecs:TextView?=null
    private var eMins:TextView?=null
    private var eSecs:TextView?=null

    companion object{
        const val restSecs:String="a"
        const val exSecs:String="b"
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        exerciseActionBar=findViewById(R.id.exerciseActionBar)

        setSupportActionBar(exerciseActionBar)
        val actionBar=supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseActionBar!!.setNavigationOnClickListener { onBackPressed() }

        rMins=findViewById(R.id.rMinutes)
        rSecs=findViewById(R.id.rSeconds)
        eMins=findViewById(R.id.eMinutes)
        eSecs=findViewById(R.id.eSeconds)

        rSecs!!.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (rSecs!!.text.toString().toInt() > 59) {
                    rSecs!!.text = "59"
                }
                else if(rSecs!!.text.length>2 && rSecs!!.text.toString().startsWith("0"))
                    rSecs!!.text=rSecs!!.text.toString().subSequence(1, rSecs!!.text.length)
                else if (rSecs!!.text.isEmpty())
                    rSecs!!.text = "00"
                else if (rSecs!!.text.length == 1)
                    rSecs!!.text = "0" + rSecs!!.text
            }
        }

        rMins!!.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if(rMins!!.text.isEmpty())
                    rMins!!.text="00"
                else if(rMins!!.text.length==1)
                    rMins!!.text="0"+rMins!!.text
                else if(rMins!!.text.length>2 && rMins!!.text.toString().startsWith("0"))
                    rMins!!.text=rMins!!.text.toString().subSequence(1, rMins!!.text.length)
            }
        }

        eSecs!!.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if (eSecs!!.text.toString().toInt() > 59) {
                    eSecs!!.text = "59"
                }
                else if(eSecs!!.text.length>2 && eSecs!!.text.toString().startsWith("0"))
                    eSecs!!.text=eSecs!!.text.toString().subSequence(1, eSecs!!.text.length)
                else if (eSecs!!.text.isEmpty())
                    eSecs!!.text = "00"
                else if (eSecs!!.text.length == 1)
                    eSecs!!.text = "0" + eSecs!!.text
            }
        }

        eMins!!.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if(eMins!!.text.isEmpty())
                    eMins!!.text="00"
                else if(eMins!!.text.length==1)
                    eMins!!.text="0"+eMins!!.text
                else if(eMins!!.text.length>2 && eMins!!.text.toString().startsWith("0"))
                    eMins!!.text=eMins!!.text.toString().subSequence(1, eMins!!.text.length)
            }
        }
    }

    fun onStart(view: View)
    {
        val restTime=rSecs!!.text.toString().toInt()+(rMins!!.text.toString().toInt()*60)
        val exTime=eSecs!!.text.toString().toInt()+(eMins!!.text.toString().toInt()*60)

        val intent= Intent(this, ExerciseActivity::class.java)
        intent.putExtra(exSecs, exTime.toString())
        intent.putExtra(restSecs, restTime.toString())

        startActivity(intent)
    }

    fun onIncrement(view: View)
    {
        if((view as TextView).id==R.id.rPlus)
        {
            var newSecs=rSecs!!.text.toString().toInt()
            newSecs++
            if(newSecs==60)
            {
                newSecs=0
                var newMins=rMins!!.text.toString().toInt()
                newMins++
                if(newMins<10)
                    rMins!!.text= "0$newMins"
                else
                    rMins!!.text=newMins.toString()
            }
            if(newSecs<10)
                rSecs!!.text= "0$newSecs"
            else
                rSecs!!.text=newSecs.toString()
        }
        else
        {
            var newSecs=eSecs!!.text.toString().toInt()
            newSecs++
            if(newSecs==60)
            {
                newSecs=0
                var newMins=eMins!!.text.toString().toInt()
                newMins++

                if(newMins<10)
                    eMins!!.text= "0$newMins"
                else
                    eMins!!.text=newMins.toString()
            }
            if(newSecs<10)
                eSecs!!.text= "0$newSecs"
            else
                eSecs!!.text=newSecs.toString()
        }
    }

    fun onDecrement(view: View)
    {
        if((view as TextView).id==R.id.rMinus)
        {
            var newMins=rMins!!.text.toString().toInt()
            var newSecs=rSecs!!.text.toString().toInt()
            if(rSecs!!.text.toString()=="00" && rMins!!.text.toString()=="00")
                return
            else if(rSecs!!.text.toString()=="00")
            {
                newMins--
                rSecs!!.text="59"

                if(newMins<10)
                    rMins!!.text= "0$newMins"
                else
                    rMins!!.text=newMins.toString()
            }
            else
            {
                newSecs--

                if(newSecs<10)
                    rSecs!!.text= "0$newSecs"
                else
                    rSecs!!.text=newSecs.toString()
            }
        }
        else
        {
            var newMins=eMins!!.text.toString().toInt()
            var newSecs=eSecs!!.text.toString().toInt()
            if(eSecs!!.text.toString()=="00" && eMins!!.text.toString()=="00")
                return
            else if(eSecs!!.text.toString()=="00")
            {
                newMins--
                eSecs!!.text="59"

                if(newMins<10)
                    eMins!!.text= "0$newMins"
                else
                    eMins!!.text=newMins.toString()
            }
            else
            {
                newSecs--

                if(newSecs<10)
                    eSecs!!.text= "0$newSecs"
                else
                    eSecs!!.text=newSecs.toString()
            }
        }
    }
}