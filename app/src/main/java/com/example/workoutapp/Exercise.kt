package com.example.workoutapp

class Exercise constructor(id: Int, name: String, image: Int, isSelected: Boolean, isCompleted:Boolean)
{
    val id=id
    val name=name
    val image=image
    var isSelected=isSelected
        private set
    var isCompleted=isCompleted
        private set

    fun complete(){
        isCompleted=true
        deselect()
    }
    fun select(){isSelected=true}
    fun deselect(){isSelected=false}

    fun resetInfo()
    {
        isCompleted=false
        isSelected=false
    }
}
