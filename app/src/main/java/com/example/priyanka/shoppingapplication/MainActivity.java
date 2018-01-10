package com.example.priyanka.shoppingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.priyanka.shoppingapplication.screens.list.ItemListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, new ItemListFragment())
                .commit();
    }
}
