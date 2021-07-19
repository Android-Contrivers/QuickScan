package com.example.quickscan;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.opencv.android.OpenCVLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab1,fab2,fab3;
    Boolean fabVisibility = false;
    public static final int CAMERA_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab1 = findViewById(R.id.FAB1);
        fab2 = findViewById(R.id.FAB2);
        fab3 = findViewById(R.id.FAB3);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fabVisibility){
                    fab2.setVisibility(View.GONE);
                    fab3.setVisibility(View.GONE);
                    fabVisibility = false;
                } else {
                    fab2.setVisibility(View.VISIBLE);
                    fab3.setVisibility(View.VISIBLE);
                    fabVisibility = true;
                }
            }
        });


        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetMultipleContents(),
                new ActivityResultCallback<List<Uri>>() {
            @Override
            public void onActivityResult(List<Uri> result) {
                String [] photos;
                int len = result.size();
                photos = new String[len];
                for(int i = 0;i<len;i++){
                    photos[i] = result.get(i).toString();
                }

                Intent intent1 = new Intent(MainActivity.this,ImageProcessingActivity.class);
                intent1.putExtra("photos",photos);
                startActivity(intent1);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        ActivityResultLauncher<Intent> mGetImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
                    
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) {


                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        mGetImage.launch(intent);
                    }
                 else {
                    requestPermissions(new String[] {Manifest.permission.CAMERA},CAMERA_CODE);
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Permission denied to use this feature", Toast.LENGTH_SHORT).show();
                }
        }
    }
}


