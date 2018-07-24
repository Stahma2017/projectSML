package com.example.stas.sml;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {




    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        content = (TextView)findViewById(R.id.text_content);
        BottomNavigationView bottomNavigation = (BottomNavigationView)
        findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_search:
                                content.setText("SEARCH");
                                break;
                            case R.id.action_places:
                                content.setText("PLACES");
                                break;
                            case R.id.action_map:
                                content.setText("MAP");
                                break;
                        }
                        return false;
                    }
                });

    }




}
