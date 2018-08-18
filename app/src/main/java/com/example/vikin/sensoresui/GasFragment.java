package com.example.vikin.sensoresui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class GasFragment extends Fragment {


    public GasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gas, container, false);

        //TextView mensaje = (TextView) view.findViewById(R.id.gas);

        TextView gas = (TextView) view.findViewById(R.id.gas);
        TextView ubicacion = (TextView) view.findViewById(R.id.ubi);
        TextView nodo = (TextView)view.findViewById(R.id.nodsen);
        //yearFragTxt = (TextView) rootView.findViewById(R.id.yearTxt);

        //UNPACK OUR DATA FROM OUR BUNDLE
        String name = this.getArguments().getString("NAME_KEY").toString();
        // int year = this.getArguments().getInt("YEAR_KEY");
        String nodsen = name.substring(0, 4);
        String ubica = name.substring(4,8);
        String datos = name.substring(8,name.length());

        if(ubica.equals("0x01")) ubicacion.setText("Cocina");
        if(nodsen.equals("0x01"))nodo.setText("Nodo uno");
        if(nodsen.equals("0x02"))nodo.setText("Nodo dos");
        gas.setText(datos+"ppm");

        //gas.setText(datos);
        return view;
    }

}
