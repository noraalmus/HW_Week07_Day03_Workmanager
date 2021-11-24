package com.example.hw_workmanger

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.TimeUnit
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var textView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

   textView=findViewById<TextView>(R.id.text)

        var worker= PeriodicWorkRequestBuilder<LocationWorker>(8,
            java.util.concurrent.TimeUnit.HOURS, 15, java.util.concurrent.TimeUnit.MINUTES
        )


        //create data for worker for holding vaibales
        var data= Data.Builder()
            .putInt("Loop",25)
        worker.setInputData(data.build())

        //  .build()

        WorkManager.getInstance(this)
            .enqueue(worker.build())
    }


}