package com.example.install.projet1;


import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.install.projet1.metiers.Convertisseur;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textMontant ;
    private Spinner spinDepart;
    private Spinner spinCible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Remplissage des combo box en prenant les info de la classe convertisseur
        spinDepart = findViewById(R.id.boxMonnaieAConv);
        spinCible = findViewById(R.id.MonnaieConv);
        textMontant = findViewById(R.id.txtSaisieMontant);

        ArrayList<String> list = new ArrayList<>(Convertisseur.getConversionTable().keySet());

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinDepart.setAdapter(adapter);
        spinCible.setAdapter(adapter);




    }

    public void convertir (View v)
    {
        // vérification des champs
        // try catch
        String monnaieDep = spinDepart.getSelectedItem().toString();
        String monnaieCible = spinCible.getSelectedItem().toString();

        try {


        if (textMontant.getText().toString().isEmpty() || textMontant.getText().toString().equals("."))
        {
            Toast.makeText(this, "Montant non indiqué ou non valide", Toast.LENGTH_LONG).show();
        }
        else if (monnaieCible.isEmpty() || monnaieDep.isEmpty())
        {
            Toast.makeText(this, "Monnaie non indiquée", Toast.LENGTH_LONG).show();
        }

        else if (monnaieCible.equals(monnaieDep))
        {
            Toast.makeText(this, "Monnaies identiques", Toast.LENGTH_LONG).show();
        }

        else {
            //execution de la conversion
            Double montant = Double.parseDouble(textMontant.getText().toString());
            double result = Convertisseur.convertir(monnaieDep, monnaieCible, montant);

            Toast.makeText(this, "click sur convertir : " + result, Toast.LENGTH_LONG).show();


            //creation page resultat
            Intent intent = new Intent(this, PageResultat.class);
            intent.putExtra("result", result);
            startActivity(intent);


        }
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "je suis dans le try catch", Toast.LENGTH_LONG).show();
        }
    }


    // instanciation des menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        //instanciation du menu XML spécifier dans un objet menu
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // methode qui se déclanchera au clic sur un item du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // on test l'item cliqué et on declanche l'action
        switch(item.getItemId()){
            case R.id.affichage :
                Intent changerAffichage = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                startActivity(changerAffichage);
                return true;
            case R.id.langue:
                Intent changerDeLangue = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changerDeLangue);
                return true;
            case R.id.date:
                Intent changerDate = new Intent(Settings.ACTION_DATE_SETTINGS);
                startActivity(changerDate);
                return true;
            case R.id.quitter:
               finish();
        }
        return false;
    }



}
