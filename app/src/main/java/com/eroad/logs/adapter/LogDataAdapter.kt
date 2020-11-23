package com.eroad.logs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroad.logs.R
import com.eroad.logs.data.SensorData
import kotlinx.android.synthetic.main.item_log_data.view.*
import kotlin.collections.ArrayList


class LogDataAdapter() : RecyclerView.Adapter<LogDataAdapter.SuggestedViewHolder>() {

    private var sensorDataList= ArrayList<SensorData>()

    fun updateList(sensorDataList: ArrayList<SensorData>){
        this.sensorDataList = sensorDataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log_data, parent, false)
        return SuggestedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sensorDataList.size
    }

    override fun onBindViewHolder(holderSuggested: SuggestedViewHolder, position: Int) {
        return holderSuggested.bind(sensorDataList[position],position)
    }

    inner class SuggestedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: SensorData, pos:Int) {
            with(itemView) {
                tvTSec.text = "TSec = " + data.tSec.toString()
                tvX.text = "X = " + data.xAcc.toString()
                tvY.text = "Y = " + data.yAcc.toString()
                tvZ.text = "Z = " + data.zAcc.toString()
            }

        }
    }




}




