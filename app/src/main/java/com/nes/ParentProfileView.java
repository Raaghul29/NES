package com.nes;

import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ParentProfileView extends AppCompatActivity {

    ImageButton imageButton,edit_btn;
    LinearLayout layout;

    TextView father_name,mother_name,father_mobile,mother_mobile,resi_mob,father_emailId,mother_emailID,address_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile_view);

        layout = (LinearLayout)findViewById(R.id.lyt_parent);

        father_name = (TextView)findViewById(R.id.parent_name);
//        if(getIntent().getExtras().getString("Parent_name")!=null && !getIntent().getExtras().getString("Parent_name").equals(""))
//            father_name.setText(getIntent().getExtras().getString("Parent_name"));
//
//        mother_name = (TextView)findViewById(R.id.mother_name);
//        if(getIntent().getExtras().getString("Mother_name")!=null && !getIntent().getExtras().getString("Mother_name").equals(""))
//            mother_name.setText("Mother_name");
//
//        father_mobile = (TextView)findViewById(R.id.father_mobile);
//        if(getIntent().getExtras().getString("Father_mob")!=null && !getIntent().getExtras().getString("Father_mob").equals(""))
//            father_mobile.setText("Father_mob");
//
//        mother_mobile = (TextView)findViewById(R.id.mother_mobile);
//        if(getIntent().getExtras().getString("mother_mob")!=null && !getIntent().getExtras().getString("mother_mob").equals(""))
//            mother_mobile.setText("mother_mob");
//
//        resi_mob = (TextView)findViewById(R.id.res_phone);
//        if(getIntent().getExtras().getString("res_no")!=null && !getIntent().getExtras().getString("res_no").equals(""))
//            resi_mob.setText("res_no");
//
//        father_emailId = (TextView)findViewById(R.id.father_email);
//        if(getIntent().getExtras().getString("father_email")!=null && !getIntent().getExtras().getString("father_email").equals(""))
//            father_emailId.setText("father_email");
//
//        mother_emailID = (TextView)findViewById(R.id.mother_email);
//        if(getIntent().getExtras().getString("mother_email")!=null && !getIntent().getExtras().getString("mother_email").equals(""))
//            mother_emailID.setText("mother_email");
//
//        address_res = (TextView)findViewById(R.id.res_address);
//        if(getIntent().getExtras().getString("res_address")!=null && !getIntent().getExtras().getString("res_address").equals(""))
//            address_res.setText("res_address");


        imageButton = findViewById(R.id.close);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_btn = (ImageButton)findViewById(R.id.btn_edit);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(layout,"Feature will be Enabled Shortly",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}