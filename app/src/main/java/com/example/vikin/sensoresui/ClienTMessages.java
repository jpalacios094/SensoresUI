package com.example.vikin.sensoresui;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienTMessages extends Thread {
    Socket socket;
    MainActivity activity;
    BufferedReader b;
    public ClienTMessages(Socket socket,MainActivity activity){
        this.socket = socket;
        this.activity =activity;
        try {
            b = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run(){
        String mensaje="";
        while(true) {
            try {
                mensaje=b.readLine();
                if(mensaje!=null) {
                    //activity.info.setText(mensaje);
                    activity.getMyData(mensaje);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
