package com.example.workoutapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ExerciseStatusAdapter(val items: ArrayList<Exercise>, val context: Context): RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>()
{
    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val exerciseItem=view.findViewById<TextView>(R.id.exerciseItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.exercises_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val exercise:Exercise=items[position]

        holder.exerciseItem.text=exercise.id.toString()
        if(exercise.isSelected)
        {
            holder.exerciseItem.background=ContextCompat.getDrawable(context, R.drawable.exercise_item_bg_selected)
        }
        else if(exercise.isCompleted)
        {
            holder.exerciseItem.background=ContextCompat.getDrawable(context, R.drawable.exercise_item_bg_completed)
        }
    }

    override fun getItemCount(): Int
    {
        return items.size
    }


}