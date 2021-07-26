package com.smaempire.whatsappstatussaver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smaempire.whatsappstatussaver.Constants.Constants;
import com.smaempire.whatsappstatussaver.R;
import com.smaempire.whatsappstatussaver.ShowStatus;
import com.smaempire.whatsappstatussaver.StatusModel;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.Holder>{
    Context context;
    ArrayList<Object> list;

    public DownloadAdapter(Context context, ArrayList<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DownloadAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.status_card_row,null,false);
        return new DownloadAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadAdapter.Holder holder, int position) {
        final StatusModel files = (StatusModel) list.get(position);
        String fileName = files.getFilename();
        if(fileName.length()>10){
            String extension = fileName.substring(fileName.length()-4,fileName.length());
            fileName = fileName.substring(0,10)+"..."+extension;
        }
        holder.fileNameTV.setText(fileName);
        holder.downloadImg.setVisibility(View.GONE);

        if(files.getUri().toString().endsWith(".mp4")){
            holder.playBtn.setVisibility(View.VISIBLE);
        }else {
            holder.playBtn.setVisibility(View.INVISIBLE);
        }

        Glide.with(context)
                .load(files.getUri())
                .into(holder.mainImg);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(files.getUri().toString().endsWith(".mp4")){
                    Intent intent = new Intent(context, ShowStatus.class);
                    intent.putExtra("TYPE","mp4");
                    intent.putExtra("PATH",files.getPath());
                    context.startActivity(intent);

                }else {
                    holder.playBtn.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(context, ShowStatus.class);
                    intent.putExtra("PATH", files.getPath());
                    intent.putExtra("TYPE","jpg");
                    context.startActivity(intent);
                }

            }
        });
        holder.shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.putExtra(Intent.EXTRA_STREAM,files.getUri());

                if(files.getUri().toString().endsWith(".mp4")){
                    i.setType("video/mp4");
                    try {
                        context.startActivity(Intent.createChooser(i,"Send Video via :"));
                    }catch (android.content.ActivityNotFoundException e){
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    i.setType("image/jpg");
                    try {
                        context.startActivity(Intent.createChooser(i,"Send Video via :"));
                    }catch (android.content.ActivityNotFoundException e){
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView mainImg,playBtn,downloadImg,shareImg;
        TextView fileNameTV;
        CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mainImg = itemView.findViewById(R.id.mainImageView);
            downloadImg = itemView.findViewById(R.id.downloadBtn);
            shareImg = itemView.findViewById(R.id.shareBtn);
            playBtn = itemView.findViewById(R.id.playButtonImg);
            cardView = itemView.findViewById(R.id.cardView);
            fileNameTV = itemView.findViewById(R.id.fileNameTV);


        }
    }
}
