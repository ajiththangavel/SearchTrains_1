package com.myPackages.searchtrains

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.myPackages.searchtrains.databinding.ActivityLoginBinding

import com.myPackages.searchtrains.databinding.ActivityTrainDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class TrainSearchingActivity : AppCompatActivity() {
    private lateinit var trainNumberEditText: EditText
    private lateinit var trainNameEditText: EditText
    private lateinit var trainListRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchButton: Button
    private lateinit var trainDetails: TextView

    private val apiClient = APIClient.getInstance()
    private val trainListAdapter = TrainListAdapter(ArrayList())
    private lateinit  var binding : ActivityTrainDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        trainNumberEditText = binding.trainNumberEditText
        trainNameEditText = binding.trainNameEditText
        trainListRecyclerView = binding.trainListRecyclerView
        progressBar = binding.progressBar
        searchButton = binding.button2
        trainDetails = binding.trainDetailsTextView
        trainDetails.visibility = View.GONE
        progressBar.visibility = View.GONE

        searchButton.setOnClickListener {
            searchTrain()
            progressBar.visibility = View.VISIBLE
        }

        trainListRecyclerView.layoutManager = LinearLayoutManager(this)
        trainListRecyclerView.adapter = trainListAdapter



    }

    /**
     * Method Which searches Trains List based on the User Input
     * makes the API call to fetch the Data from Server
     */
    private fun searchTrain() {
        val trainNumber = trainNumberEditText.text.toString()
        val trainName = trainNameEditText.text.toString().trim()

        //if User enters Nothing
        if(trainNumber.isEmpty()&& trainName.isEmpty()){
            Toast.makeText(this,"Please enter either Train number or Train Name you want to search", Toast.LENGTH_LONG).show()
        }
        //if User enters only the Train Name
        else if(trainNumber.isEmpty() && trainName.isNotEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = apiClient.search(trainName)
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        trainDetails.visibility = View.VISIBLE
                        Log.d("API_RESPONSE", response)
                        // Process the response here and update the UI
                        updateUI(response)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        // Handle error
                        Log.e("API_ERROR", "Error fetching data: ${e.message}")
                    }
                }
            }
        }
        //If user Enter only Train Number
        else if(trainNumber.isNotEmpty() && trainName.isEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = apiClient.search(trainNumber)
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        trainDetails.visibility = View.VISIBLE
                        Log.d("API_RESPONSE", response)
                        // Process the response here and update the UI
                        updateUI(response)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        // Handle error
                        Log.e("API_ERROR", "Error fetching data: ${e.message}")
                    }
                }
            }
        }
        //if user enter Both TrainName and TrainNumber
        else if(trainNumber.isNotEmpty() && trainName.isNotEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val response = apiClient.search(trainName)
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        trainDetails.visibility = View.VISIBLE
                        Log.d("API_RESPONSE", response)
                        // Process the response here and update the UI
                        updateUI(response)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.GONE
                        // Handle error
                        Log.e("API_ERROR", "Error fetching data: ${e.message}")
                    }
                }
            }
        }

    }

    /**
     * this Function which Updates the TrainListAdapter when new Data has been received After the Search
     */
    private fun updateUI(response: String) {
        // Parse the response and update the RecyclerView with train details
        // For simplicity, let's assume the response is a list of trains
        val trains = parseResponse(response)
        trainListAdapter.updateData(trains)
    }

    /**
     * This function converts The API response Data into String
     * Stores into the User List
     */
    private fun parseResponse(response: String): List<TrainDetails> {
        val trains = mutableListOf<TrainDetails>()

        // Here, we'll parse the JSON string and create Train objects
        try {
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val trainNumber = jsonObject.getInt("train_num")
                val name = jsonObject.getString("name")
                val startingStation = jsonObject.getString("train_from")
                val destination = jsonObject.getString("train_to")

                val dataObject = jsonObject.getJSONObject("data")

                // Extract arrival time and departure time
                val arriveTime = dataObject.getString("arriveTime")
                val departTime = dataObject.getString("departTime")
                val train = TrainDetails(trainNumber, name, startingStation, destination,arriveTime,departTime)


                trains.add(train)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return trains
    }
}