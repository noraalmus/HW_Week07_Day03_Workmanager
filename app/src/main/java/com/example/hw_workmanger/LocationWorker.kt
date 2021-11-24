package com.example.hw_workmanger

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class LocationWorker(context:Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {


    override fun doWork(): Result {
        getLocation()
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    fun getLocation(){

        var locationManager =applicationContext.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        var location =locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        var time=Date()
        var latitude=location?.latitude
        var longitude=location?.longitude

        val db = Firebase.firestore

        val loca = hashMapOf(
           "latitude" to latitude,
          "longitude" to longitude,
            "Date" to time
       )

        db.collection("Locations").add(loca)
            .addOnSuccessListener {

            }

            .addOnFailureListener {

            }
    }
}








