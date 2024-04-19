package com.myPackages.searchtrains

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrainListAdapter (private val trainList: ArrayList<TrainDetails>) : RecyclerView.Adapter<TrainListAdapter.TrainViewHolder>() {
    class TrainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trainNumberTextView: TextView = itemView.findViewById(R.id.trainNumberTextView)
        private val trainNameTextView: TextView = itemView.findViewById(R.id.trainNameTextView)
        private val stationFromTextView: TextView = itemView.findViewById(R.id.stationFromTextView)
        private val stationToTextView: TextView = itemView.findViewById(R.id.stationToTextView)
        private val arrivalTimeTextView: TextView = itemView.findViewById(R.id.ArrivalTimeTextView)
        private val departureTimeTextView: TextView = itemView.findViewById(R.id.DepTimeTextView)

        fun bind(trainDetails: TrainDetails) {
            trainNumberTextView.text = "${trainDetails.train_num}"
            trainNameTextView.text = "${trainDetails.name}"
            stationFromTextView.text = "${trainDetails.train_from}"
            stationToTextView.text = "${trainDetails.train_to}"
            arrivalTimeTextView.text = "${trainDetails.arriveTime}"
            departureTimeTextView.text = "${trainDetails.departTime}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.train_list_view, parent, false)
        return TrainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trainList.size
    }

    override fun onBindViewHolder(holder: TrainViewHolder, position: Int) {
        val currentTrainDetails = trainList[position]
        holder.bind(currentTrainDetails)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(trainList: List<TrainDetails>) {
        this.trainList.clear()
        this.trainList.addAll(trainList)
        notifyDataSetChanged()
    }
}