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


    String date_req="28/09/2018";
    String QUARY_URL="http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    public Document getDocument(String url) throws MalformedURLException,
            IOException, Exception {
        URL documentUrl = new URL(url);
        URLConnection conn = documentUrl.openConnection();

        DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document;
        InputStream stream = null;
        try {
            stream = conn.getInputStream();
            document = builder.parse(stream);
        } finally {
            if (stream != null)
                stream.close();
        }
        return document;
    }

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
        int request_code=1;
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},request_code);
        }
        AsyncDownloader download = new AsyncDownloader();
        download.execute();



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


    private class AsyncDownloader extends AsyncTask<Object, String, Integer>{
        String nom;
        String cod;
        String val;
        @Override
        protected Integer doInBackground(Object... objects) {
            XmlPullParser receivedData = tryDownloadingXmlData();
            try {
                int recordsFound = tryParsingXmlData(receivedData);
            } catch (XmlPullParserException e) {
            } catch (IOException e) {
            }
            return null;
        }

        private XmlPullParser tryDownloadingXmlData(){
            XmlPullParser receivedData=null;
            try{
                URL xmlUrl = new URL(QUARY_URL+date_req);
                receivedData = XmlPullParserFactory.newInstance().newPullParser();
                receivedData.setInput(xmlUrl.openStream(),null);

            } catch (XmlPullParserException e) {
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            return receivedData;
        }

        private int tryParsingXmlData(XmlPullParser receivedData) throws XmlPullParserException, IOException {
            if(receivedData!=null){
                return  processReceivedData(receivedData);
            }
            return 0;
        }

        private int processReceivedData(XmlPullParser receivedData) throws XmlPullParserException, IOException {
            int eventType = -1;
            int recordsFound = 0;

            String id="R01010";
            String nom="";
            String cod="";
            String val="";




            while(eventType!=XmlPullParser.END_DOCUMENT) {
                if(receivedData.getEventType()==XmlPullParser.START_TAG&&receivedData.getName()=="Valute"&&receivedData.getAttributeValue(0)==id){
                    receivedData.next();receivedData.next();
                    cod=receivedData.getText();
                    receivedData.next();
                    nom=receivedData.getText();
                    receivedData.next();
                    receivedData.next();
                    val=receivedData.getText();
                }

                receivedData.next();
            }
            publishProgress(cod,nom,val);

            return 0;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            nom=values[0];
            cod=values[1];
            val=values[2];
            TextView test= (TextView) getActivity().findViewById(R.id.test);
            test.setText(cod);
            super.onProgressUpdate(values);
        }

        public String getNom() {
            return nom;
        }

        public String getCod() {
            return cod;
        }

        public String getVal() {
            return val;
        }
    }

}
