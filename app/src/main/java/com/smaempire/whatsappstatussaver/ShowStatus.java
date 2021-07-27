package com.smaempire.whatsappstatussaver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.appbar.MaterialToolbar;
import com.smaempire.whatsappstatussaver.Constants.Constants;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ShowStatus extends AppCompatActivity {
    ImageView statusImg;
    VideoView videoView;
    String type,path;
    MaterialToolbar toolbar;
    String fileName;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_status_menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_status);
        statusImg = findViewById(R.id.statusImg);
        videoView = findViewById(R.id.statusVideoView);
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        toolbar = findViewById(R.id.toolbarStatus);

        ActionMenuItemView downloadBtn = toolbar.findViewById(R.id.downloadStatus);

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

        int index = path.lastIndexOf("/");
        fileName="file";
        if(index>0){
            fileName = path.substring(index+1);
        }
        String savedPath = Environment.getExternalStorageDirectory().getAbsolutePath() +Constants.SAVE_FOLDER_NAME+fileName;
        File file = new File(savedPath);
        if (file.exists()){
            downloadBtn.setVisibility(View.GONE);
        }




    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.downloadStatus:
                    downloadStatus();
                    break;
                case R.id.shareStatus:
                    shareStatus();
                    break;
            }

        return true;
        }
    });
    }

    private void shareStatus() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path)));

        if (path.endsWith(".mp4")) {
            i.setType("video/mp4");
            try {
                startActivity(Intent.createChooser(i, "Send Video via :"));
            } catch (android.content.ActivityNotFoundException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            i.setType("image/jpg");
            try {
                startActivity(Intent.createChooser(i, "Send Video via :"));
            } catch (android.content.ActivityNotFoundException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadStatus(){
            final File file = new File(path);
            String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
            File destFile = new File(destPath);

            try {
                FileUtils.copyFileToDirectory(file,destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }



            MediaScannerConnection.scanFile(
                    this,
                    new String[]{destPath + fileName},
                    new String[]{"*/*"},
                    new MediaScannerConnection.MediaScannerConnectionClient() {
                        @Override
                        public void onMediaScannerConnected() {

                        }

                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    }
            );
            Toast.makeText(this, "Saved to : "+destPath+fileName, Toast.LENGTH_SHORT).show();
        }
    }


