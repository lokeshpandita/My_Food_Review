package com.food.myfoodreview;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String>list = new ArrayList<>();
    ArrayAdapter<String>adapter;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lv = (ListView)findViewById(R.id.lv);
        db = new DataBaseHelper(this);
        readDb();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);
    }
    public void readDb(){
        Cursor res=db.getAllData();
        StringBuffer stbr=new StringBuffer();
        if(res!=null && res.getCount()>0){
            while(res.moveToNext()) {
                stbr.append("DATE: " + res.getString(1) + "\n");
                stbr.append("FOOD NAME: " + res.getString(5) + "\n");
                stbr.append("CARBOHYDRATE: " + res.getString(2) + "\n");
                stbr.append("PROTEIN: " + res.getString(3) + "\n");
                stbr.append("FAT: " + res.getString(4) + "\n");

                // get caloric value and sum it
                String carbohydrate = res.getString(2).toString();
                String protein = res.getString(3).toString();
                String fat = res.getString(4).toString();
                int total = Integer.parseInt(carbohydrate) + Integer.parseInt(protein) + Integer.parseInt(fat);
                String data = stbr.toString();
                data = data + "TOTAL: " + Integer.toString(total);
                list.add(data);
                stbr.delete(0, stbr.length());

            }
        }
    }
}