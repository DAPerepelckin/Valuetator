package com.project.dimitriipa.valuetator;


import android.Manifest;
import android.app.VoiceInteractor;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class BlankFragment extends Fragment {
    String text;
    private static final int REQUEST_CODE = 123;
    String date_req="28/09/2018";
    String QUARY_URL="http://www.cbr.ru/scripts/XML_daily.asp?date_req=";

    public boolean isOpen() {
        return open;
    }

    boolean open;

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        open=true;
        requestPermissions(new String[]{Manifest.permission.INTERNET,Manifest.permission_group.STORAGE,Manifest.permission_group.PHONE},REQUEST_CODE);
        new gettingParse().execute();




    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }

private  class gettingParse extends AsyncTask<String,Void,Void>{

    @Override
    protected Void doInBackground(String... strings) {
        text=parsing.getCharCode("R01010");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        TextView tv6 = (TextView) getActivity().findViewById(R.id.textView6);
        tv6.setText(text);
    }
}


}
