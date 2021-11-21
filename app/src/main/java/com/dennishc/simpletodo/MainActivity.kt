package com.dennishc.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    // val = variables you are never going to change again
    var listOfTasks = mutableListOf<String>()

    // lateinit Kotlin we will eventually assign something to the variable later
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create a variable that implements OnLongClickListener
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. Remove the item from the list
                listOfTasks.removeAt(position)
                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                // saveItems() so changes are persisted into the file
                saveItems()
            }

        }

        // 1. Let's detect when the user clicks on the add button
        // findViewById<Button>(R.id.button).setOnClickListener {
        //     // Code in here is going to be executed when the user clicks on a button
        //     Log.i("Dennis", "User clicked on button")
        // }

        // listOfTasks.add("Do laundry")
        // listOfTasks.add("Go for a walk")

        loadItems()

        // Lookup the RecyclerView in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView

        // Create adapter passing in the sample user data (pass in onLongClickListener event)
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

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

            // Save to file
            saveItems()
        }
    }

    // Save the data that the user has inputted
    // Save data by writing and reading from a file


    // Create a method to get the file we need (have to import external library)
    // Returns a file from Android phone
    fun getDataFile() : File {

        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into our data file
    fun saveItems() {
        // Surround w/ try and catch in case of something going wrong
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }


}