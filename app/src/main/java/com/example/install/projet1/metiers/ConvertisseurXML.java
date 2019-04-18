package com.example.install.projet1.metiers;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;

import com.example.install.projet1.R;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ConvertisseurXML {

    private  Map conversionTable = new HashMap<>();

    public ConvertisseurXML (Context ctx)
    {
        //transforme le fichier texte en document xml

        InputStream fic = ctx.getResources().openRawResource(R.raw.monnaies);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        Document document = null;
        try {
            document = dbf.newDocumentBuilder().parse(fic);

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        for (int i=0; i<document.getElementsByTagName("nom").getLength();i++)
            {
                String cle = document.getElementsByTagName("nom").item(i).getFirstChild().getNodeValue().toString() ;
                double value = Double.parseDouble(document.getElementsByTagName("nom").item(i).getNextSibling().getNextSibling().getFirstChild().getNodeValue()) ;
                conversionTable.put(cle, value);
            }


    }

    public  double convertir(String source, String cible, double montant)
    {
        //The constants should probably be defined somewhere else
        double tauxSource = ((Double)conversionTable.get(source)).doubleValue() ;
        double tauxCible = ((Double)conversionTable.get(cible)).doubleValue() ;
        double tauxConversion = tauxCible/tauxSource;
        return (montant * tauxConversion) ;
    }

    public  Map getConversionTable()
    {
        return conversionTable;
    }
}
