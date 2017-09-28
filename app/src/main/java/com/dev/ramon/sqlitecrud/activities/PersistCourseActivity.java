package com.dev.ramon.sqlitecrud.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.ramon.sqlitecrud.helpers.BDSQLiteHelper;
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
    private Course course;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getValuesExtras(getIntent().getExtras());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(course.getId()>0) {
            toolbar.setTitle("Editar");
        }else{
            toolbar.setTitle("Cadastrar");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etName = (EditText) findViewById(R.id.etName);
        etName.setText(course.getName());
        etDescription = (EditText) findViewById(R.id.etDescription);
        etDescription.setText(course.getDescription());
        btSave = (Button) findViewById(R.id.btSave);
        swStatus = (Switch) findViewById(R.id.switch2);
        ll = (LinearLayout) findViewById(R.id.llStatus);
        if(course.getId()>0) {
            swStatus.setChecked(course.getStatus() ? true : false);
            ll.setVisibility(View.VISIBLE);
        }
        sbClassHours = (SeekBar) findViewById(R.id.seekBar);
        tvClassHours = (TextView) findViewById(R.id.tvClassHours);
        tvClassHours.setText(String.valueOf(course.getClassHours()>10 ? course.getClassHours() : 10)+"h");
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        sbClassHours.setMax(100);
        sbClassHours.setProgress(course.getClassHours()>10 ? course.getClassHours() : 10);
        progress = course.getClassHours()>10 ? course.getClassHours() : 10;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getValuesExtras(Bundle extras) {
        if(extras.getParcelable("course")!=null){
            course = extras.getParcelable("course");
        }else{
            course = new Course();
        }
    }

    private boolean validate(){
        SQLHelper = new BDSQLiteHelper(PersistCourseActivity.this);
        int error = 0;
        if(!etName.getText().toString().isEmpty()){
            course.setName(etName.getText().toString());
        }else{
            etName.setError("Preencha esse campo");
            error++;
        }
        if(!etDescription.getText().toString().isEmpty()){
            course.setDescription(etDescription.getText().toString());
        }else{
            etDescription.setError("Preencha esse campo");
            error++;
        }
        course.setClassHours(progress);
        course.setStatus(swStatus.isChecked());
        if(error==0){
            String msg = "";
            if(course.getId()>0){
                SQLHelper.updateCourse(course);
                msg = "Curso "+course.getName()+" atualizado";
            }else{
                course.setId(1);
                SQLHelper.addCourse(course);
                msg = "Curso cadastrado";
            }
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}
