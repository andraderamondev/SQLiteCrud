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
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.ramon.sqlitecrud.BDSQLiteHelper;
import com.dev.ramon.sqlitecrud.R;
import com.dev.ramon.sqlitecrud.objects.Course;

public class PersistCourseActivity extends AppCompatActivity {
    EditText etName;
    EditText etDescription;
    BDSQLiteHelper SQLHelper;
    Button btSave;
    Switch swStatus;
    SeekBar sbClassHours;
    TextView tvClassHours;
    int progress = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etName = (EditText) findViewById(R.id.etName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        btSave = (Button) findViewById(R.id.btSave);
        swStatus = (Switch) findViewById(R.id.switch2);

        sbClassHours = (SeekBar) findViewById(R.id.seekBar);
        tvClassHours = (TextView) findViewById(R.id.tvClassHours);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        sbClassHours.setMax(100);
        sbClassHours.setProgress(10);
        sbClassHours.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                if(progress<10){
                    sbClassHours.setProgress(10);
                }
                tvClassHours.setText(progress + "h");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private boolean validate(){
        SQLHelper = new BDSQLiteHelper(PersistCourseActivity.this);
        int error = 0;
        Course c = new Course();
        c.setId(1);
        if(!etName.getText().toString().isEmpty()){
            c.setName(etName.getText().toString());
        }else{
            etName.setError("Preencha esse campo");
            error++;
        }
        if(!etDescription.getText().toString().isEmpty()){
            c.setName(etDescription.getText().toString());
        }else{
            etDescription.setError("Preencha esse campo");
            error++;
        }
        c.setClassHours(progress);
        c.setStatus(swStatus.isChecked());
        if(error==0){
            SQLHelper.addCourse(c);
            Toast.makeText(this,"Curso cadastrado",Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}
