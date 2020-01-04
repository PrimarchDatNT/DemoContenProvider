package com.esasyassistivetouch.democontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvResult = findViewById(R.id.tv_result);
        String data = getData();
        tvResult.setText(data);
    }

    public String getData() {
        Cursor cursor;
        String result = null;
        Uri uriQuery = Uri.parse("content://com.segu.demo.provider/students");
        cursor = this.getContentResolver().query(uriQuery, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                result = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            }
            cursor.close();
        }
        return result;
    }
}
