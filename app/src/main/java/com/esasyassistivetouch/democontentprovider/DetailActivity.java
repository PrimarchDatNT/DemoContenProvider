package com.esasyassistivetouch.democontentprovider;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_ID = "extra_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty);
        ImageView ivDetail = findViewById(R.id.iv_image_detail);
        Button btShare = findViewById(R.id.bt_share);
        Button btDelete = findViewById(R.id.bt_delete);
        Intent intent = getIntent();
        String imageDetailURL = intent.getStringExtra(EXTRA_URL);
        String imageDetailID = intent.getStringExtra(EXTRA_ID);
        Glide.with(this).load(imageDetailURL).into(ivDetail);
        btShare.setOnClickListener(v -> {
            Intent intent1 = new Intent(Intent.ACTION_SEND);
            intent1.setType("image/*");
            intent1.setAction(Intent.ACTION_SEND);
            Uri uriShare = Uri.parse(imageDetailURL);

            intent1.putExtra(Intent.EXTRA_STREAM, uriShare);
            startActivity(Intent.createChooser(intent1, "Share with : "));
        });
        btDelete.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(DetailActivity.this)) {

                    if (imageDetailID != null) {
                        Uri deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.parseLong(imageDetailID));
                        getContentResolver().delete(deleteUri, MediaStore.MediaColumns._ID + "=" + imageDetailID, null);
                        Log.e("CV", "onCreate: " +MediaStore.MediaColumns._ID );
                        ivDetail.setImageResource(R.drawable.ic_launcher_background);
                    }
                } else {
                    Intent intentPermission = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intentPermission.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intentPermission);
                }

            } else {

                getContentResolver().delete(Uri.parse(imageDetailURL), MediaStore.MediaColumns._ID + "=\"" + imageDetailURL + "\"" , null);
            }

        });


    }
}
