package com.example.install.projet1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PageResultat extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_resultat);

        // on recupere l'intent
        Intent thisIntent = getIntent();

        // on recupere les parametres passés
        Double result = thisIntent.getExtras().getDouble("result");

        //affichage du resultat dans la nouvelle page
        TextView aff = (TextView)findViewById(R.id.txtAffichageResult);
        aff.setText(result.toString());

        //permet de voir la fleche de retour en arriere
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //permet de faire les actions grace a la fleche pour revenir en arriere
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

