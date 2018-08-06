package com.nes;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.nes.R.*;
import static com.nes.R.string.*;

public class ParentHomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    String userId,uname,dadEmail,parId ;

    TextView parent_name,parent_email;

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Menu menu;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<StudentData> studentDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_parent_home_screen);

        userId = getIntent().getExtras().getString("UserId");
        uname = getIntent().getExtras().getString("username");
        dadEmail = getIntent().getExtras().getString("dadEmail");
        parId = getIntent().getExtras().getString("parId");

        Log.d("Transfered details ",userId+" "+uname+" "+dadEmail+" "+parId);

        recyclerView = (RecyclerView)findViewById(id.recycler_view);
        studentDataList = new ArrayList<>();
        load_data_from_server(parId);

        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new CustomAdapter(this,studentDataList);
        recyclerView.setAdapter(adapter);

        
        Toolbar toolbar = findViewById(id.toolbar);
        nv = (NavigationView)findViewById(id.nav_my_profile);
        nv.setNavigationItemSelectedListener(this);
        View header = nv.getHeaderView(0);
        parent_name = (TextView)header.findViewById(id.Parent_name);
        parent_email = (TextView)header.findViewById(id.parent_email);
        parent_name.setText(uname);
        parent_email.setText(dadEmail);
        menu = nv.getMenu();
        mDrawerLayout = (DrawerLayout)findViewById(id.drawer_layout);
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
    }

    private void load_data_from_server(final String parId) {

         @SuppressLint("StaticFieldLeak")
         AsyncTask<String,Void,String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {

                OkHttpClient client = new OkHttpClient();

                try {
                    URL urlServer = new URL("http://rypse.co.in/nes_dev/");
                    HttpURLConnection urlConn = (HttpURLConnection) urlServer.openConnection();
                    urlConn.setConnectTimeout(3000); //<- 3Seconds Timeout
                    urlConn.connect();
                    Log.d("Connection status", Integer.toString(urlConn.getResponseCode()));
                    if (urlConn.getResponseCode() == 200) {
                        Request request = new Request.Builder()
                                .url("http://rypse.co.in/nes_mob/parstulist.php?parIds=" + parId).build();
                        Response response = client.newCall(request).execute();

                        JSONArray array = new JSONArray(response.body().string());

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);

                            StudentData sData = new StudentData();
                            sData.setConnectionStatus("Connection Success");
                            sData.setClassname(object.getString("classname"));
                            sData.setStd_gen(object.getString("gender"));
                            sData.setDuefees(object.getString("duefees"));
                            sData.setFees(object.getString("fees"));
                            sData.setStuid(object.getString("stuid"));
                            sData.setFathername(object.getString("fathername"));
                            sData.setPaidstatus(object.getString("paidstatus"));
                            sData.setStugrn(object.getString("stugrn"));
                            sData.setDadEmail(dadEmail);
                            sData.setStuname(object.getString("stuname"));
                            JSONArray totalfees = object.getJSONArray("allfee");
                            String allfee[] = new String[totalfees.length()];
                            sData.setAllfee(allfee);
                            Log.d("allfee length",Integer.toString(sData.getAllfee().length));
                            for(int j=0;j<sData.getAllfee().length;j++)
                            {
                                allfee[j] = totalfees.getString(j);
                                Log.d("..........",""+allfee[j]);
                                // loop and add it to array or arraylist
                            }

                            JSONArray duefees = object.getJSONArray("duefee");
                            String dufees[] = new String[duefees.length()];

//                            for (int d=0;d<dufees.length;d++)
//                                System.out.println("DuFee "+dufees[d] );

                            sData.setDuefee(dufees);
                            if (duefees.length()>0) {
                               for (int j = 0; j < sData.getDuefee().length; j++) {
                                   dufees[j] = duefees.getString(j);
                                   Log.d("Due fee 1st setting : ", "" + dufees[j]);
                                   // loop and add it to array or arraylist
                               }
                            }

                            studentDataList.add(sData);

                        }
                        return "Connection Success";
                    }else {
                        return "Connection Failed";
                    }

                } catch (UnknownHostException e){
                   e.printStackTrace();
                   return "Connection Failed";
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("JSONException","END OF CONTENT");
                }

                return null;
            }

             @Override
             protected void onPostExecute(String aVoid) {
                if (aVoid != null && aVoid.equals("Connection Success")) {
                    adapter.notifyDataSetChanged();
                }
                else{
                    ConnectionDetector cd = new ConnectionDetector(ParentHomeScreen.this);
                    cd.ConnectionIssue();
                }
            }
         };

         task.execute(userId);

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

    @Override
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if(id == R.id.nav_my_profile){
//             ParentProfileViewDao parentProfileViewDao = new ParentProfileViewDao(ParentHomeScreen.this);
            //Intent i = new Intent(this,ParentProfileView.class);
//            startActivity(i);
//             parentProfileViewDao.getParentProfile(this,parId);
            Dao parentProfileViewDao = new Dao();
            parentProfileViewDao.execute(parId);

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
                    Intent intent = new Intent(ParentHomeScreen.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    ParentHomeScreen.this.finish();
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

    @Override
    public void onBackPressed() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //set title
        alertDialogBuilder.setTitle("Logout");
        //set message
        alertDialogBuilder.setMessage("Confirm Logout?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ParentHomeScreen.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                ParentHomeScreen.this.finish();
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
    
     public class Dao extends AsyncTask<String,Void,Parent> {

        @Override
        protected Parent doInBackground (String...strings){

            {

                String parId = strings[0];

                OkHttpClient client = new OkHttpClient();

                try {
                    URL urlServer = new URL("http://rypse.co.in/nes_dev/");
                    HttpURLConnection urlConn = (HttpURLConnection) urlServer.openConnection();
                    urlConn.setConnectTimeout(3000); //<- 3Seconds Timeout
                    urlConn.connect();
                    Log.d("Connection status", Integer.toString(urlConn.getResponseCode()));
                    if (urlConn.getResponseCode() == 200) {
                        String url = "http://rypse.co.in/nes_mob/pareditpro.php?parIds="+parId;

                        Request request = new Request.Builder()
                                .url(url).build();
                        Response response = client.newCall(request).execute();


                        JSONObject object = new JSONObject(response.body().string());

                        Parent parent = new Parent();

                        parent.setConnectionStatus("Connection Success");
                        parent.setParentID(object.getString("ParentID"));
                        parent.setUserID(object.getString("UserID"));
                        parent.setFatherName(object.getString("FatherName"));
                        parent.setMotherName(object.getString("MotherName"));
                        parent.setDoorStreet(object.getString("DoorStreet"));
                        parent.setCity(object.getString("City"));
                        parent.setState(object.getString("State"));
                        parent.setPincode(object.getString("Pincode"));
                        parent.setMotherTongue(object.getString("MotherTongue"));
                        parent.setReligion(object.getString("Religion"));
                        parent.setCaste(object.getString("Caste"));
                        parent.setTelRes(object.getString("TelRes"));
                        parent.setMomMobile(object.getString("MomMobile"));
                        parent.setDadMobile(object.getString("DadMobile"));
                        parent.setDadEmailID(object.getString("DadEmailID"));
                        parent.setMomEmailID(object.getString("MomEmailID"));

                        Log.d("Student", parent.toString());

                        return parent;
                    } else {
                        Parent parent = new Parent();
                        parent.setConnectionStatus("Connection Failed");

                    }
                } catch (UnknownHostException e) {
                    Log.d("Exception at IOE", "");
                    e.printStackTrace();
                    Parent parent = new Parent();
                    parent.setConnectionStatus("Connection Failed");
                    return parent;
                } catch (MalformedURLException e) {
                    Log.d("Exception at IOE", "");
                    e.printStackTrace();
                    Parent parent = new Parent();
                    parent.setConnectionStatus("Connection Failed");
                    return parent;
                } catch (JSONException e) {
                    Log.d("Exception at JSONE", "");
                    e.printStackTrace();
                    Parent parent = new Parent();
                    parent.setConnectionStatus("Connection Failed");
                    return parent;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        @Override
        protected void onPostExecute (Parent parent){



            if (parent != null) {
                if (!parent.getConnectionStatus().equals("Connection Failed")) {
                   Log.d("Ctx At post ",ctx.getClass().toString());
                   Intent i = new Intent(ctx, ParentProfileView.class);
                   Log.d("Intent",i.getData().toString());
                   i.putExtra("Parent_name", parent.getFatherName());
                   i.putExtra("Mother_name",parent.getMotherName());
                   i.putExtra("Father_mob", parent.getDadMobile());
                   i.putExtra("mother_mob", parent.getMomMobile());
                   i.putExtra("res_no", parent.getTelRes());
                   i.putExtra("father_email", parent.getDadEmailID());
                   i.putExtra("mother_email", parent.getMomEmailID());
                   i.putExtra("res_address", parent.getDoorStreet()+" "+parent.getCity()+" "+parent.getState()+" "+parent.getPincode());
                   startActivity(i);

                } else {
                   ConnectionDetector connectionDetector = new ConnectionDetector(ctx);
                   connectionDetector.ConnectionIssue();
                }
            } else {
               ConnectionDetector connectionDetector = new ConnectionDetector(ctx);
               connectionDetector.ConnectionIssue();
            }
        }
    }
}
