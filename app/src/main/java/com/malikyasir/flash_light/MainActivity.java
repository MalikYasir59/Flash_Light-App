package com.malikyasir.flash_light;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;

import android.content.pm.PackageManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;



public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 50;
    private ImageView flashlightToggle;
    private boolean isFlashlightOn = false;
    private CameraManager cameraManager;
    private String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("FlashLight");




        flashlightToggle = findViewById(R.id.flashlight_toggle);

        cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST);
        }
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        flashlightToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlashlight();

            }
        });
    }

    private void toggleFlashlight() {
        try {
            TextView flashlightStatus = findViewById(R.id.flashlight_status);
            if (isFlashlightOn) {
                cameraManager.setTorchMode(cameraId, false);
                flashlightToggle.setImageResource(R.drawable.flashoff);
                isFlashlightOn = false;
                flashlightStatus.setText(R.string.flashlight_is_off);
            } else {
                cameraManager.setTorchMode(cameraId, true);
                flashlightToggle.setImageResource(R.drawable.flashon);
                isFlashlightOn = true;
                flashlightStatus.setText(R.string.flashlight_is_on);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }



    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == CAMERA_REQUEST) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"access given",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this,"access denied",Toast.LENGTH_LONG).show();
                }
            }
        }

    }

