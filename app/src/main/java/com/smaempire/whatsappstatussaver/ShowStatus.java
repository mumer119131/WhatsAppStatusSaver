package com.smaempire.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class ShowStatus extends AppCompatActivity {
    ImageView statusImg;
    VideoView videoView;
    String type,path;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_status);
        statusImg = findViewById(R.id.statusImg);
        videoView = findViewById(R.id.statusVideoView);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        Bundle extras = getIntent().getExtras();
        Intent i = getIntent();
        if (extras == null) {

        } else {
            type = i.getStringExtra("TYPE");
            path = i.getStringExtra("PATH");

        }

        if (type.equals("mp4")) {
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(path);
            videoView.start();

        }else{
            statusImg.setVisibility(View.VISIBLE);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            statusImg.setImageBitmap(bitmap);
        }

    }
}