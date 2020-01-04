package com.esasyassistivetouch.democontentprovider;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateDBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_db);
        EditText edStudentName = findViewById(R.id.ed_student_name);
        EditText edStudentUni = findViewById(R.id.ed_student_uni);
        Button btAdd = findViewById(R.id.bt_add);
        Button btRetrive= findViewById(R.id.bt_retrive);
        btAdd.setOnClickListener(v -> {
            ContentValues values = new ContentValues();

            values.put(CustomProvider.NAME, edStudentName.getText().toString());

            values.put(CustomProvider.UNI, edStudentUni.getText().toString());

            Uri uri = getContentResolver().insert(CustomProvider.CONTENT_URI, values);

            if (uri != null) {
                Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            }

        });
        btRetrive.setOnClickListener(v -> startActivity(new Intent(CreateDBActivity.this,ResultActivity.class)));
    }
}
