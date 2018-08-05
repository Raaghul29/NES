package com.nes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StudentHomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView parent_name,parent_email,student_name;

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Menu menu;

    private String fatherName;
    private String dadEmail;
    private String studentName;
    private String std_className;
    private String stdGrn;
    private String fees;
    private String[] duefee;
    private String duefees;
    private String paidStatus;
    private String[] allfee;
    private String download;
    private String stuid;

    TextView stdent_name,grn_no,classname,DOB,Gender,DOJ,blood_grp;

    private CardView profile_view;
    private CardView fee_details;
    private CardView std_Attendance;
    private CardView std_Result;
    private CardView std_Gallery;
    private CardView std_Diary;
    private CardView alerts;
    private CardView class_circular ;
    private CardView school_circular ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_screen);

        stdent_name = (TextView)findViewById(R.id.stdent_name);
        grn_no = (TextView)findViewById(R.id.grn_no);
        classname = (TextView)findViewById(R.id.classname);
        DOB = (TextView)findViewById(R.id.DOB);
        Gender = (TextView)findViewById(R.id.Gender);
        DOJ = (TextView)findViewById(R.id.DOJ);
        blood_grp = (TextView)findViewById(R.id.blood_grp);

        studentName = getIntent().getExtras().getString("studentName");
        std_className = getIntent().getExtras().getString("std_classname");
        fatherName = getIntent().getExtras().getString("fatherName");
        stdGrn = getIntent().getExtras().getString("stdGrn");
        fees = getIntent().getExtras().getString("fees");
        duefee = getIntent().getExtras().getStringArray("Duefee");
        for (int l=0;l<duefee.length;l++)
            System.out.println("Due fee in Home : "+duefee[l]);
        duefees = getIntent().getExtras().getString("Duefees");
        paidStatus = getIntent().getExtras().getString("paidStatus");
        allfee = getIntent().getExtras().getStringArray("allfee");
        download = getIntent().getExtras().getString("download");
        dadEmail = getIntent().getExtras().getString("dadEmail");
        stuid = getIntent().getExtras().getString("stuid");

        Toolbar toolbar = findViewById(R.id.toolbar);
        nv = (NavigationView)findViewById(R.id.nav_my_profile_parent);
        nv.setNavigationItemSelectedListener(StudentHomeScreen.this);
        View header = nv.getHeaderView(0);
        parent_name = (TextView)header.findViewById(R.id.Parent_name);
        parent_email = (TextView)header.findViewById(R.id.parent_email);
        parent_name.setText(fatherName);
        parent_email.setText(dadEmail);
        menu = nv.getMenu();
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar, R.string.Open, R.string.Close){

            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View v){
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }

        };
        mDrawerLayout.addDrawerListener(mToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToggle.syncState();

        student_name = (TextView)findViewById(R.id.std_Name);
        student_name.setText(studentName);

        profile_view = (CardView)findViewById(R.id.profile_student);
        fee_details = (CardView)findViewById(R.id.view_Fees);
        std_Attendance = (CardView)findViewById(R.id.Attendance);
        std_Result = (CardView)findViewById(R.id.result);
        std_Gallery = (CardView)findViewById(R.id.Gallery);
        std_Diary = (CardView)findViewById(R.id.Std_Diary);
        alerts = (CardView)findViewById(R.id.Alerts);
        class_circular = (CardView)findViewById(R.id.Class_Circular);
        school_circular = (CardView)findViewById(R.id.School_Circular);

        profile_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                STudentProfileViewDao dao = new STudentProfileViewDao();
                dao.execute(stuid);
            }
        });

        fee_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<FeesBase> feeDispList =  FeeStructure.calculate(allfee,duefee);
                Intent i = new Intent(StudentHomeScreen.this,FeeDetails.class);
                i.putExtra("FatherName",fatherName);
                i.putExtra("dadEmail",dadEmail);
                Bundle args = new Bundle();
                args.putSerializable("feeDispList",(Serializable)feeDispList);
                i.putExtra("Bundle",args);
                startActivity(i);
            }
        });

        std_Attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeScreen.this,StudentAttendance.class);
                i.putExtra("FatherName",fatherName);
                i.putExtra("dadEmail",dadEmail);
                startActivity(i);
            }
        });

        std_Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeScreen.this,StudentResult.class);
                i.putExtra("FatherName",fatherName);
                i.putExtra("dadEmail",dadEmail);
                startActivity(i);
            }
        });

        std_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeScreen.this,StudentGallery.class);
                i.putExtra("FatherName",fatherName);
                i.putExtra("dadEmail",dadEmail);
                startActivity(i);
            }
        });

        std_Diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeScreen.this,StudentDiary.class);
                i.putExtra("FatherName",fatherName);
                i.putExtra("dadEmail",dadEmail);
                startActivity(i);
            }
        });

        alerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeScreen.this,AlertsFromSchool.class);
                i.putExtra("FatherName",fatherName);
                i.putExtra("dadEmail",dadEmail);
                startActivity(i);
            }
        });

        class_circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeScreen.this,ClassCircular.class);
                i.putExtra("FatherName",fatherName);
                i.putExtra("dadEmail",dadEmail);
                startActivity(i);
            }
        });

        school_circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentHomeScreen.this,SchoolCircular.class);
                i.putExtra("FatherName",fatherName);
                i.putExtra("dadEmail",dadEmail);
                startActivity(i);
            }
        });



    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("Items ",Integer.toString(item.getItemId()));
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(nv)) {
                mDrawerLayout.closeDrawer(nv);
            } else {
                mDrawerLayout.openDrawer(nv);
            }
            return true;
        }
        if(item.getItemId()!=android.R.id.home){
            String item_nav = (String) item.getTitle();
            Log.d("item_nav",item_nav);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if(id == R.id.nav_my_profile){
            Snackbar.make(mDrawerLayout,"My Profile",Snackbar.LENGTH_SHORT).show();
            Intent i = new Intent(this,ParentProfileView.class);
            startActivity(i);
        }
        if (id == R.id.nav_settings){
            Snackbar.make(mDrawerLayout,"Settings yet to be implemented",Snackbar.LENGTH_SHORT).show();
        }
        if (id == R.id.nav_logout){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            //set title
            alertDialogBuilder.setTitle("Logout");
            //set message
            alertDialogBuilder.setMessage("Confirm Logout?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(StudentHomeScreen.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    StudentHomeScreen.this.finish();
                    Snackbar.make(mDrawerLayout,"Logout Successful",Snackbar.LENGTH_SHORT).show();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            //create Dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            //shot it
            alertDialog.show();

        }

        return false;
    }

    private class STudentProfileViewDao extends AsyncTask<String,Void,Students> {

        @Override
        protected Students doInBackground(String... strings) {

            String stuid = strings[0];

            OkHttpClient client = new OkHttpClient();

            try {
                URL urlServer = new URL("http://rypse.co.in/nes_dev/");
                HttpURLConnection urlConn = (HttpURLConnection) urlServer.openConnection();
                urlConn.setConnectTimeout(3000); //<- 3Seconds Timeout
                urlConn.connect();
                Log.d("Connection status", Integer.toString(urlConn.getResponseCode()));
                if (urlConn.getResponseCode() == 200) {
                    String url = "http://rypse.co.in/nes_mob/stuview.php?stuid="+stuid;

                    Request request = new Request.Builder()
                            .url(url).build();
                    Response response = client.newCall(request).execute();


                    JSONObject object = new JSONObject(response.body().string());

                    Students std = new Students();

                    std.setConnectionStatus("Connection Success");
                    std.setGRNo(object.getString("GRNo"));
                    std.setStdName(object.getString("StdName"));
                    std.setNewAdFlag(object.getString("newAdFlag"));
                    std.setBg(object.getString("bg"));
                    std.setDob(object.getString("dob"));
                    std.setDoj(object.getString("doj"));
                    std.setDor(object.getString("dor"));
                    std.setGen(object.getString("gen"));
                    std.setSecName(object.getString("secName"));
                    std.setClsName(object.getString("clsName"));

                    Log.d("Student", std.toString());

                    return std;
                }
                else {
                    Students std = new Students();
                    std.setConnectionStatus("Connection Failed");
                    return std;
                }
            }catch (UnknownHostException e){
                Log.d("Exception at IOE","");
                e.printStackTrace();
                Students std = new Students();
                std.setConnectionStatus("Connection Failed");
                return std;
            }
            catch (MalformedURLException e) {
                Log.d("Exception at IOE","");
                e.printStackTrace();
                Students std = new Students();
                std.setConnectionStatus("Connection Failed");
                return std;
            } catch (JSONException e) {
                Log.d("Exception at JSONE","");
                e.printStackTrace();
                Students std = new Students();
                std.setConnectionStatus("Connection Failed");
                return std;
            }  catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Students std) {

            Intent i = new Intent(StudentHomeScreen.this,StudentProfileView.class);

            if (std!=null){
                if (!std.getConnectionStatus().equals("Connection Failed")) {


                    Log.d("Name Set", "");
                    i.putExtra("stdent_name", std.getStdName());


                    Log.d("GRN Set", "");
                    i.putExtra("grn_no", std.getGRNo());


                    Log.d("Class Set", "");
                    i.putExtra("classname", std.getClsName() + " " + std.getSecName());


                    Log.d("Class Set", "");
                    i.putExtra("classname", std.getClsName());


                    Log.d("DOB Set", "");
                    i.putExtra("DOB", std.getDob());


                    i.putExtra("Gender", std.getGen());

                    i.putExtra("Gender", std.getGen());

                    Log.d("Gender Set", "");


                    Log.d("DOJ Set", "");
                    i.putExtra("DOJ", std.getDoj());


                    Log.d("Bld_grp Set", "");
                    i.putExtra("blood_grp", std.getBg());

                    startActivity(i);

                }else{
                    ConnectionDetector connectionDetector =new ConnectionDetector(StudentHomeScreen.this);
                    connectionDetector.ConnectionIssue();
                }
            }else{
                ConnectionDetector connectionDetector =new ConnectionDetector(StudentHomeScreen.this);
                connectionDetector.ConnectionIssue();
            }
        }
    }

}
