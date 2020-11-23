package com.eroad.logs

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import androidx.appcompat.app.AppCompatActivity
import com.eroad.logs.adapter.LogsAdapter
import com.eroad.logs.data.LogData
import com.eroad.logs.data.SensorData
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), SensorEventListener {

    private var accelerometer: Sensor? = null
    private var sensorManager: SensorManager? = null
    private var isStarted = true
    private var listOfSensor = ArrayList<SensorData>()
    private var logAdapter : LogsAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        logAdapter = LogsAdapter()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        btnStart?.setOnClickListener {
            if (isStarted){
                isStarted = false
                btnStart.text = "Stop and Save data"
                sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
            }else{
                isStarted = true
                btnStart.text = "Start"
                sensorManager?.unregisterListener(this);
                saveData()

            }
        }


        logAdapter?.updateList(getListFiles(File(filesDir,"")))
        rvSavedList.adapter = logAdapter
        logAdapter?.clickListener { file, i ->

            val intent = Intent(this,LogActivity::class.java)
            intent.putExtra("data",readFile(file.name))
            startActivity(intent)
        }

    }

    private fun saveData() {
        val logs = LogData(epochTime(),epochTime(),listOfSensor)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonElement = gson.toJsonTree(logs)
        val jsonStr = gson.toJson(jsonElement)
        writeToFile(jsonStr)

    }

    private fun writeToFile(data: String) {
        val file = File(applicationContext.filesDir, getTime()+".json")
        val fileWriter = FileWriter(file)
        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(data)
        bufferedWriter.close()
        logAdapter?.updateList(getListFiles(File(filesDir,"")))
    }


    override fun onSensorChanged(event: SensorEvent) {
        x.text = "X = " + event.values[0]
        y.text = "Y = " + event.values[1]
        z.text = "Z = " + event.values[2]

        listOfSensor.add(SensorData(epochTime(),event.values[0],event.values[1],event.values[2]))

        e("Accelerometer ", "X value " + event.values[0] + " Y value " + event.values[1] + " Z value " + event.values[2])
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}


    private fun epochTime(): Long {
        val tsLong = System.currentTimeMillis() / 1000
        return  tsLong
    }

    private fun getTime(): String {
        val sdf = SimpleDateFormat("YYYY-MM-dd hh-mm-ss-SSS", Locale.ENGLISH)
        Log.e("Ss","Sensor_"+sdf.format(Date()))
        return "Sensor_"+sdf.format(Date())
    }

    private fun getListFiles(parentDir: File): ArrayList<File> {
        val inFiles = ArrayList<File>()
        val files = parentDir.listFiles()
        for (file in files) {
            if (file.isDirectory) {
                inFiles.addAll(getListFiles(file)!!)
            } else {
                if (file.name.endsWith(".json")) {
                    inFiles.add(file)
                }
            }
        }
        return inFiles
    }


    private fun readFile(name: String): String {
        var ret = ""
        var inputStream: InputStream? = null
        try {
            inputStream = openFileInput(name)
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = java.lang.StringBuilder()
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append(receiveString)
                }
                ret = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            e("login activity", "File not found: $e")
        } catch (e: IOException) {
            e("login activity", "Can not read file: $e")
        } finally {
            try {
                inputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return ret
    }

}