package com.nes;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionDetector {

    Context ctx;

    public ConnectionDetector(Context ctx){
        this.ctx=ctx;
    }

    public  boolean isConnected(){
        ConnectivityManager connectivity = (ConnectivityManager)
                ctx.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            if (networkInfo != null){
                if (connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    public void ConnectionIssue(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Connection Error:");
        builder.setMessage("Can't connect. Please try later.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
