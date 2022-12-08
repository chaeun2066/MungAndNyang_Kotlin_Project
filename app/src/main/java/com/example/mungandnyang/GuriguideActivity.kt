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

class GuriguideActivity : AppCompatActivity() , GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    companion object{
        const val LATITUDE = 37.501288 //위도
        const val LONGTITUDE = 126.878581 // 경도
        const val ADOPTNAME = "서울동물복지지원 구리센터"
    }
    lateinit var binding: ActivityGuriguideBinding
    var apiClient: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuriguideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 권한허용 콜백 함수
        val requestPermissionLauncher = registerForActivityResult(
            //여러 개의 퍼미션 요청할 때
            ActivityResultContracts.RequestMultiplePermissions()){
            //이 권한들 값이 true가 된다면 사용자에게 연결
            if(it.all { permission -> permission.value == true }){
                apiClient?.connect()
            }else{
                Toast.makeText(this, "권한거부 이유로 맵앱을 사용할 수 없습니다", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        //비동기적 방식으로 구글 맵 실행
        //지도가 정보가 올 경우에 어떤 Fragment에 이 지도 정보를 보여줄 지를 자동연결 시켜줌
        //바인딩으로 찾으면 안되고 R.id로 찾을 것, SupportMapFragment로 형변환해서 지원맵은 이 액티비티라고 연결 시켜줌
        (supportFragmentManager.findFragmentById(R.id.guriMapView) as SupportMapFragment?)?.getMapAsync(this)

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

        binding.btnCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:02-2636-7645")
            startActivity(intent)
        }

        setSupportActionBar(binding.guriToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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

    override fun onMapReady(googleMap: GoogleMap) { //지도를 사용할 준비가 되면 호출
        val center = LatLng(LATITUDE, LONGTITUDE) // LatLng 하나의 위경도 좌표를 나타내는 것
        // 구글맵에 center 위치에 구글에서 지원하는 아이콘으로 마커를 표시하고, 상수로 정한 AOPTNAME을 타이틀로 하겠다
        googleMap.addMarker(MarkerOptions().position(center).title(ADOPTNAME))

        //카메라위치 설정
        val cameraPosition = CameraPosition.Builder()
            .target(center)
            .zoom(14f)
            .build()
        //설정한 해당 설정과 카메라 위치로 카메라를 이동
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, AdoptguideActivity::class.java)
        startActivity(intent)
        finish()
    }
}