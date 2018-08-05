package com.nes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    private RelativeLayout login_layout;
    private TextInputLayout username;
    private TextInputLayout password;
    private FloatingActionButton btn_login;
    String successmsg ="";
    ConnectionDetector connectionDetector;
    private boolean connectionCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectionDetector = new ConnectionDetector(this);

        btn_login = findViewById(R.id.login);
        login_layout = (RelativeLayout)findViewById(R.id.Login_Layout);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conformInput(view);
            }
        });

    }

    public boolean checkConnectivity(){
        if (!connectionDetector.isConnected()){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean validateUsername() {
        String user = username.getEditText().getText().toString();
        Log.v("Username :", user);
        if (user.isEmpty()) {
            username.setError("Please enter username");
            return false;
        } else {
            username.setError("");
            return true;
        }
    }

    public boolean validatePassword() {
        String pass = password.getEditText().getText().toString();
        Log.v("Password : ", pass);
        if (pass.isEmpty()) {
            password.setError("Please enter password");
            return false;
        } else {
            password.setError("");
            return true;
        }
    }


    public void conformInput(View v) {



        if (!validateUsername() | !validatePassword()) {
            btn_login.setEnabled(true);
            return;
        }

        String user = username.getEditText().getText().toString();
        String pass = password.getEditText().getText().toString();
        String url = "http://rypse.co.in/nes_dev/";
        int attempt = 1;
        if (user != null && pass != null) {
            //DBHelperClass db = new DBHelperClass();
            //db.execute(user,pass);
            if (!checkConnectivity()) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("Please Check Internet Connection ")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }else {

                    btn_login.setEnabled(false);
                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Authenticating...");
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    // On complete call either onLoginSuccess or onLoginFailed
                                    Log.d("Successmg in thread", successmsg);
                                    progressDialog.dismiss();
                                    btn_login.setEnabled(true);

                                }
                            }, 2000);

                    BackgroundDbClass dao = new BackgroundDbClass(this);
                    dao.execute("login", user, pass);
            /*loginprogress.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, ParentHomeScreen.class);
            startActivity(i);*/

            }
        }
        else{
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }

    }

    public void registerHere(View view){
            Snackbar.make(login_layout,"Please Check Seat availablity before Register",Snackbar.LENGTH_LONG).show();
    }




    /*private class DBHelperClass extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            loginprogress.setVisibility(loginprogress.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.v("Connection","came here");
            int userId=0;
            Connection con = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(DB_URL,DB_username,DB_password);
                Log.d("Connection","Success");
                if (con == null){
                    return null;
                }
                else{
                    String query = "SELECT UserId from profileuser where LoginName = '"+params[0]+"'";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()){
                        userId = rs.getInt(1);
                        Log.v("UserId",Integer.toString(userId));
                    }
                    con.close();
                }

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            Log.v("UserId",Integer.toString(userId));
            return Integer.toString(userId);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String UserId) {
            Log.v("UserId on post ",UserId);
            if (UserId != null) {
                if(!UserId.equals("0")) {
                    loginprogress.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ParentHomeScreen.class));
                }else  if (UserId.equals("0")){
                    loginprogress.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            } else{
                loginprogress.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
    /*}*/

    private class BackgroundDbClass extends AsyncTask<String, Void, Login_model> {

        Context context;
        BackgroundDbClass(Context ctx) {
            context = ctx;
        }


        @Override
        protected Login_model doInBackground(String... params) {

            String type = params[0];
            String user_name = params[1];
            String pass = params[2];
//            String login_url = "http://10.0.2.2/login.php";
            OkHttpClient client = new OkHttpClient();


            if (type.equals("login")) {
//                try {
//                    URL url = new URL(login_url);
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    httpURLConnection.setRequestMethod("POST");
//                    httpURLConnection.setDoOutput(true);
//                    httpURLConnection.setDoInput(true);
//                    OutputStream outputStream = httpURLConnection.getOutputStream();
//                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//                    String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
//                            + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
//                    bufferedWriter.write(post_data);
//                    bufferedWriter.flush();
//                    bufferedWriter.close();
//                    outputStream.close();
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
//                    String result = "";
//                    String line = "";
//                    while ((line = bufferedReader.readLine()) != null) {
//                        result += line;
//                    }
//                    bufferedReader.close();
//                    inputStream.close();
//                    httpURLConnection.disconnect();
//                    return result;
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                try {
                    URL urlServer = new URL("http://rypse.co.in/nes_dev/");
                    HttpURLConnection urlConn = (HttpURLConnection) urlServer.openConnection();
                    urlConn.setConnectTimeout(3000); //<- 3Seconds Timeout
                    urlConn.connect();
                    Log.d("Connection status", Integer.toString(urlConn.getResponseCode()));
                    if (urlConn.getResponseCode() == 200) {
                        String url = "  http://rypse.co.in/nes_mob/logincheck.php?hdnsub=1&hdnuser=" + user_name + "&hdnpass=" + pass;

                        Request request = new Request.Builder()
                                .url(url).build();
                        Response response = client.newCall(request).execute();


                        JSONObject object = new JSONObject(response.body().string());

                        Login_model login_model = new Login_model();
                        login_model.setStatus("Success");
                        login_model.setConnectionStatus("Connection Success");
                        login_model.setDadEmail(object.getString("dadEmail"));
                        login_model.setDadMob(object.getString("dadMob"));
                        login_model.setMomEmail(object.getString("momEmail"));
                        login_model.setMomMob(object.getString("momMob"));
                        login_model.setParId(object.getString("parId"));
                        login_model.setUserid(object.getString("userid"));
                        login_model.setUsername(object.getString("username"));
                        login_model.setUserType(object.getString("userType"));

                        Log.d("Login_Model", login_model.toString());

                        return login_model;
                    }
                    else {
                        Login_model login_model = new Login_model();
                        login_model.setConnectionStatus("Connection Failed");
                        return login_model;
                    }
                }catch (UnknownHostException e){
                    Log.d("Exception at IOE","");
                    e.printStackTrace();
                    Login_model login_model = new Login_model();
                    login_model.setConnectionStatus("Connection Failed");
                    return login_model;
                }
                catch (MalformedURLException e) {
                    Log.d("Exception at IOE","");
                    e.printStackTrace();
                    Login_model login_model = new Login_model();
                    login_model.setConnectionStatus("Connection Failed");
                    return login_model;
                } catch (JSONException e) {
                    Log.d("Exception at JSONE","");
                    e.printStackTrace();
                    Login_model login_model = new Login_model();
                    login_model.setConnectionStatus("Connection Success");
                    login_model.setStatus("Failure");
                    return login_model;
                }  catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Login_model login_model) {
            if (login_model != null) {
                if (login_model.getParId()!=null && login_model.getStatus().equals("Success") && login_model.getConnectionStatus().equals("Connection Success")) {
                    Log.d("Login", "Success");
                    successmsg = "success";
                    Log.d("Successmg",successmsg);
                    Log.d("username",login_model.getUsername());
                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, ParentHomeScreen.class);
                    i.putExtra("parId",login_model.getParId());
                    i.putExtra("UserId",login_model.getUserid());
                    i.putExtra("username",login_model.getUsername());
                    i.putExtra("dadEmail",login_model.getDadEmail());
                    startActivity(i);
                }
                else if (login_model.getConnectionStatus().equals("Connection Success") && login_model.getStatus().equals("Failure")){
                    Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                    password.setError("Please enter valid Password");
                    username.setError("Please enter valid Username");
                }else if (login_model.getConnectionStatus().equals("Connection Failed")){
                    connectionDetector.ConnectionIssue();
                }
            }else{
                connectionDetector.ConnectionIssue();
            }
        }
    }
}