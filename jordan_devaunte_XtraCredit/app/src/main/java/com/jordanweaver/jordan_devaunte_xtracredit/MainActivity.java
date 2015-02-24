package com.jordanweaver.jordan_devaunte_xtracredit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//<<<<<<< HEAD


public class MainActivity extends Activity {


    Spinner weatherSpinner;
    //Context mContext;
    Context mContext;
    LinearLayout currentWeatherView;
    LinearLayout hourlyWeatherView;
    LinearLayout tenDayWeatherView;
    TextView locationText;
    TextView currentCondition;
    TextView degrees;
    ListView hourlyListView;
    ArrayList<weatherClass> myWeatherClass = new ArrayList<weatherClass>();
    // 2nd Page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        weatherSpinner = (Spinner) findViewById(R.id.weatherSpinner);
        currentWeatherView = (LinearLayout) findViewById(R.id.currentWeatherView);
        hourlyWeatherView = (LinearLayout) findViewById(R.id.hourlyWeatherView);
        tenDayWeatherView = (LinearLayout) findViewById(R.id.tenDayWeatherView);
        locationText = (TextView) findViewById(R.id.cityLabel);
        currentCondition = (TextView) findViewById(R.id.conditionLabel);
        degrees = (TextView) findViewById(R.id.degreesLabel);
        hourlyListView = (ListView) findViewById(R.id.hourlyList);
        mContext = this;


        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                mContext,
                R.array.spinner_stuff,
                android.R.layout.simple_dropdown_item_1line
        );

        weatherSpinner.setAdapter(spinnerAdapter);

         weatherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 switch (position){
                     case 0:
                         currentWeatherView.setVisibility(View.VISIBLE);
                         hourlyWeatherView.setVisibility(View.GONE);
                         tenDayWeatherView.setVisibility(View.GONE);
                         break;
                     case 1:

                         hourlyWeatherView.setVisibility(View.VISIBLE);
                         currentWeatherView.setVisibility(View.GONE);
                         tenDayWeatherView.setVisibility(View.GONE);
                         hourlyAdapter myAdapter = new hourlyAdapter(mContext, myWeatherClass);
                         hourlyListView.setAdapter(myAdapter);

                         break;
                     case 2:
                         tenDayWeatherView.setVisibility(View.VISIBLE);
                         currentWeatherView.setVisibility(View.GONE);
                         hourlyWeatherView.setVisibility(View.GONE);
                 }


             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });



        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                MyTask myTask = new MyTask();

                myTask.execute("http://api.wunderground.com/api/4bfff3e6246abd8a/conditions/forecast10day/hourly/settings/q/FL/orlando.json");

            } else {
                Toast.makeText(MainActivity.this, "Internet connection unavailable", Toast.LENGTH_LONG).show();
            }
        }
    }



    public class MyTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading Data");
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Please wait while we load your data");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {

                URL url = new URL(params[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                result = IOUtils.toString(is);
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                result = "Error";

            } catch (IOException e) {
                e.printStackTrace();
                result = "Error";
            }

            return result;
        }


        @Override
        protected void onPostExecute(String s) {


            super.onPostExecute(s);

            if (s.equals("Error")) {

                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT);

            }
            else {
                try {
                    JSONObject mainObject = new JSONObject(s);

                    // TODO Log current conditions and  the temperature location.


                    Log.e("Json array", "Children array");
                    JSONObject theweatherObject = mainObject.getJSONObject("current_observation");
                    JSONObject weatherObject = mainObject.getJSONObject("current_observation").getJSONObject("display_location");

                    // TODO Log
                    Log.e("Going to for loop", "For loop");


                    // First Screen
                        String location;

                        String condition;
                        String temperature;







                    //
                        // TODO Log
                        if (weatherObject.has("full")) {
                            location = weatherObject.getString("full");
                            locationText.setText(location);
                            Log.e("City", location+"");

                        } else {
                            location = "N/A";
                            Log.e("City", location+"");
                        }
                    if (theweatherObject.has("feelslike_f")) {
                        temperature = theweatherObject.getString("feelslike_f") + " F";
                        degrees.setText(temperature);


                            Log.e("City", temperature+"");

                        } else {
                            temperature = "N/A";
                            Log.e("City", temperature+"");
                        }

                        if (theweatherObject.has("weather")) {
                            condition = theweatherObject.getString("weather");
                            currentCondition.setText(condition);
                        } else {
                            condition = "N/A";
                            Log.e("City", condition+"");

                        }
                    //Second screen is an hourly forecast shown in a list.


                    JSONArray theHourlyweatherArray = mainObject.getJSONArray("hourly_forecast");


                    // TODO Log
                    Log.e("Going to for loop", "For loop");
                    for (int i = 0; i < theHourlyweatherArray.length(); i++) {
                        JSONObject temp = theHourlyweatherArray.getJSONObject(i).getJSONObject("temp");


                        String hour = null;
                        String high;
                        String Low;
                        String hourlyCondition;

                        JSONObject Objects = theHourlyweatherArray.getJSONObject(i);
                        // TODO Log
                         Log.e("I AM IN THE FOR LOOP");

                        if (temp.has("english")) {

                            high = temp.getString("english");


                        } else {
                            high = "N/A";
                            Log.i("high ", "............ high:" + high);
                        }
                        if (temp.has("dewpoint")) {
                            Low = temp.getJSONArray("english").toString();
                            Log.i("Low", "............ Low:" + Low);
//                            authorsName = childObject.getString("authors");

                        } else {
                            Low = "N/A";
                            Log.i("Low:", "............ Low:" + Low);
                        }

                        if (Objects.has("condition")) {
                            hourlyCondition = Objects.getString("condition");
                        } else {
                            hourlyCondition = "N/A";
                            Log.i("hourly Conditon", "............ hourly Conditon:" + hourlyCondition);

                        }

                        if (Objects.getJSONObject("FCTTIME").has("civil")) {
                            hour = Objects.getString("civil");

                        }

                        myWeatherClass.add(new weatherClass(high,Low,hour,hourlyCondition));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




                dialog.cancel();
            }


        }



    }


}
