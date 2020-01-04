package com.esasyassistivetouch.democontentprovider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final Uri CONTENT_URI_IMAGE = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static final String[] PROJECTION = {MediaStore.MediaColumns.DATA};
    private static final String[] PROJECTION_ID = {MediaStore.MediaColumns._ID};
    private static final String ORDER = "date ASC";
    private static final String[] PROJECTION_NAME = {MediaStore.MediaColumns.DISPLAY_NAME};
    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_ID = "extra_id";
    private ArrayList<String> listImageURI;
    private ArrayList<String> listImageID;
    private ImageResultAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listImageURI = new ArrayList<>();
        listImageID = new ArrayList<>();
        setContentView(R.layout.activity_main);
        RecyclerView rvListImage = findViewById(R.id.rv_list_image);
        Button btGetList = findViewById(R.id.bt_get_list);
        Button btCreate = findViewById(R.id.bt_create);
        rvListImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvListImage.setItemAnimator(new DefaultItemAnimator());
        rvListImage.setNestedScrollingEnabled(false);
        btGetList.setOnClickListener(v -> {
            listImageURI = getAllImageOnDevice();
            listImageID = getAllImageID();
            imageAdapter = new ImageResultAdapter(listImageURI, listImageID, MainActivity.this);
            imageAdapter.notifyDataSetChanged();
            rvListImage.setAdapter(imageAdapter);
            imageAdapter.setItemOnclickListener((view, position, isLongClick) -> {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                String data = listImageURI.get(position);
                String id = listImageID.get(position);
                intent.putExtra(EXTRA_URL, data);
                intent.putExtra(EXTRA_ID, id);
                startActivity(intent);
            });
        });
        btCreate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CreateDBActivity.class)));

    }



    public ArrayList<String> getAllImageOnDevice() {
        ArrayList<String> listImagePath = new ArrayList<>();
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

    public ArrayList<String> getAllImageID() {
        ArrayList<String> listImageID = new ArrayList<>();
        Cursor cursor;
        cursor = this.getContentResolver().query(CONTENT_URI_IMAGE, PROJECTION_ID, null, null, null);
        String absolutePathOfImage;
        if (cursor != null) {
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
                listImageID.add(absolutePathOfImage);
            }
            cursor.close();
        }
        return listImageID;
    }

}
