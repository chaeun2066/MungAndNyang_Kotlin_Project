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
import com.example.mungandnyang.databinding.ActivityAdoptguideBinding
import com.example.mungandnyang.databinding.ActivityGuriguideBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AdoptguideActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAdoptguideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMapo.setOnClickListener{
            val intent = Intent(this, MapoguideActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnGuri.setOnClickListener{
            val intent = Intent(this, GuriguideActivity::class.java)
            startActivity(intent)
            finish()
        }

        setSupportActionBar(binding.adoptlistToolbar)
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
}
