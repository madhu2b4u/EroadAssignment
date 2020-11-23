package com.eroad.logs.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eroad.logs.R
import kotlinx.android.synthetic.main.item_log.view.*
import java.io.File
import kotlin.collections.ArrayList


class LogsAdapter() : RecyclerView.Adapter<LogsAdapter.SuggestedViewHolder>() {

    private var clickFunction: ((file: File, pos:Int) -> Unit)? = null
    private var logsList= ArrayList<File>()

    fun updateList(notification: ArrayList<File>){
        this.logsList = notification
        notifyDataSetChanged()
    }

    fun clickListener(removeProduct: (File, Int) -> Unit) {
        this.clickFunction = removeProduct
    }

    fun getList(): ArrayList<File> {
        return logsList
    }

    fun removeItem(pos: Int){
        logsList.removeAt(pos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        return SuggestedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return logsList.size
    }

    override fun onBindViewHolder(holderSuggested: SuggestedViewHolder, position: Int) {
        return holderSuggested.bind(logsList[position],position)
    }

    inner class SuggestedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(file: File, pos:Int) {
            with(itemView) {
                tvX.text = file.name
                setOnClickListener {
                    clickFunction?.invoke(file,pos)
                }
            }

        }
    }




}




