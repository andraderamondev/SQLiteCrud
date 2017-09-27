package com.dev.ramon.sqlitecrud.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.dev.ramon.sqlitecrud.BDSQLiteHelper;
import com.dev.ramon.sqlitecrud.R;
import com.dev.ramon.sqlitecrud.objects.Course;

public class AddCourseActivity extends AppCompatActivity {
    EditText etName;
    EditText etDescription;
    EditText etClassHours;
    BDSQLiteHelper SQLHelper;
    Button btSave;
    Switch swStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etClassHours = (EditText) findViewById(R.id.etClassHours);
        btSave = (Button) findViewById(R.id.btSave);
        swStatus = (Switch) findViewById(R.id.switch2);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

    }

    private boolean validate(){
        SQLHelper = new BDSQLiteHelper(AddCourseActivity.this);
        Course c = new Course();
        c.setId(1);
        c.setName(etName.getText().toString());
        c.setDescription(etDescription.getText().toString());
        c.setClassHours(Integer.parseInt(etClassHours.getText().toString()));
        c.setStatus(swStatus.isChecked());
        SQLHelper.addCourse(c);
        return true;
    }
}
