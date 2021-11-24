package com.example.hw_workmanger

import android.Manifest
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
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LocationWorker(context:Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {

    val db = Firebase.firestore


    override fun doWork(): Result {



        val location = hashMapOf(
            "latitude" to latitude,
            "longitude" to longitude
        )

// Add a new document with a generated ID
        db.collection("Locations")
            .add(location)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }



    fun checkPermissionForLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            // show request permission dialog
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION),1)

        }else{


        }

    }


    fun showLocation(){

        var locationManager =getSystemService(AppCompatActivity.LOCATION_SERVICE) as? LocationManager


        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,0,0f,
            object : LocationListener {
                override fun onLocationChanged(location: Location) {


                    db.collection("Locations")
                        .add(location)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }

                }
            }

    })
}


override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

        db.collection("Locations")
            .get(location)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
            }
    }else{

        AlertDialog.Builder(this).apply {
            title= "warning"
            setMessage("To access location go to Setting-> allow location service")
            setPositiveButton("Ok", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {


                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }

            })
        }.show()
    }


}
}