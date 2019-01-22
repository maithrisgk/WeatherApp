package com.maithri.myproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCity extends AppCompatActivity {
    //public static final String DB="DBCity";
    CityDB cityDB;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        cityDB=new CityDB(this);
        database=cityDB.getWritableDatabase();



    }

    public void saveCity(View view) {
        EditText cityname=(EditText)findViewById(R.id.addthiscity);
        String userenter=cityname.getText().toString();

        ContentValues values=new ContentValues();
        values.put("cityname",userenter);
        database.insert("Cities",null,values);

        Toast.makeText(AddCity.this,"City added: "+userenter,Toast.LENGTH_LONG).show();

    }
}


