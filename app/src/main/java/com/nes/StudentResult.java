package com.nes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class StudentResult extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    TextView parent_name,parent_email;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private Menu menu;

    private String fatherName;
    private String dadEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result);

        fatherName = getIntent().getExtras().getString("FatherName");
        dadEmail = getIntent().getExtras().getString("dadEmail");

        Toolbar toolbar = findViewById(R.id.toolbar);
        nv = (NavigationView)findViewById(R.id.nav_my_profile_parent);
        nv.setNavigationItemSelectedListener(StudentResult.this);
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
                    Intent intent = new Intent(StudentResult.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    StudentResult.this.finish();
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
}
