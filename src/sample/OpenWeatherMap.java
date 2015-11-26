
package sample;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


import java.text.DecimalFormat;
import java.util.*;


public class OpenWeatherMap {
    /**
     * Arrays amb la informació que pot tenir cada un dels dias
     */
    public ArrayList<Double> tempMin = new ArrayList<>();
    public ArrayList<Double> tempMax = new ArrayList<>();
    public ArrayList<String>presion=new ArrayList<String>();
    public ArrayList<Double>velVent=new ArrayList<Double>();
    public ArrayList<String>dirVent=new ArrayList<String>();
    public ArrayList<String>humidad=new ArrayList<String>();
    public ArrayList<String>lluvia=new ArrayList<String>();
    public ArrayList<String>dia=new ArrayList<String>();
    public ArrayList<String> iconid=new ArrayList<String>();
    public ArrayList<String> estado = new ArrayList<String>();
    double mitjanatemp;


    /**
     *
     * @param list
     * @param ciutat
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public void refresh(ObservableList list, String ciutat) throws ParserConfigurationException, IOException, SAXException {
        String text;
        /*
        URL serveix per extreure la informació directament de una URL
         */
        URL xmlURL = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q="+ciutat+",es&lang=sp&mode=xml&APPID=720f431ee254e6c38e84787031900368");
        InputStream InputFile = xmlURL.openStream();
        /*
        Protocol d'entrada
         */
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(InputFile);
        doc.getDocumentElement().normalize();
        NodeList nl = doc.getElementsByTagName("time");
        DecimalFormat decimales = new DecimalFormat(",00");

        for (int temp =0; temp<nl.getLength();temp++){
            Element temps = (Element) nl.item(temp);
            /**
             * Omplim les arrays amb a diferent informació que té cadascun dels dies.
             */

            tempMax.add(Double.parseDouble(decimales.format(Double.parseDouble(temps.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("max").getNodeValue()) -273.15)));
            tempMin.add(Double.parseDouble(decimales.format(Double.parseDouble(temps.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("min").getNodeValue())-273.15)));
            velVent.add(Double.parseDouble(temps.getElementsByTagName("windSpeed").item(0).getAttributes().getNamedItem("mps").getNodeValue()));
            humidad.add(temps.getElementsByTagName("humidity").item(0).getAttributes().getNamedItem("value").getNodeValue()+" "+temps.getElementsByTagName("humidity").item(0).getAttributes().getNamedItem("unit").getNodeValue());
            presion.add(temps.getElementsByTagName("pressure").item(0).getAttributes().getNamedItem("value").getNodeValue()+" "+temps.getElementsByTagName("pressure").item(0).getAttributes().getNamedItem("unit").getNodeValue());
            dirVent.add(temps.getElementsByTagName("windDirection").item(0).getAttributes().getNamedItem("name").getNodeValue());
            dia.add(temps.getAttribute("day"));
            iconid.add(temps.getElementsByTagName("symbol").item(0).getAttributes().getNamedItem("var").getNodeValue()+".png");
            estado.add(temps.getElementsByTagName("symbol").item(0).getAttributes().getNamedItem("name").getNodeValue());

            text="Dia :  "+temps.getAttribute("day")+"\n\n";
            if(temps.getElementsByTagName("precipitation").item(0).hasAttributes()){

                text=text+" - Previsiones de lluvia  : "+temps.getElementsByTagName("precipitation").item(0).getAttributes().getNamedItem("value").getNodeValue()+"\n";
                lluvia.add(temps.getElementsByTagName("precipitation").item(0).getAttributes().getNamedItem("value").getNodeValue());

            }else{
                lluvia.add("");
            }
            text=text+" - Temperatura Máxixma :  "+tempMax.get(temp)+ "\n - Temperatura Minima : "+ String.format("%s\n", tempMin.get(temp));
            text=text+" - Estado  :  " + temps.getElementsByTagName("symbol").item(0).getAttributes().getNamedItem("name").getNodeValue()+"\n\n";

            /*
            Omplim directament el ListView
             */
            list.add(text);

        }

    }


}