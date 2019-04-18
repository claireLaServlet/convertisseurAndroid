package com.example.install.projet1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.install.projet1.metiers.ConvertisseurBDD;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textMontant ;
    private Spinner spinDepart;
    private Spinner spinCible;
    //ConvertisseurXML cxml;
    ConvertisseurBDD bdd;
    private List<String> monnaies = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Remplissage des combo box en prenant les info de la classe convertisseur
        spinDepart = findViewById(R.id.boxMonnaieAConv);
        spinCible = findViewById(R.id.MonnaieConv);
        textMontant = findViewById(R.id.txtSaisieMontant);

        ConvertisseurBDD.getInstance(getApplicationContext());
        ArrayList<String> list = new ArrayList<>(ConvertisseurBDD.getConversionTable().keySet());



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinDepart.setAdapter(adapter);
        spinCible.setAdapter(adapter);

        //pression longue sur l'image pour la declancher
        //registerForContextMenu(findViewById(R.id.licorne));

        // gestion des preferences
        //instanciation de l'instance
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int setting = adapter.getPosition(sharedPreferences.getString("monnaieDep", ""));
        int setting2 = adapter.getPosition(sharedPreferences.getString("monnaieArr", ""));

        spinDepart.setSelection(setting);
        spinCible.setSelection(setting2);


    }

    public void convertir (View v)
    {
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
           // double result = Convertisseur.convertir(monnaieDep, monnaieCible, montant);
            double result = bdd.convertir(monnaieDep, monnaieCible, montant);

            //creation page resultat
            Intent intent = new Intent(this, PageResultat.class);
            intent.putExtra("result", result);
            startActivity(intent);

            //utilisation des preferences

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            // Writing data to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //ajout des données
            editor.putString("monnaieDep", monnaieDep);
            editor.putString("monnaieArr", monnaieCible);
            //applique les modifications
            editor.apply();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item)
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
        return super.onContextItemSelected(item);
    }





}
