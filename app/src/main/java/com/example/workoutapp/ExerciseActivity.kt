package com.example.workoutapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener
{
    private var restTimer: CountDownTimer?=null
    private var restProgress=0
    private var exerciseTimer: CountDownTimer?=null
    private var exerciseProgress=0
    private var currentExercise=0

    private var progressBar:ProgressBar?=null
    private var llRestView:LinearLayout?=null
    private var llExerciseView:LinearLayout?=null
    private var progressBar2:ProgressBar?=null
    private var upcomingExercise: TextView?=null
    private var llPauseView:LinearLayout?=null

    private var exercises:ArrayList<Exercise>?=null
    private var player: MediaPlayer?=null
    private var tts:TextToSpeech?=null
    private var exerciseAdapter:ExerciseStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val exerciseActionBar=findViewById<androidx.appcompat.widget.Toolbar>(R.id.exerciseActionBar)

        progressBar=findViewById(R.id.progressBar)
        llRestView=findViewById(R.id.llRestView)
        llExerciseView=findViewById(R.id.llExerciseView)
        progressBar2=findViewById(R.id.progressBar2)
        exercises=Exercises.defaultExerciseList()
        upcomingExercise=findViewById(R.id.upcomingExercise)
        llPauseView=findViewById(R.id.llPauseView)

        progressBar?.max=intent.getStringExtra(SetupActivity.restSecs).toString().toInt()
        progressBar2?.max=intent.getStringExtra(SetupActivity.exSecs).toString().toInt()

        tts= TextToSpeech(this, this)

        setupAdapter()

        setSupportActionBar(exerciseActionBar)
        exerciseActionBar!!.setNavigationOnClickListener { onBackPressed() }
        val actionBar=supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        setupRestView()
    }

    private fun setupAdapter()
    {
        val totalProgressBar=findViewById<RecyclerView>(R.id.totalProgressBar)
        totalProgressBar.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter= ExerciseStatusAdapter(exercises!!, this)
        totalProgressBar.adapter=exerciseAdapter
    }

    private fun setupRestView()
    {
        player?.release()
        player= MediaPlayer.create(applicationContext, R.raw.press_start)
        player!!.isLooping=false
        player!!.start()

        llRestView!!.visibility= View.VISIBLE
        llExerciseView!!.visibility=View.INVISIBLE
        upcomingExercise!!.text= exercises!![0].name
        if(restTimer!=null)
        {
            restTimer!!.cancel()
            restProgress=0
        }
        setRestProgressBar()
    }

    private fun setRestProgressBar()
    {
        val timer=findViewById<TextView>(R.id.timer)
        val totalTime=progressBar?.max!!
        progressBar?.progress=totalTime

        restTimer= object: CountDownTimer(((totalTime-restProgress)*1000).toLong(), 1000)
        {
            override fun onTick(millisUntilFinished: Long)
            {
                timer!!.text = (totalTime-restProgress).toString()
                restProgress++
                progressBar!!.progress = totalTime - restProgress

                if(timer.text.toString().toInt()<4)
                {
                    player!!.release()
                    player=MediaPlayer.create(applicationContext, R.raw.tick)
                    player!!.isLooping=false
                    player!!.start()
                }

            }

            override fun onFinish()
            {
                setUpExerciseView()
                player!!.release()
                player=MediaPlayer.create(applicationContext, R.raw.finish)
                player!!.isLooping=false
                player!!.start()
            }
        }.start()

        upcomingExercise!!.text= exercises!![currentExercise].name
    }

    fun setUpExerciseView()
    {
        val exerciseImage=findViewById<ImageView>(R.id.exerciseImage)
        val exerciseName=findViewById<TextView>(R.id.exerciseName)

        llRestView!!.visibility= View.INVISIBLE
        llExerciseView!!.visibility=View.VISIBLE
        if(exerciseTimer!=null)
        {
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        exercises!![currentExercise].select()
        exerciseAdapter!!.notifyDataSetChanged()
        exerciseImage!!.setBackgroundResource(exercises!![currentExercise].image)
        exerciseName!!.text= exercises!![currentExercise].name
        speakOut(exerciseName.text.toString())

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar()
    {
        val timer2=findViewById<TextView>(R.id.timer2)
        val totalTime=progressBar2?.max!!
        progressBar2?.progress=exerciseProgress

        exerciseTimer=object:CountDownTimer(((totalTime-exerciseProgress)*1000).toLong(), 1000)
        {
            override fun onTick(millisUntilFinished: Long)
            {
                timer2!!.text = (totalTime-exerciseProgress).toString()
                exerciseProgress++
                progressBar2!!.progress = totalTime - exerciseProgress

                if(timer2.text.toString().toInt()<4)
                {
                    player!!.release()
                    player=MediaPlayer.create(applicationContext, R.raw.tick)
                    player!!.isLooping=false
                    player!!.start()
                }
             }

            override fun onFinish()
            {
                exerciseTimer!!.cancel()
                exerciseProgress=0
                timer2!!.text=totalTime.toString()

                if(currentExercise<exercises!!.size-1) {
                    currentExercise++
                    exercises!![currentExercise-1].complete()
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }
                else
                {
                    finishExercise()
                }
            }
        }.start()
    }

    fun finishExercise()
    {
        val intent= Intent(this, EndActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onPause(view: View)
    {
        llPauseView?.visibility=View.VISIBLE
        if(llRestView?.visibility==View.VISIBLE) {
            restTimer?.cancel()
            llRestView?.alpha=0.2.toFloat()
        }
        else {
            exerciseTimer!!.cancel()
            llExerciseView?.alpha=0.2.toFloat()
        }
    }

    fun onContinue(view: View)
    {
        llPauseView?.visibility=View.INVISIBLE
        if(llRestView?.visibility==View.VISIBLE) {
            setRestProgressBar()
            llRestView?.alpha=1.toFloat()
        }
        else {
            setExerciseProgressBar()
            llExerciseView?.alpha=1.toFloat()
        }
    }

    public override fun onDestroy()
    {
        if(restTimer!=null)
        {
            restTimer!!.cancel()
            restProgress=0
        }

        if(exerciseTimer!=null)
        {
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }

        if(player!=null)
            player!!.stop()

        if(tts!=null)
        {
            tts!!.stop()
            tts!!.shutdown()
        }

        super.onDestroy()
    }

    override fun onInit(status: Int)
    {
        if(status==TextToSpeech.SUCCESS)
        {
            val result=tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("MyMessage", "Lang not supported.")
            }
        }
        else
        {
            Log.e("MyMessage", "Init failed")
        }
    }

    private fun speakOut(text: String)
    {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}