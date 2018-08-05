package com.nes;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StudentProfileView extends AppCompatActivity {

    ImageButton imageButton;
    ImageView std_image;
    private LinearLayout layout;
    ImageButton editProfile;
    private String stuid;
    TextView stdent_name,grn_no,classname,DOB,Gender,DOJ,blood_grp;
    private String stden_name,grno_no,classname_sec,std_DOB,std_Gender,std_DOJ,std_blood_grp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_view);

        std_image = (ImageView)findViewById(R.id.image_student);


        stden_name = getIntent().getExtras().getString("stdent_name");
        Log.d("Std Detais",stden_name);
        grno_no = getIntent().getExtras().getString("grn_no");
        Log.d("Std Detais",grno_no);
        classname_sec = getIntent().getExtras().getString("classname");
        Log.d("Std Detais",classname_sec);
        std_DOB = getIntent().getExtras().getString("DOB");
        Log.d("Std Detais",std_DOB);
        std_Gender = getIntent().getExtras().getString("Gender");
        Log.d("Std Detais",std_Gender);
        std_DOJ = getIntent().getExtras().getString("DOJ");
        Log.d("Std Detais",std_DOJ);
        std_blood_grp = getIntent().getExtras().getString("blood_grp");
        Log.d("Std Detais",std_blood_grp);

        if (std_Gender.equals("M")){
            std_image.setImageResource(R.drawable.male_student);
        }else
        {
            std_image.setImageResource(R.drawable.female_student);
        }

        layout = (LinearLayout)findViewById(R.id.lyt_parent);

        stdent_name = (TextView)findViewById(R.id.stdent_name);
        if (stden_name!=null && !stden_name.equals(""))
            stdent_name.setText(stden_name);
        grn_no = (TextView)findViewById(R.id.grn_no);
        if (grno_no!=null && !grno_no.equals(""))
            grn_no.setText(grno_no);
        classname = (TextView)findViewById(R.id.classname);
        if (classname_sec!=null && !classname_sec.equals(""))
            classname.setText(classname_sec);
        DOB = (TextView)findViewById(R.id.DOB);
        if (std_DOB!=null && !std_DOB.equals(""))
            DOB.setText(std_DOB);
        Gender = (TextView)findViewById(R.id.Gender);
        if (std_Gender!=null && !std_Gender.equals("")) {
            if (std_Gender.equals("M")) {
                Gender.setText("MALE");
            } else {
                Gender.setText("FEMALE");
            }
        }
        DOJ = (TextView)findViewById(R.id.DOJ);
        if (std_DOJ!=null && !std_DOJ.equals(""))
            DOJ.setText(std_DOJ);
        blood_grp = (TextView)findViewById(R.id.blood_grp);
        if (std_blood_grp!=null && !std_blood_grp.equals(""))
        blood_grp.setText(std_blood_grp);



        imageButton = (ImageButton)findViewById(R.id.close);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editProfile = (ImageButton)findViewById(R.id.btn_edit);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(layout,"Please contact school for correction",Snackbar.LENGTH_LONG).show();
            }
        });

    }
}
