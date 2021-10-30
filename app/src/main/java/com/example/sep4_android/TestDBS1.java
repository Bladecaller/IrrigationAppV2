package com.example.sep4_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sep4_android.R;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.os.Bundle;
import android.widget.Toast;

public class TestDBS1 extends AppCompatActivity {

    public Button run;
    public TextView message;
    public ProgressBar progressBar;

    public Connection con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dbs1);

        run = (Button) findViewById(R.id.logInButton1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        run.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
    }
    public class CheckLogin extends AsyncTask<String,String,String>
    {


            String z ="";
            Boolean isSuccesss = false;
            String name1 = "";

            protected void onPreExecute()
            {
                progressBar.setVisibility(View.VISIBLE);
            }

            protected void onPostExecute(String r)
            {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(TestDBS1.this, r, Toast.LENGTH_LONG).show();
                if(isSuccesss)
                {
                    message = (TextView) findViewById(R.id.textView12);
                    message.setText(name1);
                }
            }
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
        @SuppressLint("NewApi")
        public Connection ConnectionClass()
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
