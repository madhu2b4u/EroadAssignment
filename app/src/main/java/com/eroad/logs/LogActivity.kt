package com.eroad.logs

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.eroad.logs.adapter.LogDataAdapter
import com.eroad.logs.data.LogData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_log_file.*


class LogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_file)


        val data = intent.getStringExtra("data")
        val logData = convertStringToJson(data)
        val logList = logData.sensorData
        val adapter = LogDataAdapter()
        if (logList != null) {
            adapter?.updateList(logList)
        }
        rvLogData.adapter = adapter

        tvStart.text = "Start = " +logData?.start.toString()
        tvEnd.text = "End = " +logData?.end.toString()



    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val myIntent = Intent(applicationContext, MainActivity::class.java)
        startActivityForResult(myIntent, 0)
        return true
    }

    private fun convertStringToJson(data:String): LogData {
        val gson = Gson()
        val convertedObject: LogData = Gson().fromJson(data, LogData::class.java)
        return convertedObject

    }
}