package com.esasyassistivetouch.democontentprovider.main;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.esasyassistivetouch.democontentprovider.R;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EXTRA_URL = "extra_url";
    private static final String EXTRA_ID = "extra_id";

    ImageView ivDetail;
    Button btShare;
    Button btDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activty);
        initView();
        Intent intent = getIntent();
        String imageDetailURL = intent.getStringExtra(EXTRA_URL);
        Glide.with(this).load(imageDetailURL).into(ivDetail);

    }

    private void initView() {
        ivDetail = findViewById(R.id.iv_image_detail);
        btShare = findViewById(R.id.bt_share);
        btDelete = findViewById(R.id.bt_delete);
    }

    @Override
    public void onClick(View v) {
        Intent intentData = getIntent();
        switch (v.getId()) {
            case R.id.bt_share:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("image/*");
                intentShare.setAction(Intent.ACTION_SEND);
                intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse((intentData.getStringExtra(EXTRA_URL))));
                startActivity(Intent.createChooser(intentShare, "Share with : "));
                break;
            case R.id.bt_delete:
                String imageDetailID = intentData.getStringExtra(EXTRA_ID);
                if (!TextUtils.isEmpty(imageDetailID)) {
                    Uri deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Long.parseLong(imageDetailID));
                    getContentResolver().delete(deleteUri, MediaStore.MediaColumns._ID + "= ?", new String[]{imageDetailID});
                    ivDetail.setImageResource(R.drawable.ic_launcher_background);
                }

                break;
        }
    }
}


