package com.food.myfoodreview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 101 ;
    SearchView searchView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ImageView img;
    EditText ed1,ed2,ed3,ed4;
    Spinner spinner;
    ArrayAdapter adapter;
    ArrayList<String>foodNameList = new ArrayList<>();
    String date,carbohydrate,protein,fat,foodName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.sv);
        img = (ImageView)findViewById(R.id.img);

        ed1 = (EditText)findViewById(R.id.ed1);
        ed2 = (EditText)findViewById(R.id.ed2);
        ed3 = (EditText)findViewById(R.id.ed3);
        spinner = (Spinner)findViewById(R.id.spn);
        // initialize sharedPref
        pref = getApplicationContext().getSharedPreferences("MyPref",0);
        editor = pref.edit();
        // save login status
        editor.putString("login","true");
        editor.commit();
        addSpinnerData();
        spinner.setAdapter(adapter);
        getDate();
        // add search view listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchData = query;
                Intent n = new Intent(getApplicationContext(), BrowserActivity.class);
                n.putExtra("search", searchData);
                startActivity(n);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new code
                if (checkPermission()){
                    Intent c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(c, PERMISSION_REQUEST_CODE);
                }
                else {
                    requestPermission();
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position>0){
                    foodName = foodNameList.get(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Toast.makeText(this,"",Toast.LENGTH_LONG).show();
    }
    // create options menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.log:
                editor.putString("login","false").commit();
                finish();
                break;
            case R.id.lst:
                startActivity(new Intent(this,ListActivity.class));
        }
        return true;
    }
    public  void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

              Bitmap imageData = (Bitmap) data.getExtras().get("data");
                 Uri imgUri = data.getData();
                    if (imgUri!=null){
                        img.setImageURI(imgUri);
                    }
                    else {
                        img.setImageBitmap(imageData);
                    }
            }
        }
    }

    public void saveData(View view) {
        carbohydrate = ed1.getText().toString();
        protein = ed2.getText().toString();
        fat = ed3.getText().toString();

        saveDb();
    }
    // save data into database
    public void saveDb(){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        boolean success = dataBaseHelper.insertDetail(date,carbohydrate,protein,fat,foodName);
        if (success){
            Toast.makeText(this,"Saved into database",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"failed",Toast.LENGTH_SHORT).show();
        }
    }
    // add item in spinner
    public void addSpinnerData(){
        foodNameList.add("Select");
        foodNameList.add("BreakFast");
        foodNameList.add("Lunch");
        foodNameList.add("Snack");
        foodNameList.add("Dinner");
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,foodNameList);
    }
    // get current date
    public void getDate(){
        Calendar calendar = Calendar.getInstance();
        //SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        SimpleDateFormat mdformat3 = new SimpleDateFormat("dd/MM/YYYY");
        //SimpleDateFormat mdformat2 = new SimpleDateFormat("hh:mm:ss");
        date= mdformat3.format(calendar.getTime());
        ///tv1.setText(date);
        // arrange date and time

    }
}