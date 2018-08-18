package com.example.vikin.sensoresui;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.util.Calendar;
import java.util.Random;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public TextView info, clientes;
    Switch switchButton;
    Boolean activar = true;
    NotificationHelper helper;
    Server server;
    ServerSocket serverSocket;
    static final int READ_BLOCK_SIZE = 100;
    private static final  int uniqueID = 45612;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.infoip);
        switchButton = (Switch)findViewById(R.id.switch1);
        switchButton.setChecked(true);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { //Activar o desactivar notificaciones
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    activar = true;
                }else{
                    activar = false;
                }
            }
        });
        server = new Server(this); //Intanciar clase server
        info.setText(server.getIpAddress()+":"+server.getPort()); // mostrar la información IP de donde se ejecuta el servidor
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){ //Crear menú contextual
        getMenuInflater().inflate(R.menu.my_context_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getTitle().toString()){
            case "Historial":
                Intent intent = new Intent(this, DisplayMessageActivity.class);
                //EditText editText = (EditText) findViewById(R.id.editText);
                //String message = editText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, "hola");
                startActivity(intent);
                break;
            case "Borrar Historial":
                info.setText("Registros borrados");
                deleteFile("Registro.txt");
                break;
                default:
                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getMyData(String message){
        String opc="";
        String nodsen, ubica;
        int tempe, gas;
        String datos ="";
        String nodo="";
        String ubicacion="";
        Bundle bundle = new Bundle();

        if(message!=" ") {
            opc = message.substring(0, 4);
            //datos=message.substring(0, 4);

            switch (opc) {
                case "0x01":
                    message = message.substring(4, message.length());
                    nodsen = message.substring(0, 4);
                    if(nodsen.equals("0x01"))  nodo ="Nodo Uno";
                    if(nodsen.equals("0x02")) nodo = "Nodo dos";
                    ubica = message.substring(4,8);
                    if(ubica.equals("0x01")) ubicacion="Cocina";
                    datos = message.substring(8,message.length());
                    //PACK DATA IN A BUNDLE
                    //tempe = Integer.parseInt(datos);
                    /*if(tempe>40 && activar){
                       helper = new NotificationHelper(this);
                       Notification.Builder builder = helper.getEDMTChannelNotification("Temperatura",datos + "°C");
                       helper.getManager().notify(new Random().nextInt(),builder.build());
                    }*/
                    //mensajetemp = message;
                    bundle.putString("NAME_KEY", message);
                    //PASS OVER THE BUNDLE TO OUR FRAGMENT
                    TempFragment tempFragment = new TempFragment();
                    tempFragment.setArguments(bundle);
                    //THEN NOW SHOW OUR FRAGMENT
                    getSupportFragmentManager().beginTransaction().replace(R.id.container1, tempFragment).commit();
                    try {
                        FileOutputStream fos = openFileOutput("Registro.txt", MODE_APPEND);
                        OutputStreamWriter osw = new OutputStreamWriter(fos);
                        osw.write("Ubicación: " + ubicacion +"\n" +
                                    "Nodo sensor: "+ nodo+ "\n"+
                                    "Temperatura: " +datos+"°C"+ "\n"+
                                    Calendar.getInstance().getTime() + "\n\n");
                        osw.flush();
                        osw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                //return envia;
                case "0x02":
                    message = message.substring(4, message.length());
                    nodsen = message.substring(0, 4);
                    if(nodsen.equals("0x01"))  nodo ="Nodo Uno";
                    if(nodsen.equals("0x02")) nodo = "Nodo dos";
                    ubica = message.substring(4,8);
                    if(ubica.equals("0x01")) ubicacion="Cocina";
                    datos = message.substring(8,message.length());
                    //PACK DATA IN A BUNDLE
                    gas = Integer.parseInt(datos);
                    if(gas>100 && activar){
                        helper = new NotificationHelper(this);
                        Notification.Builder builder = helper.getEDMTChannelNotification("Concentración Gas",datos +"ppm");
                        helper.getManager().notify(new Random().nextInt(),builder.build());
                    }
                    //mensajeGas = message;
                    //PACK DATA IN A BUNDLE
                    //Bundle bundle = new Bundle();
                    bundle.putString("NAME_KEY", message);
                    //PASS OVER THE BUNDLE TO OUR FRAGMENT
                    GasFragment gasFragment = new GasFragment();
                    gasFragment.setArguments(bundle);
                    //THEN NOW SHOW OUR FRAGMENT
                    //
                    getSupportFragmentManager().beginTransaction().replace(R.id.container2, gasFragment).commit();
                    try {
                        FileOutputStream fos = openFileOutput("Registro.txt", MODE_APPEND);
                        OutputStreamWriter osw = new OutputStreamWriter(fos);
                        osw.write("Ubicación: " + ubicacion +"\n" +
                                "Nodo sensor: "+ nodo+ "\n"+
                                "Concentracion de gas: " +datos+"ppm"+ "\n"+
                                Calendar.getInstance().getTime() + "\n\n");
                        osw.flush();
                        osw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                //return envia;
                case "0x03":
                    // NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                   // notificacion();
                    //notifica();
                    //Dialog dialog = new Dialog(this);
                    if(activar) {
                        helper = new NotificationHelper(this);
                        Notification.Builder builder = helper.getEDMTChannelNotification("Sensor PIR", "Persona detectada");
                        helper.getManager().notify(new Random().nextInt(), builder.build());
                    }
                    //info.setTextSize(18);
                    //info.setText("Persona Detectada");
                    break;
                default:
                    break;
            }
        }
    }

}
