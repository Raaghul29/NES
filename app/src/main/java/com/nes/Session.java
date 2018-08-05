package com.nes;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx=ctx;
        sharedPreferences = ctx.getSharedPreferences("Nes_User",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedin(boolean loggedin){
        editor.putBoolean("loggedinmode",loggedin);
        editor.commit();

    }

    public  boolean loggedin(){
        return sharedPreferences.getBoolean("loggedinmode",false);
    }

}
