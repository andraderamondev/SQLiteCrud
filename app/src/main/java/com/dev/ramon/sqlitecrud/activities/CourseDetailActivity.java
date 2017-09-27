package com.dev.ramon.sqlitecrud.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dev.ramon.sqlitecrud.R;
import com.dev.ramon.sqlitecrud.objects.Course;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CourseDetailActivity extends AppCompatActivity {
    private Course course;
    private AlertDialog alert;
    TextView tvDescription;
    TextView tvClassHours;
    TextView tvStatus;
    TextView tvRegisterDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvClassHours = (TextView) findViewById(R.id.tvClassHours);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvRegisterDate = (TextView) findViewById(R.id.tvRegisterDate);

        getValuesExtras(getIntent().getExtras());
        toolbar.setTitle(course.getName());

        tvDescription.setText(course.getDescription());
        tvClassHours.setText(course.getClassHours()+"h");
        tvStatus.setText(course.getStatus()==true ? "Ativo" : "Inativo");
        tvRegisterDate.setText(course.getRegisterDate());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getValuesExtras(Bundle extras) {
        course = extras.getParcelable("course");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            showDialog();
            return true;
        }
        if (id == R.id.action_edit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Deseja realmente excluir esse curso?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                //
            }
        });
        alert = builder.create();
        alert.show();
        alert.getButton(alert.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
        alert.getButton(alert.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }
}
