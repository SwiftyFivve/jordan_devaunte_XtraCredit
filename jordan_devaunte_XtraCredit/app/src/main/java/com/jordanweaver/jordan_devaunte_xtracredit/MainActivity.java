package com.jordanweaver.jordan_devaunte_xtracredit;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity {


    Spinner weatherSpinner;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherSpinner = (Spinner) findViewById(R.id.weatherSpinner);

        mContext = this;


        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                mContext,
                R.array.spinner_stuff,
                android.R.layout.simple_dropdown_item_1line
        );

        weatherSpinner.setAdapter(spinnerAdapter);



    }


}
