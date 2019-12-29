package com.app.roomcache;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import Adapters.UserAdapter;
import Model.User;
import Room.UserRepository;
import ViewModels.UserViewModel;

public class MainActivity extends AppCompatActivity {

    UserRepository userRepository;
    RecyclerView recyclerView;
    UserViewModel userModel;
    List<User> userList;
    UserAdapter adapter;
    ProgressBar prg;
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prg = findViewById(R.id.prg);
        refresh = findViewById(R.id.refresh);

        refresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        userRepository = new UserRepository(this);
        userModel = ViewModelProviders.of(MainActivity.this).get(UserViewModel.class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();

        adapter = new UserAdapter(userList,this);
        recyclerView.setAdapter(adapter);


        userModel.getCheckDb().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                if(users.size() == 0){
                    userRepository.getUserList();
                }
            }
        });

        userModel.getListLiveData().observe(this, new Observer<List<User>>() {

            @Override
            public void onChanged(List<User> users) {

                prg.setVisibility(View.INVISIBLE);
                adapter.setUserList(users);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AddUser.class);
                startActivity(i);
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                userRepository.getUserList();
                refresh.setRefreshing(false);
            }
        });


      //  userRepository.getUserList();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
