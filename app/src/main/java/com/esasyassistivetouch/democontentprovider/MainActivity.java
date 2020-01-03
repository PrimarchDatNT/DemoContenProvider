package com.esasyassistivetouch.democontentprovider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final Uri CONTENT_URI_IMAGE = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static final String[] PROJECTION = {MediaStore.MediaColumns.DATA};
    private static final String EXTRA_URL = "extra_url";
    private ArrayList<String> listImageURI;
    private ImageResultAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listImageURI = new ArrayList<>();
        setContentView(R.layout.activity_main);
        RecyclerView rvListImage = findViewById(R.id.rv_list_image);
        Button btGetList = findViewById(R.id.bt_get_list);
        rvListImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvListImage.setItemAnimator(new DefaultItemAnimator());
        rvListImage.setNestedScrollingEnabled(false);
        btGetList.setOnClickListener(v -> {
            listImageURI = getAllImageOnDevice();
            imageAdapter = new ImageResultAdapter(listImageURI, MainActivity.this);
            imageAdapter.notifyDataSetChanged();
            rvListImage.setAdapter(imageAdapter);
            imageAdapter.setItemOnclickListener((view, position, isLongClick) -> {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                String data = listImageURI.get(position);
                intent.putExtra(EXTRA_URL, data);
                startActivity(intent);
            });
        });

    }


    public ArrayList<String> getAllImageOnDevice() {
        ArrayList<String> listImagePath = new ArrayList<>();
        ArrayList<ImageResult> listResults = new ArrayList<>();
        ImageResult imageResult = null;
        Cursor cursor;
        cursor = this.getContentResolver().query(CONTENT_URI_IMAGE, PROJECTION, null, null, null);
        String absolutePathOfImage;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                listImagePath.add(absolutePathOfImage);
            }
            cursor.close();
        }
        return listImagePath;
    }

    private void initItemViewListener() {
        imageAdapter.setItemOnclickListener((view, position, isLongClick) -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            String data = listImageURI.get(position);
            intent.putExtra(EXTRA_URL, data);
            startActivity(intent);
        });
    }
}
