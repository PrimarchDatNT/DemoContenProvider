package com.esasyassistivetouch.democontentprovider.main;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esasyassistivetouch.democontentprovider.R;
import com.esasyassistivetouch.democontentprovider.data.StudentProvider;

public class CreateDBActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String GET_DATA_FROM_MY_PROVIDER_PACKAGE_NAME = "com.esasyassistivetouch.getdatafrommyprovider";
    EditText edStudentName;
    EditText edStudentUni;
    Button btAdd;
    Button btRetrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_db);
        initView();
    }

    private void initView() {
        edStudentName = findViewById(R.id.ed_student_name);
        edStudentUni = findViewById(R.id.ed_student_uni);
        btAdd = findViewById(R.id.bt_add);
        btRetrive = findViewById(R.id.bt_retrive);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                ContentValues values = new ContentValues();

                values.put(StudentProvider.COLUMN_STUDENT_NAME, edStudentName.getText().toString());

                values.put(StudentProvider.COLUMN_STUDENT_UNIVERSITY, edStudentUni.getText().toString());

                Uri uri = getContentResolver().insert(StudentProvider.CONTENT_URI, values);

                if (uri != null) {
                    Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bt_retrive:
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(GET_DATA_FROM_MY_PROVIDER_PACKAGE_NAME);
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
                break;
        }
    }
}
