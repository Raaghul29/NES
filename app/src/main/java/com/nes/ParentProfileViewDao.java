package com.nes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

public class ParentProfileViewDao extends AppCompatActivity{
    Context context;
    public ParentProfileViewDao(Context ctx) {
        context = ctx;
        Log.d("CTX",context.getClass().toString());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void getParentProfile(Context ctx, String parId){

        Intent i = new Intent(ctx,ParentProfileView.class);
            startActivity(i);
//        Dao dao = new Dao();
//        dao.execute(parId);

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
//                    Log.d("Ctx At post ",ctx.getClass().toString());
//                    Intent i = new Intent(ctx, ParentProfileView.class);
//                    Log.d("Intent",i.getData().toString());
//                    i.putExtra("Parent_name", parent.getFatherName());
//                    i.putExtra("Mother_name",parent.getMotherName());
//                    i.putExtra("Father_mob", parent.getDadMobile());
//                    i.putExtra("mother_mob", parent.getMomMobile());
//                    i.putExtra("res_no", parent.getTelRes());
//                    i.putExtra("father_email", parent.getDadEmailID());
//                    i.putExtra("mother_email", parent.getMomEmailID());
//                    i.putExtra("res_address", parent.getDoorStreet()+" "+parent.getCity()+" "+parent.getState()+" "+parent.getPincode());
//                    startActivity(i);

                } else {
//                    ConnectionDetector connectionDetector = new ConnectionDetector(ctx);
//                    connectionDetector.ConnectionIssue();
                }
            } else {
//                ConnectionDetector connectionDetector = new ConnectionDetector(ctx);
//                connectionDetector.ConnectionIssue();
            }
        }
    }
}
