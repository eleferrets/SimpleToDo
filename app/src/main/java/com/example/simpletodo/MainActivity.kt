package com.example.simpletodo

import android.os.Bundle
import org.apache.commons.io.FileUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks = mutableListOf<String>()
    // Init this value later
    lateinit var adapter: TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object: TaskItemAdapter.onLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // Remove the item from the list
                listOfTasks.removeAt(position)
                // Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()
                saveItems()

            }
        }

        loadItems()
// Detect when user clicks add button
//        findViewById<Button>(R.id.button) {
//            // When the user clicks the button
//            Log.i("Caran", "User clicked a button")
//        }

        // Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the button and input field so the user can enter a task and add it to the list
        // Access all further calls using this selector
       val inputTextField = findViewById<EditText>(R.id.addTaskField)
        // Get a reference to the button
        // set an onclickListener
        findViewById<Button>(R.id.button).setOnClickListener {
// Grab the text the user has inputted into @id/addTaskField
            // .text.toString turns the editable into an actual string
            val userInputtedTask = inputTextField.text.toString()

            // Add the string to out list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)
            // Update code - Notify adapter that data has changed
            adapter.notifyItemInserted(listOfTasks.size-1)

        // Reset text field - user experience
            inputTextField.setText("")

        saveItems()
        }
    }
    // Save the data the user has inputted
    // Save data by writing/reading from a file

    // Create a metohd to get the file we need
    fun getDataFile() : File {
        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file
    fun loadItems () {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // Save items by writing them into our data file
    fun saveItems () {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        }
        catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}