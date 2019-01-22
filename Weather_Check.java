package com.maithri.myproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Weather_Check extends AppCompatActivity {

    final static String API_KEY = "16b1c6b249b4a1bd4e5dc8c31dfebef1";

    EditText text;
    //TextView t=(TextView)findViewById(R.id.textView2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather__check);

        text=(EditText)findViewById(R.id.editText);
        Bundle extra=getIntent().getExtras();
        if(extra!=null) {
            String citynameis = extra.getString("cityname");
            Toast.makeText(Weather_Check.this, "received value from mylocation" + citynameis, Toast.LENGTH_LONG).show();
            text.setText(citynameis);
        }
    }

    public void showweather(View v)
    {

        String search=text.getText().toString();
        SearchWeather as=new SearchWeather(search);
        as.execute();
    }
    class SearchWeather extends AsyncTask<Object,Object,ArrayList>
    {
        String search=null;
        final String URL="http://api.openweathermap.org/data/2.5/weather?q=";
        public SearchWeather(String search)
        {
            this.search=search;
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            ListView listView=(ListView)findViewById(R.id.listView);
            ArrayAdapter adapter=new ArrayAdapter(Weather_Check.this,android.R.layout.simple_list_item_1,result);
            listView.setAdapter(adapter);


            super.onPostExecute(result);
        }

        @Override
        protected ArrayList doInBackground(Object... params) {
            InputStream stream=null;
            try
            {

                StringBuilder stringBuilder=new StringBuilder();
                stringBuilder.append(URL);

                stringBuilder.append(search);
                stringBuilder.append("&appid="+API_KEY);

                URL url=new URL(stringBuilder.toString());
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.connect();
                stream=conn.getInputStream();

                return parseResult(stringify(stream));


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private ArrayList parseResult(String result)
    {

        String streamAsString = result;

        ArrayList results=new ArrayList();

        try
        {

            JSONObject jsonObject=new JSONObject(streamAsString);
            JSONArray array=jsonObject.getJSONArray("weather");

            JSONObject jsonweatherobj=jsonObject.getJSONObject("main");
            Double temperature=jsonweatherobj.getDouble("temp");



           // results.add("Temperature Kelvin:"+temperature);

            double c = temperature - 273.16;

            results.add("Temperature in Celsius:    "+(float)c);

            double f=(((temperature - 273) * 9/5) + 32);

            results.add("Temperature Fahrenheit:"+(float)f);

            results.add("City :      "+jsonObject.get("name"));
            results.add("Description:      "+array.getJSONObject(0).get("description"));
            //t.setText(results.indexOf(jsonObject));

        } catch (Exception e) {

            System.err.println(e);
            Log.d("The City","ERROR PARSING THE JSON .. STRING WAS:::"+streamAsString);

        }

        return results;
    }
    public String stringify(InputStream stream) throws IOException,UnsupportedEncodingException
    {
        Reader reader=null;
        reader=new InputStreamReader(stream,"UTF-8");
        BufferedReader bufferedReader=new BufferedReader(reader);
        return bufferedReader.readLine();
    }
}




