package com.example.install.projet1.metiers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConvertisseurBDD extends SQLiteOpenHelper{

    private static Map<String, Double> conversionTable = new HashMap<String, Double>();

    //Database info
    private static String DB_NAME = "MONNAIES";
    private static String DB_PATH = "/data/data/com.example.install.projet1/databases/";

    public static final String TABLE = "MONNAIES";
    public static final String MONNAIE = "MONNAIE";
    public static final String TAUX = "TAUX";
    SQLiteDatabase db;
    private static ConvertisseurBDD instance;

    public ConvertisseurBDD(Context context) {

        super(context, "MONNAIES", null, 1);
        Log.i("XXXXXXXXXXXXXXXXXXXXXXX", "constructeur");
        db= getWritableDatabase();
        lire(conversionTable);
       //insertData("toto", 100.0);
      // deleteData("toto");
      updateData("toto", 3456770987.56 );

    }

    public static synchronized ConvertisseurBDD getInstance(Context context) {
        if (instance == null)
            instance = new ConvertisseurBDD (context);
        return instance;
    }

    public static double convertir(String source, String cible, double montant) {
        //The constants should probably be defined somewhere else
        double tauxSource = ((Double) conversionTable.get(source)).doubleValue();
        double tauxCible = ((Double) conversionTable.get(cible)).doubleValue();
        double tauxConversion = tauxCible / tauxSource;
        return (montant * tauxConversion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("XXXXXXXX", "je suis dans le oncreate");
        db.execSQL("CREATE TABLE MONNAIES ( MONNAIE TEXT, TAUX NUMERIC)");
        db.insert("MONNAIES", null, createContentValues("Dollars US", 1));
        db.insert("MONNAIES", null, createContentValues("Yen", 76.6908));
        db.insert("MONNAIES", null, createContentValues("Euro", 0.7697));
    }


    private ContentValues createContentValues(String monnaie, double taux) {
        ContentValues values = new ContentValues();
        values.put("MONNAIE", monnaie);
        values.put("TAUX", taux);
        return values;
    }

    public void lire(Map<String, Double> conversionTable)
    {
        //Récupère dans un Cursor les données contenues dans la BDD
        Cursor c = db.query("MONNAIES", new String[] {"MONNAIE", "TAUX"},null,null, null, null, null);
        c.moveToFirst();
        //balayage du Cursor
        while (!c.isAfterLast())
        {
            conversionTable.put(c.getString(0), new Double(c.getString(1)));
            c.moveToNext();
        }
        //fermeture du cursor
        c.close();
        //on ferme la BDD
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }


    public static Map<String, Double> getConversionTable() {
        return conversionTable;
    }

    //inserer dans la database
    public void insertData(String monnaie, Double valeur) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MONNAIE,monnaie);
        contentValues.put(TAUX,valeur);

        long result = db.insert("MONNAIES",null ,contentValues);
    }

    //supprimer une monnaie
    public Integer deleteData (String monnaie) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("MONNAIES", "MONNAIE = ?",new String[] {monnaie});
    }

    //Modifier une valeur dans la database
    public boolean updateData(String monnaie, Double valeur) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MONNAIE,monnaie);
        contentValues.put(TAUX,valeur);
        db.update("MONNAIES", contentValues, "MONNAIE = ?",new String[] { monnaie });
        return true;
    }

}
