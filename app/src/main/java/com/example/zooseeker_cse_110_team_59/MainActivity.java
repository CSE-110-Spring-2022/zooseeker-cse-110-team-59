package com.example.zooseeker_cse_110_team_59;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.zooseeker_cse_110_team_59.List.ListActivity;

/**
 * Class:            MainActivity
 * Description:      sets up the onCreate method and  is the entry point of the application
 *
 * Public functions: onCreate          - creates the application for the MainActivity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The onCreate method creates the MainActivity for the application
     * super is used to call the parent class constructor
     * setContentView is used to set the xml
     * @param Bundle is used to save and recover state information in activity
     *
     * @return None
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZooData.setZooData();

        Intent listIntent = new Intent(this, ListActivity.class);
        startActivity(listIntent);
    }
}