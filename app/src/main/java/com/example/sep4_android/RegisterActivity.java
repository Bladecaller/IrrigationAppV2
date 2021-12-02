package com.example.sep4_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import android.widget.Toast;

import firebase_sql_helper_classes.ConnectionClass;

public class RegisterActivity extends AppCompatActivity {

    public Button run;
    public ProgressBar progressBar;
    public EditText username;
    public EditText password;
    public EditText email;

    public Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=(EditText) findViewById(R.id.emailText);
        username=(EditText) findViewById(R.id.usernameText);
        password=(EditText) findViewById(R.id.pwText);
        run = (Button) findViewById(R.id.logInButton1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        run.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Registeruser registeruser = new Registeruser();
                registeruser.execute("");
            }
        });

    }

    public class Registeruser extends AsyncTask<String, String , String>{

        String z = "";
        Boolean isSuccess = true;

        boolean isEmpty(EditText text) {
            CharSequence str = text.getText().toString();
            return TextUtils.isEmpty(str);
        }
        boolean isEmail(EditText text) {
            CharSequence email = text.getText().toString();
            return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        }
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        protected String doInBackground(String... strings) {

                try {
                    con = connectionClass(ConnectionClass.un.toString(), ConnectionClass.pass.toString(), ConnectionClass.db.toString(), ConnectionClass.ip.toString());

                    if (!isEmail(email)) {
                        runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Email is not valid", Toast.LENGTH_LONG).show());
                        throw new IOException("Email is not validdd");
                    }
                    if (isEmpty(username)) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_LONG).show());
                        throw new IOException("username is not validdd");
                    }
                    if (isEmpty(password)) {
                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_LONG).show());
                        throw new IOException("password is not validdd");
                    }
                    if (con == null) {
                        z = "Check Your Internet Connection";
                        throw new IOException("Email is not validdd");
                    } else {
                        Statement statement = con.createStatement();
                        String sql = "INSERT INTO Users (email,username,password) VALUES ('" + email.getText() + "','" + username.getText() + "','" + password.getText() + "')";
                        statement.executeUpdate(sql);
                    }
                } catch (SQLException sqlException) {
                    isSuccess = false;
                    z = sqlException.getMessage();
                    Log.e("Database : ", sqlException.getMessage());
                } catch (Exception e) {
                    isSuccess = false;
                    z = e.getMessage();
                }

                return z;
            }
    }

    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = "null";
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://lbsclimateapp.database.windows.net:1433;ssl=require;DatabaseName=ClimateApp;user=sqladmin@lbsclimateapp;password=8xMiJ3frSTuhY2IxZBZCpOOLAMLs7ZB2WHJvHJHEZBPtHJ3gQp;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1: ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error her 2: ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3: ", e.getMessage());
        }
        return connection;
    }
}