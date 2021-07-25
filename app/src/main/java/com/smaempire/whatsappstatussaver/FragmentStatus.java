package com.smaempire.whatsappstatussaver;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.smaempire.whatsappstatussaver.Adapter.StatusAdapter;
import com.smaempire.whatsappstatussaver.Constants.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentStatus extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Object> filesList = new ArrayList<>();
    File[] files;
    StatusAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_status, container, false);


        Dexter.withContext(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        recyclerView = root.findViewById(R.id.rvStatus);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter = new StatusAdapter(getActivity(), getData());
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


        return root;
    }

    private ArrayList<Object> getData() {
        StatusModel model;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.FOLDER_NAME + "Media/.Statuses";
        File targetDirector = new File(targetPath);
        files = targetDirector.listFiles();

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
        return filesList;
    }
}
