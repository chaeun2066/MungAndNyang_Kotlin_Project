package com.example.mungandnyang

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.mungandnyang.databinding.ActivityMapoguideBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapoguideActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    companion object{
        const val LATITUDE = 37.579001
        const val LONGTITUDE = 126.890423
        const val ADOPTNAME = "서울동물복지지원 마포센터"
    }
    var apiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMapoguideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
            if(it.all { permission -> permission.value == true }){
                apiClient?.connect()
            }else{
                Toast.makeText(this, "권한거부 이유로 맵앱을 사용할 수 없습니다", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        (supportFragmentManager.findFragmentById(R.id.mapoMapView) as SupportMapFragment?)?.getMapAsync(this)
        if(
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) !== PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED ){

            requestPermissionLauncher.launch( arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ))
        }else{
            apiClient?.connect()
        }

        binding.ivCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:02-2124-2839")
            startActivity(intent)
        }

        setSupportActionBar(binding.mapoToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConnected(p0: Bundle?) {

    }

    override fun onConnectionSuspended(data: Int) {
        Log.d("mungmap", "Location Provider 더 이상 이용이 불가능한 상황 ${data}")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.d("mungmap", "Location Provider가 제공되지 않는 상황 ${connectionResult.errorMessage}")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val center = LatLng(LATITUDE, LONGTITUDE)
        googleMap.addMarker(MarkerOptions().position(center).title(ADOPTNAME))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(center))

        val cameraPosition = CameraPosition.Builder()
            .target(center)
            .zoom(14f)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, AdoptguideActivity::class.java)
        startActivity(intent)
        finish()
    }
}