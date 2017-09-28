package com.dev.ramon.sqlitecrud.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dev.ramon.sqlitecrud.helpers.BDSQLiteHelper;
import com.dev.ramon.sqlitecrud.R;
import com.dev.ramon.sqlitecrud.adapters.CourseRecycleViewAdapter;
import com.dev.ramon.sqlitecrud.objects.Course;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvCourses;
    BDSQLiteHelper dBHelper;
    String orderBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orderBy = "DESC";
        rvCourses = (RecyclerView) findViewById(R.id.rvCourses);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PersistCourseActivity.class);
                i.putExtra("course", new Course());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
            return true;
        }

        if(id == R.id.action_list){
            if(orderBy=="DESC"){
                orderBy = "ASC";
            }else{
                orderBy = "DESC";
            }
            addListeners();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addListeners() {
        rvCourses.setHasFixedSize(true);
        rvCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCourses.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvCourses.setAdapter(new CourseRecycleViewAdapter(getAllCourses(orderBy), new CourseRecycleViewAdapter.OnClickListener() {
            @Override
            public void onClickItemList(Course course, int position) {
                Intent i = new Intent(MainActivity.this, CourseDetailActivity.class);
                i.putExtra("course", course);
                startActivity(i);
            }
        }));
    }

    private ArrayList<Course> getAllCourses(String orderBy) {
        dBHelper = new BDSQLiteHelper(this);
        ArrayList<Course> list = new ArrayList<>();
        try {
            for (Course course : dBHelper.getCourses(orderBy)) {
                list.add(course);
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return list;
    }
}
