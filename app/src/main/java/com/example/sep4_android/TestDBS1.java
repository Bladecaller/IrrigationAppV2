package com.example.sep4_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.widget.Toast;

public class TestDBS1 extends AppCompatActivity {

    public Button run;
    public Button regButton;
    public ProgressBar progressBar;
    public EditText username;
    public EditText password;

    public Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dbs1);

        run = findViewById(R.id.logInButton1);
        regButton = findViewById(R.id.regButton);
        progressBar = findViewById(R.id.progressBar);
        username = findViewById(R.id.usernameText);
        password = findViewById(R.id.pwText);



        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestDBS1.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
    }
            /*
            protected String doInBackground(String... params)
            {
                try{
                    con = ConnectionClass();
                    if (con == null)
                    {
                        z = "Check INTERNET";
                    }
                    else
                    {

                        /*
                        Statement create = con.createStatement();
                        System.out.println("Inserting records into the table...");
                        String sql1 = "INSERT INTO USERS(id, username, password) VALUES (2, 'Cata', 'Easy')";
                        create.executeUpdate(sql1);
                        System.out.println("Inserted records into the table...");
                        */
/*
                        String query = "select * from Users WHERE id = 2";
                        Statement statement = con.createStatement();
                        ResultSet rs = statement.executeQuery(query);
                        if (rs.next()) {
                            name1 = rs.getString("username");
                            z = "query successful";
                            isSuccesss = true;
                            con.close();
                        }
                        else
                        {
                            z = "Invalid Query";
                            isSuccesss = false;
                        }
                    }
                }
                catch (SQLException ex)
                {
                    isSuccesss = false;
                    z = ex.getMessage() +" "+ ex.getLocalizedMessage() +" "+ ex.getStackTrace();


                    Log.d("sql error", z);
                }
                return z;
            }
        }

 */

            public class CheckLogin extends AsyncTask<String, String, String>{

                String z = null;
                Boolean isSuccess = false;


                @Override
                protected void onPreExecute() {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                protected void onPostExecute(String s) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                protected String doInBackground(String... strings) {
                    con = connectionClass(ConnectionClass.un.toString(),ConnectionClass.pass.toString(),ConnectionClass.db.toString(),ConnectionClass.ip.toString());
                    if(con == null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TestDBS1.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
                            }
                        });
                        z = "On Internet Connection";
                    }
                    else {
                        try {
                            String sql = "SELECT * FROM Users WHERE username = '" + username.getText() + "' AND password = '" + password.getText() + "' ";
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(sql);

                            if (rs.next()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TestDBS1.this, "Login Success", Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(getApplicationContext(), Main123Activity.class);
                                        i.putExtra("username", String.valueOf(username.getText()));
                                        startActivity(i);
                                    }

                                });
                                z = "Success";



                                Intent intent = new Intent(TestDBS1.this, Main123Activity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TestDBS1.this, "Check email or password", Toast.LENGTH_LONG).show();
                                    }
                                });

                                username.setText("");
                                password.setText("");
                            }
                        } catch (Exception e) {
                            isSuccess = false;
                            Log.e("SQL Error : ", e.getMessage());
                        }
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

