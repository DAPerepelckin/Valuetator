package com.project.dimitriipa.valuetator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class parsing {

    static String charCode;
    static String nominal;
    static String val;
    String date_req="28/09/2018";
    String QUARY_URL="http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    static Exception ex = null;


    public static void parse(String id){

        XmlPullParser reqData=null;

        try {

            URL Urlxml = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=28/09/2018");
            reqData = XmlPullParserFactory.newInstance().newPullParser();
            reqData.setInput(Urlxml.openStream(),null);

            while (reqData.getEventType()!=XmlPullParser.END_DOCUMENT){





                if (reqData.getEventType()==XmlPullParser.START_TAG&&reqData.getAttributeValue(0).toString().equalsIgnoreCase(id)){
                    reqData.next();
                    reqData.next();
                    reqData.next();
                    reqData.next();
                    reqData.next();
                    charCode = reqData.getText();
                    reqData.next();
                    reqData.next();
                    reqData.next();
                    nominal = reqData.getText();
                    reqData.next();
                    reqData.next();
                    reqData.next();
                    reqData.next();
                    reqData.next();
                    reqData.next();
                    val = reqData.getText();
                    break;
                }
                reqData.next();
            }
String re=charCode;

        } catch (MalformedURLException e) {
            ex = e;
            return;
        } catch (XmlPullParserException e) {
            ex = e;
            return;
        } catch (IOException e) {
            ex = e;
            return;
        }
    }




    public static String getCharCode(String id) {
        parse(id);
        if(ex==null){
            return charCode;
        }
        else{
            return ex.toString();
        }
    }

    public String getNominal(String id) {
        parse(id);
        if(ex==null){
            return nominal;
        }
        else{
            return ex.toString();
        }
    }

    public String getVal(String id) {
        parse(id);
        if(ex==null){
            return val;
        }
        else{
            return ex.toString();
        }
    }
}
