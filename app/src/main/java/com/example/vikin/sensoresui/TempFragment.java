package com.example.vikin.sensoresui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.InputStreamReader;



public class TempFragment extends Fragment {
    public TempFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temp, container, false);

        TextView temperatura = (TextView) view.findViewById(R.id.temp);
        TextView ubicacion = (TextView) view.findViewById(R.id.ubi);
        TextView nodo = (TextView)view.findViewById(R.id.nodsen);
        //yearFragTxt = (TextView) rootView.findViewById(R.id.yearTxt);

        //UNPACK OUR DATA FROM OUR BUNDLE
        String name = this.getArguments().getString("NAME_KEY").toString() ;
        //Obtener los datos de la cadena
        //temperatura.setText(name);String nodsen = name.substring(0, 4);
        //        String ubica = name.substring(4,8);
        //        String datos = name.substring(8,name.length());
        //
        //        if(ubica.equals("0x01")) ubicacion.setText("Cocina");
        //        if(nodsen.equals("0x01"))nodo.setText("Nodo uno");
        //        if(nodsen.equals("0x02"))nodo.setText("Nodo dos");
        //        temperatura.setText(datos+"°C");




        // Inflate the layout for this fragment
        return view;
    }


}
