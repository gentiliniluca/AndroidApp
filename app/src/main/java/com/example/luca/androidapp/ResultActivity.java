package com.example.luca.androidapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity
{

    Global global;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        global=((Global)getApplicationContext());

        result= (TextView) findViewById(R.id.result_label);
        TextView content_label= (TextView) findViewById(R.id.content_label);
        content_label.setText("Content: ");

        //gestisco il pulsante indietro nella action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        PIGResponse response_from_pig = new PIGResponse(this, global.get_port_result());
        response_from_pig.execute();


    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home: //mi gestisce la freccia indietro nella action bar
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return false;
    }

    public void updateResult(String msg)
    {
        result.setText(msg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
