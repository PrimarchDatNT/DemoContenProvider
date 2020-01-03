package com.esasyassistivetouch.democontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty);
        ImageView ivDetail = findViewById(R.id.iv_image_detail);
        Button btShare = findViewById(R.id.bt_share);
        Intent intent = getIntent();
        String imageDetailURL = intent.getStringExtra(EXTRA_URL);
        Glide.with(this).load(imageDetailURL).into(ivDetail);
        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_SEND);
                Uri uriShare = Uri.parse(imageDetailURL);

                intent.putExtra(Intent.EXTRA_STREAM ,uriShare);
                startActivity(Intent.createChooser(intent,"Share with : "));
            }
        });
    }
}
