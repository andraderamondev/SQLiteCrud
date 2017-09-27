package com.dev.ramon.sqlitecrud.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dev.ramon.sqlitecrud.BDSQLiteHelper;
import com.dev.ramon.sqlitecrud.R;
import com.dev.ramon.sqlitecrud.adapters.CourseRecycleViewAdapter;
import com.dev.ramon.sqlitecrud.objects.Course;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvCourses;
    BDSQLiteHelper dBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvCourses = (RecyclerView) findViewById(R.id.rvCourses);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PersistCourseActivity.class);
                startActivity(i);
            }
        });
        addListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addListeners();
    }

    private void addListeners() {
        rvCourses.setHasFixedSize(true);
        rvCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCourses.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvCourses.setAdapter(new CourseRecycleViewAdapter(getAllCourses(), new CourseRecycleViewAdapter.OnClickListener() {
            @Override
            public void onClickItemList(Course course, int position) {
                Intent intent = new Intent(MainActivity.this, CourseDetailActivity.class);
                intent.putExtra("course", course);
                startActivity(intent);
            }
        }));
    }

    private ArrayList<Course> getAllCourses() {
        dBHelper = new BDSQLiteHelper(this);
        ArrayList<Course> list = new ArrayList<>();
        try {
            for (Course course : dBHelper.getCourses()) {
                list.add(course);
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return list;
    }
}
