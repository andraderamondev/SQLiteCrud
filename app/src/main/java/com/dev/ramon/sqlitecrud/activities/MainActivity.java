package com.dev.ramon.sqlitecrud.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
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
    Toolbar toolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("ordem", MODE_PRIVATE);
        orderBy = prefs.getString("orderBy", null);
        if (orderBy == null){
            orderBy = "DESC";
        }
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
            SharedPreferences sharedPreferences = getSharedPreferences("ordem", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if(orderBy=="DESC"){
                orderBy = "ASC";
            }else{
                orderBy = "DESC";
            }
            editor.putString("orderBy", orderBy);
            editor.commit();
            addListeners();
            return true;
        }

        if(id == R.id.action_search){
            handleMenuSearch();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }

    private void addListeners() {
        rvCourses.setHasFixedSize(true);
        rvCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar
        if(isSearchOpened){ //test if the search is open
            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar
            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.search_vector));
            isSearchOpened = false;
        } else { //open the search entry
            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title
            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor
            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch(edtSeach.getText().toString());
                        return true;
                    }
                    return false;
                }
            });
            edtSeach.requestFocus();
            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);
            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.search_vector));
            isSearchOpened = true;
        }
    }

    private void doSearch(String like) {
        rvCourses.setHasFixedSize(true);
        rvCourses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dBHelper = new BDSQLiteHelper(this);

        ArrayList<Course> list = new ArrayList<>();
        try {
            for (Course course : dBHelper.searchCourses(orderBy,like)) {
                list.add(course);
            }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        rvCourses.setAdapter(new CourseRecycleViewAdapter(list, new CourseRecycleViewAdapter.OnClickListener() {
            @Override
            public void onClickItemList(Course course, int position) {
                Intent i = new Intent(MainActivity.this, CourseDetailActivity.class);
                i.putExtra("course", course);
                startActivity(i);
            }
        }));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_A: {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
