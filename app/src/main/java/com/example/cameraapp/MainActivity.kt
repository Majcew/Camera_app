package com.example.cameraapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.configuration.UpdateConfiguration
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.selector.front
import io.fotoapparat.selector.off
import io.fotoapparat.selector.torch
import io.fotoapparat.view.CameraView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var camera: Fotoapparat? = null
    private var lensLocation:CameraType? = null
    private var flashState:FlashState? = null
    private val path = ""

    private val permissions = arrayOf(Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createCamera()
        lensLocation = CameraType.BACK
        flashState = FlashState.OFF
        fab_camera.setOnClickListener {
            takePhoto()
        }

        fab_switch_camera.setOnClickListener {
            switchCamera()
        }
        fab_flash.setOnClickListener {
            changeFlashState()
        }

    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

    }*/
    override fun onStart() {
        super.onStart()
        if (!hasPermissions()) {
            requestPermission()
        }else{
            camera?.start()
        }
    }

    override fun onStop() {
        super.onStop()
        camera?.stop()
    }

    private fun changeFlashState() {
        camera?.updateConfiguration(
            UpdateConfiguration(
                flashMode = if (flashState == FlashState.OFF) torch() else off()
            )
        )
        flashState = if(flashState == FlashState.OFF) FlashState.ON else FlashState.OFF
    }

    private fun switchCamera() {
        flashState = FlashState.OFF
        camera?.switchTo(
            lensPosition = if(lensLocation == CameraType.BACK) front() else back(),
            cameraConfiguration = CameraConfiguration.default()
        )
        lensLocation = if(lensLocation == CameraType.BACK)
            CameraType.FRONT
        else
            CameraType.BACK
    }

    private fun takePhoto() {
        camera?.takePicture()
        //val photo = camera?.takePicture()
        //photo?.saveToFile(path)
    }
    //Funkcja sprawdzajaca, czy aplikacja posiada potrzebne pozwolenia do poprawnego działania
    private fun hasPermissions(): Boolean{
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
    //Funkcja prosząca o dane pozwolenie
    private fun requestPermission(){
        ActivityCompat.requestPermissions(this, permissions,0)
    }
    //Tworzy instancję o danych atrybutach
    private fun createCamera() {
        val cameraView = findViewById<CameraView>(R.id.camera_view)

        camera = Fotoapparat(
            context = this,
            view = cameraView, //gdzie ma wyświetlać obraz
            scaleType = ScaleType.CenterCrop,
            lensPosition = back() //który aparat ma wybrać (tylni to back())
        )
    }
    enum class CameraType {FRONT,BACK}
    enum class FlashState {ON,OFF}
}
