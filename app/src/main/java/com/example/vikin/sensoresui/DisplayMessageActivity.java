package com.example.vikin.sensoresui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DisplayMessageActivity extends AppCompatActivity {
    static final int READ_BLOCK_SIZE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        TextView msgRecord = (TextView)findViewById(R.id.msgRecord);
        try{
            msgRecord.setText("No existe historial almacenado");
            FileInputStream fis = openFileInput("Registro.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s ="";
            int charRead;
            while ((charRead = isr.read(inputBuffer))>0){
                String readString = String.copyValueOf(inputBuffer, 0,charRead);
                s+= readString;
                inputBuffer = new char[READ_BLOCK_SIZE];

            }
            msgRecord.setText(s);
            Toast.makeText(getBaseContext(), "cargado",Toast.LENGTH_SHORT);
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
