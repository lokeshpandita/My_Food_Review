package com.food.myfoodreview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    EditText ed1,ed2,ed3,ed4;
    String userName,email,pwd,cnfPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed1 = (EditText)findViewById(R.id.ed1);
        ed2 = (EditText)findViewById(R.id.ed2);
        ed3 = (EditText)findViewById(R.id.ed3);
        ed4 = (EditText)findViewById(R.id.ed4);
        //initialise sharedpref
        pref = getApplicationContext().getSharedPreferences("MyPref",0);
        editor = pref.edit();

    }

    public void register(View view) {
        userName = ed1.getText().toString();
        email = ed2.getText().toString();
        pwd = ed3.getText().toString();
        cnfPwd = ed4.getText().toString();
        // check user if user enter detail
        if (userName.isEmpty()){
            ed1.setError("Field should not be empty");
            ed1.requestFocus();
            return;
        }
        else if (email.isEmpty()){
            ed2.setError("Field should not be empty");
            ed2.requestFocus();
            return;
        }
        else if (pwd.isEmpty()){
            ed3.setError("Field should not be empty");
            ed3.requestFocus();
            return;
        }
        else if (cnfPwd.isEmpty()){
            ed4.setError("Field should not be empty");
            ed4.requestFocus();
            return;
        }
        // check if pwd matched to cnfPwd
        if (!pwd.equals(cnfPwd)){
            ed4.setError("Password not matched");
            ed4.requestFocus();
            return;
        }
        // save data in sharedPref
        editor.putString("userName",userName);
        editor.putString("email",email);
        editor.putString("password",pwd);
        editor.commit();

        Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show();
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void login(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }
}