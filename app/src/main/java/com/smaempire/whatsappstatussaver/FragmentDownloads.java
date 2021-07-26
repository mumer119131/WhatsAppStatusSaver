package com.smaempire.whatsappstatussaver;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smaempire.whatsappstatussaver.Adapter.DownloadAdapter;
import com.smaempire.whatsappstatussaver.Adapter.StatusAdapter;
import com.smaempire.whatsappstatussaver.Constants.Constants;

import java.io.File;
import java.util.ArrayList;

public class FragmentDownloads extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Object> filesList = new ArrayList<>();
    private Button refreshBtn;
    DownloadAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root= (ViewGroup) inflater.inflate(R.layout.fragment_downloads,container,false);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        recyclerView = root.findViewById(R.id.recyclerViewDownloads);

        setUpRefresh();
        return root;
    }


    private void setUpRefresh() {
        filesList.clear();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DownloadAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private ArrayList<Object> getData() {
        StatusModel model;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.SAVE_FOLDER_NAME;
        File targetDirector = new File(targetPath);

        File[] files = targetDirector.listFiles();


        try {
            if(files != null){

                for (int i = 0; i < files.length; i++) {

                    File file = files[i];
                    model = new StatusModel();
                    model.setUri(Uri.fromFile(file));
                    model.setPath(files[i].getAbsolutePath());
                    model.setFilename(file.getName());

                    if (!model.getUri().toString().endsWith(".nomedia")) {
                        filesList.add(model);
                    }

                }
            }
        }catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        return filesList;
    }


}
