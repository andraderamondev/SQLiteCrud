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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.ramon.sqlitecrud.R;
import com.dev.ramon.sqlitecrud.adapters.CourseRecycleViewAdapter;
import com.dev.ramon.sqlitecrud.objects.Course;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvCourses;
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
                Intent i = new Intent(MainActivity.this, AddCourseActivity.class);
                startActivity(i);
            }
        });
        addListeners();
    }

    private void addListeners() {
        rvCourses.setHasFixedSize(true);
        rvCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvCourses.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rvCourses.setAdapter(new CourseRecycleViewAdapter(recuperarListaDeProfessores(), new CourseRecycleViewAdapter.OnClickListener() {
            @Override
            public void onClickItemList(Course course, int position) {
                Intent intent = new Intent(MainActivity.this, CourseDetailActivity.class);
                intent.putExtra("course", course);
                startActivity(intent);
            }
        }));
    }

    private ArrayList<Course> recuperarListaDeProfessores() {
        ArrayList<Course> list = new ArrayList<>();
        Course course;

        for(int i = 1; i <= 10; i++) {
            course = new Course();
            course.setNome("Curso "+i);
            list.add(course);
        }

        return list;
    }
}
