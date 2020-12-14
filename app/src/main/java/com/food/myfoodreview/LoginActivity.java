package com.food.myfoodreview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    EditText ed1,ed2,ed3,ed4;
    String email,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed1 = (EditText)findViewById(R.id.ed1);
        ed2 = (EditText)findViewById(R.id.ed2);
        // initialize sharedPref
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        // check if user already login
        String status = pref.getString("login","");
        if (status.equalsIgnoreCase("true")){
            // redirect user to main screen
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    public void login(View view) {
        email = ed1.getText().toString();
        pwd = ed2.getText().toString();

        // check if user enter data
        if (email.isEmpty()){
            ed1.setError("Field should not be empty");
            ed1.requestFocus();
            return;
        }
        else if (pwd.isEmpty()){
            ed2.setError("Field should not be empty");
            ed2.requestFocus();
            return;
        }
        // retrieve saved data from sharedPref
        String dbEmail = pref.getString("email","");
        String dbPwd = pref.getString("password","");
        // verify to login authenticate
        if (email.equals(dbEmail) && pwd.equals(dbPwd)){
            // login success redirect user to main screen
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        else {
            // login failed alert user
            Toast.makeText(this,"Username or password is incorrect",Toast.LENGTH_SHORT).show();
        }
    }
            // If user presses register
    public void register(View view) {
        startActivity(new Intent(this,RegisterActivity.class));
    }
}