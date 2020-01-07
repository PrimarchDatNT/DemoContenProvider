package com.esasyassistivetouch.democontentprovider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final Uri CONTENT_URI_IMAGE = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static final String[] PROJECTION = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns._ID, MediaStore.MediaColumns.SIZE};
    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_ID = "extra_id";
    private static final String NO_ARGUMENT = "no_argument";
    private RecyclerView rvListImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvListImage = findViewById(R.id.rv_list_image);
        Button btGetList = findViewById(R.id.bt_get_list);
        Button btCreate = findViewById(R.id.bt_create);
        EditText edQuery = findViewById(R.id.ed_query_argument);
        rvListImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvListImage.setItemAnimator(new DefaultItemAnimator());
        rvListImage.setNestedScrollingEnabled(false);
        btGetList.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(edQuery.getText())) {
                getData(edQuery.getText().toString());
            } else {
                getData(NO_ARGUMENT);
            }

        });
        btCreate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CreateDBActivity.class)));

    }


    private void getData(String arg) {
        CursorLoader cursorLoader;
        if (arg.equals(NO_ARGUMENT)) {
            cursorLoader = new CursorLoader(this, CONTENT_URI_IMAGE, null, null, null
                    , MediaStore.MediaColumns._ID + " DESC");
        } else {
            cursorLoader = new CursorLoader(this, CONTENT_URI_IMAGE, PROJECTION, MediaStore.MediaColumns._ID + " = ?"
                    , new String[]{arg}, MediaStore.MediaColumns._ID + " ASC");
        }
        Cursor cursor = cursorLoader.loadInBackground();
        ArrayList<ImageResult> listImageResult = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ImageResult result = new ImageResult();
                result.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)));
                result.setName(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)));
                result.setSize(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE)));
                result.setId(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)));
                listImageResult.add(result);
            }
            cursor.close();
        }

        ImageResultAdapter adapter = new ImageResultAdapter(this, listImageResult);
        adapter.notifyDataSetChanged();
        rvListImage.setAdapter(adapter);
        adapter.setItemOnclickListener((view, position, isLongClick) -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            String data = listImageResult.get(position).getPath();
            String id = listImageResult.get(position).getId();
            intent.putExtra(EXTRA_URL, data);
            intent.putExtra(EXTRA_ID, id);
            startActivity(intent);
        });

    }


}
