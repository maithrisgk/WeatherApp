package com.maithri.myproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyLocations extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> citylist;
    CityDB cityDB;
    SQLiteDatabase sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_locations);
        listView=(ListView)findViewById(R.id.locationlistview);
        citylist=new ArrayList<>();
        cityDB=new CityDB(this);
        sd=cityDB.getReadableDatabase();
        String selSQL="select * from "+CityDB.TABLE;
        Cursor cursor=sd.rawQuery(selSQL,null);

        while (cursor.moveToNext())
        {
            String cl=cursor.getString(0);
            citylist.add(cl);
            adapter =new ArrayAdapter<String>(this,R.layout.item_locationlist,citylist);
            listView.setAdapter(adapter);
        }
        if (!cursor.moveToNext())
        {
            Toast.makeText(MyLocations.this,"Go to Add City tab to add your city",Toast.LENGTH_LONG).show();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t=(TextView)findViewById(R.id.tv1);
                String xyz=citylist.get(position);
                Toast.makeText(MyLocations.this,"item selected:"+ xyz,Toast.LENGTH_LONG).show();

                Intent i=new Intent(MyLocations.this,Weather_Check.class);
                i.putExtra("cityname",xyz);
                startActivity(i);
            }
        });
    }
}
