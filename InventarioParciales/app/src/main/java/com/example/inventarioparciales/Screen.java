package com.example.inventarioparciales;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

public class Screen {
    private Context conte;
    private Activity act;
    public Screen (Activity myActivity){
        this.act=myActivity;
    }
    public void flag (){
        act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
