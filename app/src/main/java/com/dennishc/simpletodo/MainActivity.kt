package com.dennishc.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listOfTasks = mutableListOf<String>()

        // 1. Let's detect when the user clicks on the add button
        // findViewById<Button>(R.id.button).setOnClickListener {
        //     // Code in here is going to be executed when the user clicks on a button
        //     Log.i("Dennis", "User clicked on button")
        // }

        listOfTasks.add("Do laundry")
        listOfTasks.add("Go for a walk")

        // Lookup the RecyclerView in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView

        // Create adapter passing in the sample user data
        val adapter = TaskItemAdapter(listOfTasks)

        // Attach the adapter to the RecyclerView to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)


        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        // Get a reference to the button and then set an onClickLister
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that our adata has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1) // add to the end of the list

            // 3. Reset text field
            inputTextField.setText("")
        }
    }
}