package com.jfuentes.josegerardo;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class volleySingleton {
    private static volleySingleton mInstance;
    private RequestQueue mRequesQueve;

    private volleySingleton(Context context){
        mRequesQueve = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized volleySingleton getInstance(Context context){
        if(mInstance==null){
            mInstance=new volleySingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getmRequesQueve(){
        return mRequesQueve;
    }
}
