package com.example.quickscan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageProcessingActivity extends AppCompatActivity {

    ImageView img1;
    Button proceedBtn,backBtn,CrossBtn;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_processing);

        String [] photos = getIntent().getExtras().getStringArray("photos");
        int length = photos.length;
        img1 = findViewById(R.id.img1);
        proceedBtn = findViewById(R.id.proceedBtn);
        backBtn = findViewById(R.id.backPhotoBtn);
        img1.setImageURI(Uri.parse(photos[count]));

        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count < length-1) {
                    count++;
                    img1.setImageURI(Uri.parse(photos[count]));
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count > 0) {
                    count--;
                    img1.setImageURI(Uri.parse(photos[count]));
                }
            }
        });



    }
}